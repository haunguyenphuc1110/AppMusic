package com.example.appmusic.Service;

import com.example.appmusic.Model.Album;
import com.example.appmusic.Model.BaiHat;
import com.example.appmusic.Model.ChuDe;
import com.example.appmusic.Model.ChuDeTheLoai;
import com.example.appmusic.Model.Playlist;
import com.example.appmusic.Model.QuangCao;
import com.example.appmusic.Model.TheLoai;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    //Get data banner
    @GET("Server/songbanner.php")
    Call<List<QuangCao>> getDataBanner();

    //Get data playlist
    @GET("Server/playlistdisplay.php")
    Call<List<Playlist>> getPlaylistRandom();

    //Get data category music
    @GET("Server/themeandkinddisplay.php")
    Call<ChuDeTheLoai> getChuDeTheLoai();

    //Get data album
    @GET("Server/albumhot.php")
    Call<List<Album>> getAlbum();

    //Get data most liked songs
    @GET("Server/baihatyeuthich.php")
    Call<List<BaiHat>> getBaiHatHot();

    //Get list song from banner
    @FormUrlEncoded
    @POST("Server/danhsachbaihat.php")
    Call<List<BaiHat>> getDanhSachBaiHatTheoQuangCao(@Field("idquangcao") String idquangcao);

    //Get list song from playlist
    @FormUrlEncoded
    @POST("Server/danhsachbaihat.php")
    Call<List<BaiHat>> getDanhSachBaiHatTheoPLaylist(@Field("idplaylist") String idplaylist);

    //Get list song from the loai
    @FormUrlEncoded
    @POST("Server/danhsachbaihat.php")
    Call<List<BaiHat>> getDanhSachBaiHatTheoTheLoai(@Field("idtheloai") String idtheloai);

    //Get list song from album
    @FormUrlEncoded
    @POST("Server/danhsachbaihat.php")
    Call<List<BaiHat>> getDanhSachBaiHatTheoAlbum(@Field("idalbum") String idalbum);

    //Get all playlist
    @GET("Server/danhsachcacplaylist.php")
    Call<List<Playlist>> getAllPlaylist();

    //Get all chu de
    @GET("Server/danhsachchude.php")
    Call<List<ChuDe>> getAllChuDe();

    //Get list theo loai from chu de
    @FormUrlEncoded
    @POST("Server/theloaitheochude.php")
    Call<List<TheLoai>> getDanhSachTheLoaiTheoChuDe(@Field("idchude") String idchude);

    //Get all album
    @GET("Server/danhsachalbum.php")
    Call<List<Album>> getAllAlbum();

    //Update luot thich cho bai hat hot
    @FormUrlEncoded
    @POST("Server/updateluotthich.php")
    Call<String> updateLuotThich(@Field("luotthich") String luotthich, @Field("idbaihat") String idbaihat);

    //Search bai hat
    @FormUrlEncoded
    @POST("Server/searchbaihat.php")
    Call<List<BaiHat>> searchBaiHat(@Field("key") String key);

    //Insert user
    @FormUrlEncoded
    @POST("Server/insertuser.php")
    Call<String> insertUser(@Field("userid") String userid, @Field("username") String username, @Field("useremail") String useremail, @Field("userlink") String userlink);

    //Insert user's favorite song
    @FormUrlEncoded
    @POST("Server/insertuserfavoritesong.php")
    Call<String> insertUserFavoriteSong(@Field("userid") String userid, @Field("songid") String songid);

    //Insert user's favorite song
    @FormUrlEncoded
    @POST("Server/removeuserfavoritesong.php")
    Call<String> removeUserFavoriteSong(@Field("userid") String userid, @Field("songid") String songid);

    //Insert user's favorite song
    @FormUrlEncoded
    @POST("Server/userfavoritesong.php")
    Call<List<BaiHat>> getUserFavoriteSong(@Field("userid") String userid);
}
