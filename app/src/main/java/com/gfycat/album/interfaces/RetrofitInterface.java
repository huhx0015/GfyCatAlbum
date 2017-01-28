package com.gfycat.album.interfaces;

import com.gfycat.album.models.GrantResponsePojo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * Created by Michael Yoon Huh on 1/28/2017.
 */

public interface RetrofitInterface {

    // CLIENT CREDENTIALS GRANT: grant_type = "client_credentials"
    @POST
    Call<GrantResponsePojo> getToken(@Field("grant_type") String grantType,
                                           @Field("client_id") String clientId,
                                           @Field("client_secret") String clientSecret);

    // LOGIN GRANT: grant_type = "password"
    @POST
    Call<GrantResponsePojo> loginUser(@Field("grant_type") String grantType,
                                      @Field("client_id") String clientId,
                                      @Field("client_secret") String clientSecret,
                                      @Field("username") String username,
                                      @Field("password") String password);


}
