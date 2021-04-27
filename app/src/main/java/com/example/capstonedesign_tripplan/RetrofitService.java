package com.example.capstonedesign_tripplan;


import com.example.capstonedesign_tripplan.data.LocationPOJO;
import com.squareup.okhttp.MediaType;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    @Headers("Authorization: KakaoAK 929dabd5f2654ceec48e769482fdcac3")
    @GET("local/search/keyword.json?")
    Observable<LocationPOJO> searchLocation
            (@Query("query") String keyword,
             @Query("page") int page);
}
