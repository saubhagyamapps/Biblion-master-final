package app.biblion.retrofit;


import app.biblion.model.AllSongListModel;
import app.biblion.model.ArticalDetailsModel;
import app.biblion.model.ArticalModel;
import app.biblion.model.BookDetailsModel;
import app.biblion.model.CategoryModel;
import app.biblion.model.DevotionModel;
import app.biblion.model.EditProfileModel;
import app.biblion.model.HomeModel;
import app.biblion.model.LikeModel;
import app.biblion.model.LoginModel;
import app.biblion.model.MyLibraryBookModel;
import app.biblion.model.RegisterModel;
import app.biblion.model.ResetPasswordModel;
import app.biblion.model.SearchModel;
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
                                           @Part("language") RequestBody language,
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
                                                 @Field("googleimage") String googleimage,
                                                 @Field("language") String language);


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
    @POST("getarticals")
    Call<ArticalDetailsModel> getArticalDetails(@Field("artical_id") int page);

    @FormUrlEncoded
    @POST("getbooks")
    Call<MyLibraryBookModel> getMyLibraryBook(@Field("page") int page);

    @GET("homeapi")
    Call<HomeModel> getHomeList();

    @GET()
    @Streaming
    Call<ResponseBody> downloadImage(@Url String fileUrl);


    @FormUrlEncoded
    @POST("reset")
    Call<ResetPasswordModel> resetPassword(@Field("id") String id,
                                           @Field("oldpass") String oldpass,
                                           @Field("newpass") String newpass);

    @Multipart
    @POST("editprofile")
    Call<EditProfileModel> getEditDetailsWithImag(@Part("id") RequestBody id,
                                                  @Part("name") RequestBody name,
                                                  @Part("email") RequestBody email,
                                                  @Part("gender") RequestBody gender,
                                                  @Part("dob") RequestBody dob,
                                                  @Part("mobile") RequestBody mobile,
                                                  @Part("country") RequestBody country,
                                                  @Part("state") RequestBody state,
                                                  @Part("city") RequestBody city,
                                                  @Part("language") RequestBody language,
                                                  @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("editprofile")
    Call<EditProfileModel> getEditDetails(@Field("id") String id,
                                          @Field("name") String name,
                                          @Field("email") String email,
                                          @Field("gender") String gender,
                                          @Field("dob") String dob,
                                          @Field("mobile") String mobile,
                                          @Field("country") String country,
                                          @Field("state") String state,
                                          @Field("city") String city,
                                          @Field("language") String language);

    @FormUrlEncoded
    @POST("allsonglist")
    Call<AllSongListModel> getSongList(@Field("page") int page);

    @GET("uploading")
    Call<DevotionModel> getDevotiondata();

    @FormUrlEncoded
    @POST("getarticals")
    Call<ArticalDetailsModel> getArticledetails(@Field("id") String id);

    @FormUrlEncoded
    @POST("like")
    Call<LikeModel> getLikes(@Field("user_id") String user_Id,
                             @Field("artical_id") String article_Id,
                             @Field("likes") int likes);

    @FormUrlEncoded
    @POST("category_book")
    Call<CategoryModel> getCategoryBook(@Field("category") String category,
                                        @Field("page") int page);

    @FormUrlEncoded
    @POST("getbook")
    Call<BookDetailsModel> getBookDetails(@Field("id") String category);

    @FormUrlEncoded
    @POST("search")
    Call<SearchModel> getSearchList(@Field("type") String type,
                                    @Field("value") String value,
                                    @Field("page") int page);
}