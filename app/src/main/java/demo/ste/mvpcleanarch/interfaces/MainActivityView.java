package demo.ste.mvpcleanarch.interfaces;


import java.util.List;

import demo.ste.mvpcleanarch.model.LoginResponse;
import demo.ste.mvpcleanarch.model.entities.RepoResponseDbEntity;

public interface MainActivityView {
    void showProgress();

    void hideProgress();

    void onError(String type);

    void onLoginResponseReceived(LoginResponse model);

    void writeToStatus(String status);

    void clearResults();

    void onGetRepoListFromDb(List<RepoResponseDbEntity> list);

    void setNoResult();


}
