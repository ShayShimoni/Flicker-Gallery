package shay.s.flickergallery.repository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import shay.s.flickergallery.model.FlickrResponse;

//Here all service methods are written
public interface FlickrService {

    @GET("rest/?per_page=20&extras=url_s&format=json&nojsoncallback=1&api_key=" + Repository.API_KEY)
    Call<FlickrResponse> getRecentPhotos(@Query("method") String method);

    @GET("rest/?per_page=20&extras=url_s&format=json&nojsoncallback=1&api_key=" + Repository.API_KEY)
    Call<FlickrResponse> getNextPage(@Query("method") String method, @Query("page") int page);

}
