package shay.s.flickergallery.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class FlickrPhoto implements Parcelable {
    private String  id;
    private String secret;
    private String server;

    @SerializedName("url_s")
    private String urlStr;

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public String getUrlStr() {
        return urlStr;
    }

    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }

    @NonNull
    @Override
    public String toString() {
        return "\nFlickerPhoto{" +
                "id=" + id +
                ", secret='" + secret + '\'' +
                ", server='" + server + '\'' +
                ", urlStr='" + urlStr + '\'' +
                '}';
    }

    protected FlickrPhoto(Parcel in) {
        id = in.readString();
        secret = in.readString();
        server = in.readString();
        urlStr = in.readString();
    }

    //Parcelable implements
    public static final Creator<FlickrPhoto> CREATOR = new Creator<FlickrPhoto>() {
        @Override
        public FlickrPhoto createFromParcel(Parcel in) {
            return new FlickrPhoto(in);
        }

        @Override
        public FlickrPhoto[] newArray(int size) {
            return new FlickrPhoto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(secret);
        dest.writeString(server);
        dest.writeString(urlStr);
    }
}