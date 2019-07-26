package com.example.appmusic.Service;

public class APIService {
    public static String base_url = "https://phuchau1110.000webhostapp.com/";

    public static DataService getService(){
        return APIRetrofitClient.getClient(base_url).create(DataService.class);
    }
}
