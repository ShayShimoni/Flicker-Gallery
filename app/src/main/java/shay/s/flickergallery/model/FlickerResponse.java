package shay.s.flickergallery.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

//The complete response from flicker API
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

        @SerializedName("pages")
        private int totalPages;

        @SerializedName("photo")
        private ArrayList<FlickerPhoto> photos;

        public int getPage() {
            return page;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public ArrayList<FlickerPhoto> getPhotos() {
            return photos;
        }


        @NonNull
        @Override
        public String toString() {
            return "PhotosAndInfo{" +
                    "page=" + page +
                    ", totalPages=" + totalPages +
                    ", photos=" + photos +
                    '}';
        }
    }
}
