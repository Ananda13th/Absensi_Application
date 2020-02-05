package example.com.data.net;

import example.com.data.entity.BaseResponseEntity;
import example.com.data.entity.UserEntity;
import example.com.data.entity.UserRespEntity;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Service {
    @GET("interns")
    Single<UserRespEntity> getUserList();

    @GET("interns/{userId}")
    Single<UserEntity> getUser(@Path("userId") String id);

    @DELETE("interns/{userId}")
    Single<BaseResponseEntity> deleteUser(@Path("userId") String id);

    @POST("interns")
    Single<BaseResponseEntity> addUser(@Body UserEntity user);

    @POST("interns/login")
    Single<UserEntity> loginUser(@Body UserEntity user);

    @PUT("interns/{userId}")
    Single<BaseResponseEntity> updateUser(@Path("userId") String id, @Body UserEntity user);
}
