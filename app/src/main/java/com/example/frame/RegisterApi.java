package com.example.frame;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterApi {
    @FormUrlEncoded
    @POST("create_user")
    Call<ResponseBody> createUser(@Field("name") String name,
                                  @Field("mobile") String mobile,
                                  @Field("email") String email,
                                  @Field("username") String username,
                                  @Field("password") String password,
                                  @Field("confirmPassword") String confirmPassword );


}
