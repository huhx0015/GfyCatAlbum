package com.gfycat.album.interfaces;

import com.gfycat.album.models.CreateGfycatPojo;
import com.gfycat.album.models.GetAlbumFoldersPojo;
import com.gfycat.album.models.GetBookmarkPojo;
import com.gfycat.album.models.GrantRequest;
import com.gfycat.album.models.GrantResponsePojo;
import com.gfycat.album.models.MessageResponsePojo;
import com.gfycat.album.models.SearchResponsePojo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Michael Yoon Huh on 1/28/2017.
 */

public interface RetrofitInterface {

    // CLIENT CREDENTIALS GRANT: grant_type = "client_credentials"
    @POST("oauth/token")
    Call<GrantResponsePojo> getToken(@Body GrantRequest request);

    // LOGIN GRANT: grant_type = "password"
    @POST("oauth/token")
    Call<GrantResponsePojo> loginUser(@Body GrantRequest request);

    // GET BOOKMARK FOLDERS:
    @GET("me/bookmark-folders")
    Call<GetBookmarkPojo> getBookmarkFolders();

    // GET BOOKMARK BY ID:
    @GET("me/bookmarks/{gfyId}")
    Call<MessageResponsePojo> getBookmark(@Path("gfyId") String gfyId);

    // SAVE BOOKMARK:
    @PUT("me/bookmarks/{gfyId}")
    Call<MessageResponsePojo> saveBookmark(@Path("gfyId") String gfyId);

    // GET ALBUM FOLDERS:
    @GET("me/album-folders")
    Call<GetAlbumFoldersPojo> getAlbumFolders();

    // GET ALBUM BY ID:
    @GET("users/{userId}/albums/{albumId}")
    Call<MessageResponsePojo> getAlbum(@Path("userId") String userId, @Path("albumId") String albumId);

    // CREATE GFYCAT:
    @POST("gfycats")
    Call<CreateGfycatPojo> createGfycat(@Field("fetchUrl") String fetchUrl,
                                        @Field("title") String title);

    // SEARCH:
    @GET("gfycats/search?search_text={keywords}")
    Call<SearchResponsePojo> searchGfy(@Path("keywords") String keywords);
}