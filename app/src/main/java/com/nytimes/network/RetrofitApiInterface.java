package com.nytimes.network;

import com.nytimes.model.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitApiInterface {

    @GET("svc/mostpopular/v2/mostviewed/{section}/{period}.json")
    Call<ArticleResponse> getArticles(@Path("section") String section,
                                      @Path("period") String period,
                                      @Query("api-key") String apiKey);
}
