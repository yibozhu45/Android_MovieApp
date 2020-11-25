package com.example.project_mobile.ui.main.module;

public class url_handler {

    private String url = "https://www.omdbapi.com/?apikey=39c77b5b";

    public url_handler(String title, String type, String year) {
        if(!title.isEmpty())
        {
            title = "&s=" + title;
            url += title;
        }

        if(!type.isEmpty())
        {
            type = "&type=" + type;
            url += type;
        }

        if(!year.isEmpty())
        {
            year = "&y=" + year;
            url += year;
        }
    }

    public String getUrl() {
        return url;
    }
}
