package kr.or.womanup.nambu.hjy.spaceminiproject;

import java.io.Serializable;

public class APOD implements Serializable {
    String date;
    String explanation;
    String media_type;
    String service_version;
    String title;
    String url;

    public APOD() {}

    public APOD(String date, String explanation, String media_type, String service_version, String title, String url) {
        this.date = date;
        this.explanation = explanation;
        this.media_type = media_type;
        this.service_version = service_version;
        this.title = title;
        this.url = url;
    }

}
