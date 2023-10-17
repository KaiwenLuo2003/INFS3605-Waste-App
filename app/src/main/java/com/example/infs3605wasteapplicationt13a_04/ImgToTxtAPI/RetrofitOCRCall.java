package com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitOCRCall {

    static final String key = "41d2114f-6a73-11ee-b75c-9ab569923c64";
    static final String modelId =  "49289810-b2ea-4227-8e77-244ec6aec526";



    //GET: the model number
    @Headers("api-key: " + key)
    @GET("api/v2/OCR/Model/" + modelId)
    Call<ResponseBody> getModel();


    //POST: upload the image and receive the results of the uploaded image
    @Headers("api-key: " + key)
    @Multipart
    @POST("api/v2/OCR/Model/49289810-b2ea-4227-8e77-244ec6aec526/LabelFile")
    Call<ResponseBody> getReceiptData(
            @Part MultipartBody.Part image
    );


/*    TODO: try get POST method working
      TODO: figure out how to access pic taken from camera
      TODO: figure out how to get URL, pic details etc. from the phone
*/


}
