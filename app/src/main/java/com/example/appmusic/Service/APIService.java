package com.example.appmusic.Service;

public class APIService {
    public static String base_url = "http://192.168.43.205:81/AppMusic/";

    public static DataService getService(){
        return APIRetrofitClient.getClient(base_url).create(DataService.class);
    }
}
