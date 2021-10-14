package shay.s.flickergallery.ui.home;

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
    private static final String SUFFIX_PHOTO_SIZE_M = "_m"; //medium (240px)
    private static final String SUFFIX_PHOTO_SIZE_Q = "_q"; // thumbnail (150px) + square shape
    private final MutableLiveData<FlickerResponse.PhotosAndInfo> photosAndInfoLiveData;
    private final MutableLiveData<FlickerResponse.PhotosAndInfo> nextPageLiveData;

    public HomeViewModel() {
        photosAndInfoLiveData = new MutableLiveData<>();
        nextPageLiveData = new MutableLiveData<>();
        initList();
    }

    public LiveData<FlickerResponse.PhotosAndInfo> getPhotosAndInfoLiveData() {
        return photosAndInfoLiveData;
    }

    public LiveData<FlickerResponse.PhotosAndInfo> getNextPageLiveData() {
        return nextPageLiveData;
    }

    private void initList(){
        Call<FlickerResponse> recentPhotos = Repository.getRepo().getRecentPhotos(Repository.METHOD_GET_RECENT);
        recentPhotos.enqueue(new Callback<FlickerResponse>() {
            @Override
            public void onResponse(@NonNull Call<FlickerResponse> call, @NonNull Response<FlickerResponse> response) {
                FlickerResponse flickerResponse = response.body();
                if (flickerResponse == null)
                    return;

                FlickerResponse.PhotosAndInfo photosAndInfo = flickerResponse.getPhotosAndInfo();
                changePhotoToThumbnail(photosAndInfo.getPhotos());

                photosAndInfoLiveData.postValue(photosAndInfo);
            }

            @Override
            public void onFailure(@NonNull Call<FlickerResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //Change the image size (according to flicker api's URLs) for it to fit a thumbnail size.
    private void changePhotoToThumbnail(List<FlickerPhoto> photos){
        for (FlickerPhoto flickerPhoto : photos) {
            String urlStr = flickerPhoto.getUrlStr();
            if (urlStr == null)
                return;
            String[] splittedStr = urlStr.split(SUFFIX_PHOTO_SIZE_M);
            String newImageSizeStr = splittedStr[0] + SUFFIX_PHOTO_SIZE_Q + splittedStr[1];
            flickerPhoto.setUrlStr(newImageSizeStr);
        }
    }

    public void getNextPage(String method, int page){
        Repository.getRepo().getNextPage(method, page).enqueue(new Callback<FlickerResponse>() {
            @Override
            public void onResponse(@NonNull Call<FlickerResponse> call, @NonNull Response<FlickerResponse> response) {
                FlickerResponse flickerResponse = response.body();
                if (flickerResponse == null)
                    return;
                FlickerResponse.PhotosAndInfo photosAndInfo = flickerResponse.getPhotosAndInfo();
                changePhotoToThumbnail(photosAndInfo.getPhotos());

                nextPageLiveData.postValue(photosAndInfo);
            }

            @Override
            public void onFailure(@NonNull Call<FlickerResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}