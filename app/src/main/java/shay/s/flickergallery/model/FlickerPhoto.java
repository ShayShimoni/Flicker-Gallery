package shay.s.flickergallery.model;

import androidx.annotation.NonNull;

public class FlickerPhoto {
    private int id;
    private String secret;
    private String server;
    private String urlStr;

    public int getId() {
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