package com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public interface RetrofitOCRCall {
    @GET("")
    Call<Response>
}
