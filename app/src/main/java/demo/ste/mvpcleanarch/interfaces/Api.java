package demo.ste.mvpcleanarch.interfaces;

import java.util.List;

import demo.ste.mvpcleanarch.model.LoginResponse;
import demo.ste.mvpcleanarch.model.RepoResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;


public interface Api {


    @GET("/user")
    Observable<LoginResponse> doLogin(
            @Header("Accept") String accept,
            @Header("Authorization") String authorization,
            @Header("Content-Type") String contentType
    );


    @GET("/users/{username}/repos")
    Observable<List<RepoResponse>> getAllRepos(
            @Header("Accept") String accept,
            @Header("Authorization") String authorization,
            @Header("Content-Type") String contentType,
            @Path("username") String userName
    );


}
