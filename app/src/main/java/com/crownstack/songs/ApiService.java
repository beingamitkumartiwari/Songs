package com.crownstack.songs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by amitkumartiwari on 2/9/2018.
 */

public interface ApiService {

    @GET("search")
    Call<SongListPojo> getAllSongs(@Query("term") String artistName);

}
