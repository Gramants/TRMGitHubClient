package demo.ste.mvpcleanarch.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import java.util.List;

import demo.ste.mvpcleanarch.interfaces.Interactor;
import demo.ste.mvpcleanarch.interfaces.MainActivityPresenter;
import demo.ste.mvpcleanarch.interfaces.MainActivityView;
import demo.ste.mvpcleanarch.model.LoginResponse;
import demo.ste.mvpcleanarch.model.entities.RepoResponseDbEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivityPresenterImpl implements MainActivityPresenter, Interactor.DatafromServer, LifecycleObserver {


    private Interactor interactor;
    private MainActivityView mainActivityView;
    private CompositeDisposable disposeBag;


    public MainActivityPresenterImpl(MainActivityView mainActivityView, Interactor interactor) {
        this.mainActivityView = mainActivityView;
        this.interactor = interactor;

        if (mainActivityView instanceof LifecycleOwner) {
            ((LifecycleOwner) mainActivityView).getLifecycle().addObserver(this);
        }
        disposeBag = new CompositeDisposable();
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAttach() {
        loadAllReposFromDb();
    }

    private void loadAllReposFromDb() {
        mainActivityView.clearResults();
        Disposable disposable = interactor.loadReposFromDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .subscribe(this::handleReturnedData, this::handleError, () -> mainActivityView.hideProgress());

        disposeBag.add(disposable);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDetach() {
        disposeBag.clear();
    }


    private void handleReturnedData(List<RepoResponseDbEntity> list) {
        mainActivityView.hideProgress();
        if (list != null && !list.isEmpty()) {
            mainActivityView.onGetRepoListFromDb(list);
        } else {
            mainActivityView.setNoResult();
        }
    }


    private void handleError(Throwable error) {
        mainActivityView.hideProgress();
        mainActivityView.writeToStatus(error.getLocalizedMessage());
    }

    @Override
    public void onLogin(LoginResponse data) {
        mainActivityView.onLoginResponseReceived(data);
    }

    @Override
    public void onGetRepoFromRemote(List<RepoResponseDbEntity> data) {
        mainActivityView.hideProgress();
        interactor.updateDb(data);


    }


    @Override
    public void setError(String message) {
        mainActivityView.onError(message);
    }

    @Override
    public void doSimpleLogin(String userName, String password) {
        interactor.doLoginUseCase(this, userName, password);
    }

    @Override
    public void doFetchAllRepos(String userName) {
        interactor.fetchAllReposUseCase(this, userName);
    }

    @Override
    public void getResultFromDb() {
        onAttach();
    }

}
