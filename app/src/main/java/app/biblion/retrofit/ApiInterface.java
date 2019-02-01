package app.biblion.retrofit;


import app.biblion.model.ArticalModel;
import app.biblion.model.EditProfileModel;
import app.biblion.model.HomeModel;
import app.biblion.model.LoginModel;
import app.biblion.model.MyLibraryBookModel;
import app.biblion.model.RegisterModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiInterface {

    @Multipart
    @POST("signup")
    Call<RegisterModel> getRegisterDetails(@Part("email") RequestBody email,
                                           @Part("password") RequestBody password,
                                           @Part("firebase_id") RequestBody firebase_id,
                                           @Part("device_id") RequestBody device_id,
                                           @Part("myname") RequestBody myname,
                                           @Part("dob") RequestBody dob,
                                           @Part("gender") RequestBody gender,
                                           @Part("username") RequestBody username,
                                           @Part("mobile") RequestBody mobile,
                                           @Part("country") RequestBody country,
                                           @Part("state") RequestBody state,
                                           @Part("city") RequestBody city,
                                           @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("signup")
    Call<RegisterModel> getRegisterDetailsGoogle(@Field("email") String email,
                                                 @Field("password") String password,
                                                 @Field("firebase_id") String firebase_id,
                                                 @Field("device_id") String device_id,
                                                 @Field("myname") String myname,
                                                 @Field("dob") String dob,
                                                 @Field("gender") String gender,
                                                 @Field("username") String username,
                                                 @Field("mobile") String mobile,
                                                 @Field("country") String country,
                                                 @Field("state") String state,
                                                 @Field("city") String city,
                                                 @Field("googleimage") String googleimage);


    @FormUrlEncoded
    @POST("signin")
    Call<LoginModel> getLoginDetails(@Field("email") String email,
                                     @Field("password") String password,
                                     @Field("device_id") String device_id,
                                     @Field("firebase_id") String firebase_id);

    @FormUrlEncoded
    @POST("articals")
    Call<ArticalModel> getArticalList(@Field("page") int page);

    @FormUrlEncoded
    @POST("getbooks")
    Call<MyLibraryBookModel> getMyLibraryBook(@Field("page") int page);

    @FormUrlEncoded
        @POST("home")
    Call<HomeModel> getHomeList(@Field("page") int page);

    @GET()
    @Streaming
    Call<ResponseBody> downloadImage(@Url String fileUrl);

    @Multipart
    @POST("editprofile")
    Call<EditProfileModel> getEditDetails(@Part("name") RequestBody name,
                                          @Part("username") RequestBody username,
                                          @Part("gender") RequestBody gender,
                                          @Part("dob") RequestBody dob,
                                          @Part("mobile") RequestBody mobile,
                                          @Part("email") RequestBody email,
                                          @Part("password") RequestBody password,
                                          @Part("device_id") RequestBody device_id,
                                          @Part("firebase_id") RequestBody firebase_id,
                                          @Part("country") RequestBody country,
                                          @Part("state") RequestBody state,
                                          @Part("city") RequestBody city,
                                          @Part MultipartBody.Part file);
}