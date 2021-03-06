package shay.s.flickergallery.repository;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import shay.s.flickergallery.model.FlickrResponse;

//Shared object to manage the data fetching.
public class Repository {
    public static final String API_KEY = "aabca25d8cd75f676d3a74a72dcebf21";

    //Query string param for getting the most recent photos uploaded to flicker.
    public static final String METHOD_GET_RECENT = "flickr.photos.getRecent";

    private static Repository repo;

    //The API service
    private final FlickrService service;

    private Repository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://api.flickr.com/services/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(FlickrService.class);
    }

    public static Repository getRepo() {
        if (repo == null)
            repo = new Repository();
        return repo;
    }

    public Call<FlickrResponse> getRecentPhotos(String method) {
        return service.getRecentPhotos(method);
    }

    public Call<FlickrResponse> getNextPage(String method, int page) {
        return service.getNextPage(method, page);
    }
}
