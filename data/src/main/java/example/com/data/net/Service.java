package example.com.data.net;

import example.com.data.entity.BaseResponseEntity;
import example.com.data.entity.CheckInReqEntity;
import example.com.data.entity.InputHistoryEntity;
import example.com.data.entity.OutputHistoryEntity;
import example.com.data.entity.OverrideReqEntity;
import example.com.data.entity.OverrideRespEntity;
import example.com.data.entity.OverrideRespListEntity;
import example.com.data.entity.UserEntity;
import example.com.data.entity.UserListEntity;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Service {
    @GET("interns")
    Single<UserListEntity> getUserList();

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

    @POST("/absentmg-in-out/absent")
    Single<BaseResponseEntity> checkUser(@Body CheckInReqEntity check);

    @POST("/absentmg-in-out/history")
    Single<OutputHistoryEntity> historyUser(@Body InputHistoryEntity inputHistory);

    @POST("/absentmg-in-out/override")
    Single<BaseResponseEntity> overrideUser(@Body OverrideReqEntity overrideInput);

    @GET("/absentmg-in-out/override/list")
    Single<OverrideRespListEntity> getOverrideList();

    @POST("/absentmg-in-out/override/accept")
    Single<BaseResponseEntity> acceptOverride(@Body OverrideRespEntity overrideInput);

    @DELETE("/absentmg-in-out/override/reject/{id}")
    Single<BaseResponseEntity> rejectOverride(@Path("id") Integer id);
}
