package shay.s.flickergallery.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shay.s.flickergallery.model.FlickerPhoto;
import shay.s.flickergallery.model.FlickerResponse;
import shay.s.flickergallery.repository.Repository;

public class HomeViewModel extends ViewModel {
    private static final String SUFFIX_PHOTO_SIZE_M = "_m"; //medium (240px)
    private static final String SUFFIX_PHOTO_SIZE_Q = "_q"; // thumbnail (150px) + square shape
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
                if (flickerResponse == null)
                    return;

                FlickerResponse.PhotosAndInfo photosAndInfo = flickerResponse.getPhotosAndInfo();

                //Change the image size according to flicker api's URLs, for it to fit a thumbnail size.
                for (FlickerPhoto flickerPhoto : photosAndInfo.getPhotos()) {
                    String urlStr = flickerPhoto.getUrlStr();
                    if (urlStr == null)
                        return;
                    String[] splittedStr = urlStr.split(SUFFIX_PHOTO_SIZE_M);
                    String newImageSizeStr = splittedStr[0] + SUFFIX_PHOTO_SIZE_Q + splittedStr[1];
                    flickerPhoto.setUrlStr(newImageSizeStr);
                }

                photosAndInfoLiveData.postValue(photosAndInfo);
            }

            @Override
            public void onFailure(@NonNull Call<FlickerResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}