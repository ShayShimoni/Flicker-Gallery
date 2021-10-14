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
        private int pages;
        private int total;

        @SerializedName("photo")
        private ArrayList<FlickerPhoto> photos;

        public int getPage() {
            return page;
        }

        public int getPages() {
            return pages;
        }

        public int getTotal() {
            return total;
        }

        public ArrayList<FlickerPhoto> getPhotos() {
            return photos;
        }

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
