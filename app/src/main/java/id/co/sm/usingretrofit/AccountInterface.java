package id.co.sm.usingretrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AccountInterface {
    @GET("/v2/everything")
    Call<Example> getEverything(@Query("q") String q,
                                @Query("domains") String domains,
                                @Query("language") String language,
                                @Query("sortBy") String sortby,
                                @Query("pageSize") String pageSize,
                                @Query("apiKey") String key);
}
