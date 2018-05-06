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
        api.getAllRepos("application/json", userToken, "application/json", userName)
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
                .subscribe(repos -> datafromServer.onGetRepoFromRemote(repos), error -> datafromServer.setError(error.getLocalizedMessage()), () -> {
                });

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
        this.userToken = Credentials.basic(userName, password);
        api.doLogin("application/json", userToken, "application/json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResp -> datafromServer.onLogin(loginResp), error -> datafromServer.setError(error.getLocalizedMessage()), () -> {
                });
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


}
