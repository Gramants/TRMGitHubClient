package demo.ste.mvpcleanarch.interfaces;


import java.util.List;

import demo.ste.mvpcleanarch.model.LoginResponse;
import demo.ste.mvpcleanarch.model.entities.RepoResponseDbEntity;
import io.reactivex.Flowable;

public interface Interactor {
    void fetchAllReposUseCase(DatafromServer datafromServer, String userName);

    Flowable<List<RepoResponseDbEntity>> loadReposFromDb();

    Flowable<RepoResponseDbEntity> loadRepoFromDbById(long repoId);


    void doLoginUseCase(DatafromServer datafromServer, String userName, String password);

    void updateDb(List<RepoResponseDbEntity> data);

    void deleteAllUseCase();


    interface DatafromServer {
        void onLogin(LoginResponse data);

        void onGetRepoFromRemote(List<RepoResponseDbEntity> data);

        void setError(String message);
    }

}
