package app.biblion.retrofit;


import app.biblion.model.ArticalModel;
import app.biblion.model.HomeModel;
import app.biblion.model.LoginModel;
import app.biblion.model.MyLibraryBookModel;
import app.biblion.model.RegisterModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("signup")
    Call<RegisterModel> getRegisterDetails(@Field("email") String email,
                                           @Field("password") String password,
                                           @Field("firebase_id") String firebase_id,
                                           @Field("device_id") String device_id,
                                           @Field("myname") String myname,
                                           @Field("dob") String dob,
                                           @Field("gender") String gender,
                                           @Field("username") String username,
                                           @Field("mobile") String mobile);


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
}