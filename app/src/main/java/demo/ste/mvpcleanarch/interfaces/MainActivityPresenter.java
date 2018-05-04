package demo.ste.mvpcleanarch.interfaces;


public interface MainActivityPresenter {

    void doSimpleLogin(String userName, String password);

    void doFetchAllRepos(String userName);

    void getResultFromDb();
}
