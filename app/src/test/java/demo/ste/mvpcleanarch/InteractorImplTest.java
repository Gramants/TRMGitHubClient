package demo.ste.mvpcleanarch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import demo.ste.mvpcleanarch.data.database.ProjectDb;
import demo.ste.mvpcleanarch.data.database.RepoDao;
import demo.ste.mvpcleanarch.domain.InteractorImpl;
import demo.ste.mvpcleanarch.interfaces.Api;
import demo.ste.mvpcleanarch.interfaces.Interactor;
import demo.ste.mvpcleanarch.model.LoginResponse;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;


@RunWith(MockitoJUnitRunner.class)
public class InteractorImplTest {

    private static final String ANY_STRING = "anystring";


    @Mock
    Api api;

    @Mock
    RepoDao repoDao;

    @Mock
    ProjectDb projectDb;


    Interactor interactor;


    @Test
    public void getLogin_HttpSuccess() {

        // GIVEN
        MockitoAnnotations.initMocks(this);
        interactor = new InteractorImpl(api, projectDb, repoDao);

        // WHEN
        Mockito.when(api.doLogin(ANY_STRING, ANY_STRING, ANY_STRING)).thenReturn(Observable.just(new LoginResponse()));
        TestObserver<LoginResponse> testSubscriber = TestObserver.create();
        api.doLogin(ANY_STRING, ANY_STRING, ANY_STRING).subscribe(testSubscriber);

        // THEN
        testSubscriber.assertNoErrors();
        testSubscriber.assertSubscribed();
        testSubscriber.assertComplete();

    }

    @Test
    public void getLogin_HttpFailure() {
        // GIVEN
        MockitoAnnotations.initMocks(this);
        interactor = new InteractorImpl(api, projectDb, repoDao);

        // WHEN
        Mockito.when(api.doLogin(ANY_STRING, ANY_STRING, ANY_STRING)).thenReturn(get403ForbiddenError());
        TestObserver<LoginResponse> testSubscriber = TestObserver.create();
        api.doLogin(ANY_STRING, ANY_STRING, ANY_STRING).subscribe(testSubscriber);

        // THEN
        testSubscriber.assertError(HttpException.class);
    }


    private Observable<LoginResponse> get403ForbiddenError() {
        return Observable.error(new HttpException(
                Response.error(401, ResponseBody.create(MediaType.parse("application/json"), "Unauthorized"))));

    }

}



