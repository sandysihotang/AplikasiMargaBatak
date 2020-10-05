package stud11417031.develops.projecttestwithlaravel.Connection;

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
import retrofit2.http.Path;
import retrofit2.http.Url;
import stud11417031.develops.projecttestwithlaravel.model.LoginResponse;
import stud11417031.develops.projecttestwithlaravel.model.Marga;
import stud11417031.develops.projecttestwithlaravel.model.MargaResponse;
import stud11417031.develops.projecttestwithlaravel.model.ResponeForSignUp;
import stud11417031.develops.projecttestwithlaravel.model.ResponseMarga;
import stud11417031.develops.projecttestwithlaravel.model.ResponseMargaDetail;
import stud11417031.develops.projecttestwithlaravel.model.ResponseUser;
import stud11417031.develops.projecttestwithlaravel.model.User;

public interface Api {
    @Multipart
    @POST("createuser")
    Call<ResponeForSignUp> createUser(
            @Part("username") RequestBody username,
            @Part("firstname") RequestBody firstname,
            @Part("password") RequestBody password,
            @Part("lastname") RequestBody lastname
    );

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("user")
    Call<ResponseUser> getUser();

    @GET("uploads/{name}")
    Call<ResponseBody> getUrl(
            @Path("name") String url
    );

    @Multipart
    @POST("createMarga")
    Call<MargaResponse>sendMarga(
            @Part MultipartBody.Part photo,
            @Part("marga") RequestBody marga,
            @Part("cerita") RequestBody cerita
    );

    @GET("marga")
    Call<ResponseMarga>getAllMarga();

    @GET("detail/{id}")
    Call<ResponseMargaDetail>getMarga(
            @Path("id") int id
    );
}
