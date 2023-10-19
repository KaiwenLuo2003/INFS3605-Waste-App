package com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI;

import okhttp3.Credentials;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitOCRCall {

    static final String apiKey = "41d2114f-6a73-11ee-b75c-9ab569923c64";
    static final String modelId =  "49289810-b2ea-4227-8e77-244ec6aec526";
    static final String BASE_URL = "https://app.nanonets.com";
    static final String password = "";




    //GET: the model number
    @GET("api/v2/OCR/Model/" + modelId)
    Call<ResponseBody> getModel(
            @Header ("Authorization") String apiKey
    );


    //POST: upload the image and receive the results of the uploaded image
//    @Headers("api-key: " + key)
//    @Multipart
    @Headers("api-key: " + apiKey)
    @POST("api/v2/OCR/Model/" + modelId + "/LabelFile")
    Call<ResponseBody> postReceiptData(
            @Body RequestBody image
    );


    //OkHttpClient with Interceptor
    public static Retrofit getClient() {
        // Create an OkHttpClient with an interceptor to add your API key to the headers
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization", "Bearer " + apiKey)
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        });

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

/*    TODO: try get POST method working
      TODO: figure out how to access pic taken from camera
      TODO: figure out how to get URL, pic details etc. from the phone
      TODO: then translate JSONObject into text on to the screen
*/


}
