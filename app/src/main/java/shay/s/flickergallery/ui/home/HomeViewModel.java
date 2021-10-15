package shay.s.flickergallery.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shay.s.flickergallery.model.FlickrPhoto;
import shay.s.flickergallery.model.FlickrResponse;
import shay.s.flickergallery.repository.Repository;

public class HomeViewModel extends ViewModel {
    private static final String SUFFIX_PHOTO_SIZE_M = "_m"; //medium (240px)
    private static final String SUFFIX_PHOTO_SIZE_Q = "_q"; // thumbnail (150px) + square shape
    private static final String SUFFIX_PHOTO_SIZE_B = "_b"; // large (1024px)
    private final MutableLiveData<FlickrResponse.PhotosAndInfo> photosAndInfoLiveData;
    private final MutableLiveData<FlickrResponse.PhotosAndInfo> nextPageLiveData;
    private final MutableLiveData<FlickrPhoto> selectedPhotoLiveData;

    public HomeViewModel() {
        photosAndInfoLiveData = new MutableLiveData<>();
        nextPageLiveData = new MutableLiveData<>();
        selectedPhotoLiveData = new MutableLiveData<>();
        initList();
    }

    public LiveData<FlickrResponse.PhotosAndInfo> getPhotosAndInfoLiveData() {
        return photosAndInfoLiveData;
    }

    public LiveData<FlickrResponse.PhotosAndInfo> getNextPageLiveData() {
        return nextPageLiveData;
    }

    public MutableLiveData<FlickrPhoto> getSelectedPhotoLiveData() {
        return selectedPhotoLiveData;
    }

    private void initList() {
        Call<FlickrResponse> recentPhotos = Repository.getRepo().getRecentPhotos(Repository.METHOD_GET_RECENT);
        recentPhotos.enqueue(new Callback<FlickrResponse>() {
            @Override
            public void onResponse(@NonNull Call<FlickrResponse> call, @NonNull Response<FlickrResponse> response) {
                FlickrResponse flickrResponse = response.body();
                if (flickrResponse == null)
                    return;

                FlickrResponse.PhotosAndInfo photosAndInfo = flickrResponse.getPhotosAndInfo();
                changePhotoToThumbnail(photosAndInfo.getPhotos());

                photosAndInfoLiveData.postValue(photosAndInfo);
            }

            @Override
            public void onFailure(@NonNull Call<FlickrResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //Change the image size (according to flicker api's URLs) for it to fit a thumbnail size.
    private void changePhotoToThumbnail(List<FlickrPhoto> photos) {
        for (FlickrPhoto flickrPhoto : photos) {
            String urlStr = flickrPhoto.getUrlStr();
            if (urlStr == null)
                return;
            String[] splittedStr = urlStr.split(SUFFIX_PHOTO_SIZE_M);
            String newImageSizeStr = splittedStr[0] + SUFFIX_PHOTO_SIZE_Q + splittedStr[1];
            flickrPhoto.setUrlStr(newImageSizeStr);
        }
    }

    //Change the image size to a large size.
    public void changePhotoToLargeSize(FlickrPhoto flickrPhoto) {
        String urlStr = flickrPhoto.getUrlStr();
        if (urlStr == null || flickrPhoto.getUrlStr().contains(SUFFIX_PHOTO_SIZE_B))
            return;
        String[] splittedStr = urlStr.split(SUFFIX_PHOTO_SIZE_Q);
        String newImageSizeStr = splittedStr[0] + SUFFIX_PHOTO_SIZE_B + splittedStr[1];
        flickrPhoto.setUrlStr(newImageSizeStr);
    }


    public void getNextPage(String method, int page) {
        Repository.getRepo().getNextPage(method, page).enqueue(new Callback<FlickrResponse>() {
            @Override
            public void onResponse(@NonNull Call<FlickrResponse> call, @NonNull Response<FlickrResponse> response) {
                FlickrResponse flickrResponse = response.body();
                if (flickrResponse == null)
                    return;
                FlickrResponse.PhotosAndInfo photosAndInfo = flickrResponse.getPhotosAndInfo();
                changePhotoToThumbnail(photosAndInfo.getPhotos());

                nextPageLiveData.postValue(photosAndInfo);
            }

            @Override
            public void onFailure(@NonNull Call<FlickrResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}