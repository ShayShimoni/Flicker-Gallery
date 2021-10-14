package shay.s.flickergallery.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import shay.s.flickergallery.model.FlickerPhoto;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<FlickerPhoto>> imagesLiveData;

    public HomeViewModel() {
        imagesLiveData = new MutableLiveData<>();
    }

    public LiveData<List<FlickerPhoto>> getImagesLiveData() {
        return imagesLiveData;
    }
}