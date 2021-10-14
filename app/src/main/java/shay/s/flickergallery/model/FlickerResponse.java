package shay.s.flickergallery.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlickerResponse {

    @SerializedName("photos")
    private PhotosAndInfo photosAndInfo;

    public PhotosAndInfo getPhotosAndInfo() {
        return photosAndInfo;
    }

    @NonNull
    @Override
    public String toString() {
        return "FlickerResponse{" +
                "photosAndInfo=" + photosAndInfo +
                '}';
    }

    public static class PhotosAndInfo{

        private int page;
        private int pages;
        private int total;

        @SerializedName("photo")
        private List<FlickerPhoto> photos;

        @NonNull
        @Override
        public String toString() {
            return "\nPhotosAndInfo{" +
                    "page=" + page +
                    ", pages=" + pages +
                    ", total=" + total +
                    ", photos=" + photos +
                    '}';
        }
    }
}
