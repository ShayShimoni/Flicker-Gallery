package shay.s.flickergallery.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class FlickerPhoto {
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
}