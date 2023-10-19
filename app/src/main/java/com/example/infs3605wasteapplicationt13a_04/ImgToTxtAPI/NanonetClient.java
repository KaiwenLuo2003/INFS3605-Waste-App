package com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI;

import java.util.Base64;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class NanonetClient {
    private static final String BASE_URL = "https://app.nanonets.com";
//    private static final String apiKey = "41d2114f-6a73-11ee-b75c-9ab569923c64";
    private static final String apiKey = "2dccb768-6e4f-11ee-9011-8676698a674c";
    private static final String modelId = "49289810-b2ea-4227-8e77-244ec6aec526";

    public interface NanonetApiService {
        //GET: the model number
        @GET("api/v2/OCR/Model/" + modelId)
        Call<ResponseBody> getModel(
                @Header("Authorization") String apiKey
        );


        //POST: upload the image and receive the results of the uploaded image
        //    @Headers("api-key: " + key)
        //    @Multipart
        @POST("api/v2/OCR/Model/" + modelId + "/LabelFile")
        Call<ResponseBody> postReceiptData(
                @Header("Authorization") String apiKey,
                @Body RequestBody image
        );


        //OkHttpClient with Interceptor
        public static Retrofit getClient() {
            byte[] encodedBytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                encodedBytes = Base64.getEncoder().encode(apiKey.getBytes());
            }
            String encodedKey = new String(encodedBytes);

            // Create an OkHttpClient with an interceptor to add your API key to the headers
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", "Bearer " + encodedKey)
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
    }

}
