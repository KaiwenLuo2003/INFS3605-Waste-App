package com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface RetrofitOCRCall {
    @GET("https://app.nanonets.com/api/v2/OCR/Model/49289810-b2ea-4227-8e77-244ec6aec526")
    Call<ResponseBody> getModel();

}
