package example.com.data.net;

import example.com.data.entity.BaseResponseEntity;
import example.com.data.entity.CheckInReqEntity;
import example.com.data.entity.InputHistoryEntity;
import example.com.data.entity.OutputHistoryEntity;
import example.com.data.entity.OverrideHistoryRespListEntity;
import example.com.data.entity.OverrideReqEntity;
import example.com.data.entity.OverrideRespEntity;
import example.com.data.entity.OverrideRespListEntity;
import example.com.data.entity.UserEntity;
import example.com.data.entity.UserListEntity;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    @POST("interns/absent")
    Single<BaseResponseEntity> checkUser(@Body CheckInReqEntity check);

    @POST("interns/history")
    Single<OutputHistoryEntity> historyUser(@Body InputHistoryEntity inputHistory);

    @POST("override")
    Single<BaseResponseEntity> requestOverride(@Body OverrideReqEntity overrideInput);

    @GET("override")
    Single<OverrideRespListEntity> getOverrideList();

    @POST("override/accept")
    Single<BaseResponseEntity> approveOverride(@Body OverrideRespEntity overrideInput);

    @PUT("override/{overrideId}")
    Single<BaseResponseEntity> rejectOverride(@Path("overrideId") String id);

    @GET("override/{userid}")
    Single<OverrideHistoryRespListEntity> getOverrideHistoryList(@Path("userid") String userid);

    @DELETE("override/{overrideId}")
    Single<BaseResponseEntity> deletePendingOverride(@Path("overrideId") String userid);

//    @Multipart
//    @PUT("interns/upload-image/{userid}")
//    Single<BaseResponseEntity> uploadImage(
//            @Part("userid") RequestBody userid,
//            @Part MultipartBody.Part[] mulyipartTypedOutput);
}
