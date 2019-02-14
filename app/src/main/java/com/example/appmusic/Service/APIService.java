package com.example.appmusic.Service;

public class APIService {
    private static String base_url = "https://phuchau1110.000webhostapp.com/Server/";

    public static DataService getService(){
        return APIRetrofitClient.getClient(base_url).create(DataService.class);
    }
}
