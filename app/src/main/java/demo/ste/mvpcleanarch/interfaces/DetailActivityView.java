package demo.ste.mvpcleanarch.interfaces;


import java.util.List;

import demo.ste.mvpcleanarch.model.LoginResponse;
import demo.ste.mvpcleanarch.model.entities.RepoResponseDbEntity;

public interface DetailActivityView {
    void showProgress();

    void hideProgress();

    void onError(String type);

    void onGetRepoDetailFromDb(RepoResponseDbEntity repoItem);

    void writeToStatus(String localizedMessage);
}
