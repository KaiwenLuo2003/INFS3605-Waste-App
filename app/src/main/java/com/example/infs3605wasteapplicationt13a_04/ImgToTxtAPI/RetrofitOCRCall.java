package com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface RetrofitOCRCall {
    @GET("https://app.nanonets.com/api/v2/OCR/Model/49289810-b2ea-4227-8e77-244ec6aec526")
    Call<ResponseBody> getModel();

/*    TODO: try get POST method working
      TODO: figure out how to access pic taken from camera
      TODO: figure out how to get URL, pic details etc. from the phone
      TODO: figure out how to send out the deets through POST method
*/


}
