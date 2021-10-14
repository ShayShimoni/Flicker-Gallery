package shay.s.flickergallery.ui.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shay.s.flickergallery.model.FlickerPhoto;
import shay.s.flickergallery.model.FlickerResponse;
import shay.s.flickergallery.repository.Repository;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<FlickerResponse.PhotosAndInfo> photosAndInfoLiveData;

    public HomeViewModel() {
        photosAndInfoLiveData = new MutableLiveData<>();
        getData();
    }

    public LiveData<FlickerResponse.PhotosAndInfo> getPhotosAndInfoLiveData() {
        return photosAndInfoLiveData;
    }

    private void getData(){
        Call<FlickerResponse> recentPhotos = Repository.getRepo().getRecentPhotos(Repository.METHOD_GET_RECENT);
        recentPhotos.enqueue(new Callback<FlickerResponse>() {
            @Override
            public void onResponse(@NonNull Call<FlickerResponse> call, @NonNull Response<FlickerResponse> response) {
                FlickerResponse flickerResponse = response.body();
                if (flickerResponse == null){
                    Log.d("flickerResponse","Null Response");
                    return;
                }
                FlickerResponse.PhotosAndInfo photosAndInfo = flickerResponse.getPhotosAndInfo();
                photosAndInfoLiveData.postValue(photosAndInfo);
            }

            @Override
            public void onFailure(@NonNull Call<FlickerResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}