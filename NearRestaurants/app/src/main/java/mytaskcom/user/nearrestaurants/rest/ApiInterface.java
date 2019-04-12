package mytaskcom.user.nearrestaurants.rest;

import mytaskcom.user.nearrestaurants.model.RestApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("place/textsearch/json")
    Call<RestApiResponse> getRestaruntDetails(@Query("query") String quer, @Query("key") String apiKey);


//    @GET("place/nearbysearch/json")
//    Call<RestApiResponse> getRestaruntDetails(@Query("location") String quer,@Query("type") String type, @Query("key") String apiKey);
//}
}