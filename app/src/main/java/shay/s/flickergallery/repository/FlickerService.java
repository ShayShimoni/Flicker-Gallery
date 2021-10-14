package shay.s.flickergallery.repository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import shay.s.flickergallery.model.FlickerResponse;

public interface FlickerService {

    @GET("rest/?per_page=20&extras=url_s&format=json&nojsoncallback=1&api_key=" + Repository.API_KEY)
    Call<FlickerResponse> getRecentPhotos(@Query("method") String method);

}
