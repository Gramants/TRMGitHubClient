package demo.ste.mvpcleanarch.domain;

import java.util.List;

import demo.ste.mvpcleanarch.data.database.ProjectDb;
import demo.ste.mvpcleanarch.data.database.RepoDao;
import demo.ste.mvpcleanarch.data.database.UpdateRecords;
import demo.ste.mvpcleanarch.interfaces.Api;
import demo.ste.mvpcleanarch.interfaces.Interactor;
import demo.ste.mvpcleanarch.model.LoginResponse;
import demo.ste.mvpcleanarch.model.RepoResponse;
import demo.ste.mvpcleanarch.model.entities.RepoResponseDbEntity;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;


public class InteractorImpl implements Interactor {

    private Api api;
    private RepoDao repoDao;
    private ProjectDb projectDb;
    private String userToken;

    public InteractorImpl(Api api, ProjectDb projectDb, RepoDao repoDao) {
        this.api = api;
        this.repoDao = repoDao;
        this.projectDb = projectDb;
    }

    @Override
    public void fetchAllReposUseCase(DatafromServer datafromServer, String userName) {
        getReposObservable(userName)
                .flatMap(list ->
                        Observable.fromIterable(list)
                                .map(new Function<RepoResponse, RepoResponseDbEntity>() {
                                    @Override
                                    public RepoResponseDbEntity apply(RepoResponse repoResponse) throws Exception {
                                        return new RepoResponseDbEntity(repoResponse.getId(), repoResponse.getName(), repoResponse.getPrivate(), repoResponse.getHtmlUrl(), repoResponse.getDescription());
                                    }
                                })
                                .toList()
                                .toObservable()

                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getReposObserver(datafromServer));

    }


    public Observable<List<RepoResponse>> getReposObservable(String userName) {
        return api.getAllRepos("application/json", userToken, "application/json", userName);
    }


    private Observer<List<RepoResponseDbEntity>> getReposObserver(DatafromServer datafromServer) {
        return new Observer<List<RepoResponseDbEntity>>() {

            @Override
            public void onError(Throwable e) {
                datafromServer.setError(e.getLocalizedMessage());
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<RepoResponseDbEntity> repos) {
                datafromServer.onGetRepoFromRemote(repos);
            }

        };
    }

    @Override
    public Flowable<List<RepoResponseDbEntity>> loadReposFromDb() {
        return repoDao.getAllRepos();
    }

    @Override
    public Flowable<RepoResponseDbEntity> loadRepoFromDbById(long repoId) {
        return repoDao.getRepoById(repoId);
    }

    @Override
    public void doLoginUseCase(DatafromServer datafromServer, String userName, String password) {
        getLoginObservable(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getLoginObserver(datafromServer));
    }

    @Override
    public void updateDb(List<RepoResponseDbEntity> sanitizedRecords) {
        new UpdateRecords() {

            @Override
            protected void updateRecords() {
                projectDb.beginTransaction();
                try {
                    repoDao.updateData(sanitizedRecords);
                    projectDb.setTransactionSuccessful();
                } finally {
                    projectDb.endTransaction();
                }
            }

        };

    }


    public Observable<LoginResponse> getLoginObservable(String userName, String password) {
        this.userToken = Credentials.basic(userName, password);
        return api.doLogin("application/json", userToken, "application/json");

    }

    public Observer<LoginResponse> getLoginObserver(final DatafromServer datafromServer) {
        return new Observer<LoginResponse>() {

            @Override
            public void onError(Throwable e) {
                datafromServer.setError(e.getLocalizedMessage());
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(LoginResponse apiResponseModel) {
                datafromServer.onLogin(apiResponseModel);
            }
        };
    }


}
