package com.gfycat.album.interfaces;

import com.gfycat.album.models.CreateGfycatPojo;
import com.gfycat.album.models.CreateGfycatRequest;
import com.gfycat.album.models.GetAlbumFoldersPojo;
import com.gfycat.album.models.GetBookmarkPojo;
import com.gfycat.album.models.GfycatPojo;
import com.gfycat.album.models.GrantRequest;
import com.gfycat.album.models.GrantResponsePojo;
import com.gfycat.album.models.MessageResponsePojo;
import com.gfycat.album.models.SearchResponsePojo;
import com.gfycat.album.models.StatusGfycatPojo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    Call<GetBookmarkPojo> getBookmarkFolders(@Header("Authorization") String token);

    // GET BOOKMARK BY ID:
    @GET("me/bookmarks/{gfyId}")
    Call<MessageResponsePojo> getBookmark(@Header("Authorization") String token, @Path("gfyId") String gfyId);

    // SAVE BOOKMARK:
    @PUT("me/bookmarks/{gfyId}")
    Call<MessageResponsePojo> saveBookmark(@Header("Authorization") String token, @Path("gfyId") String gfyId);

    // GET ALBUM FOLDERS:
    @GET("me/album-folders")
    Call<GetAlbumFoldersPojo> getAlbumFolders(@Header("Authorization") String token);

    // GET ALBUM BY ID:
    @GET("users/{userId}/albums/{albumId}")
    Call<MessageResponsePojo> getAlbum(@Header("Authorization") String token, @Path("userId") String userId, @Path("albumId") String albumId);

    // CREATE GFYCAT:
    @POST("gfycats")
    Call<CreateGfycatPojo> createGfycat(@Header("Authorization") String token, @Body CreateGfycatRequest request);

    // GFYCAT UPLOAD STATUS:
    @GET("gfycats/fetch/status/{gfyname}")
    Call<StatusGfycatPojo> checkUploadStatus(@Header("Authorization") String token, @Path("gfyname") String gfyname);

    // GET GFYCAT BY ID:
    @GET("gfycats/{gfyid}")
    Call<GfycatPojo> getGfyCat(@Header("Authorization") String token, @Path("gfyid") String gfyId);

    // SEARCH:
    @GET("gfycats/search?search_text={keywords}")
    Call<SearchResponsePojo> searchGfy(@Path("keywords") String keywords);
}