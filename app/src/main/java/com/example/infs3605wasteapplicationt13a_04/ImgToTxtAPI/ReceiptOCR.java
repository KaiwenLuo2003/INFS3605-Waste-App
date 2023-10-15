package com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import io.grpc.okhttp.internal.Credentials;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ReceiptOCR{

    private static MediaType MEDIA_TYPE_JPG;
    private static final String ocrModelId = "49289810-b2ea-4227-8e77-244ec6aec526";
    private Gson gson = new Gson();

    private Response response;

    public ReceiptOCR() throws IOException, JSONException {

        //need to decode Response to JSON
//        Response receiptModel = getReceiptModel();
//        System.out.println(receiptModel);


    }

    //get the AI model from Nanonets
    public Response getReceiptModel() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://app.nanonets.com/api/v2/OCR/Model/" + ocrModelId)
                .get()
                .addHeader("authorization", Credentials.basic("41d2114f-6a73-11ee-b75c-9ab569923c64", ""))
                .build();

        return response = client.newCall(request).execute();
//        return new JSONObject(String.valueOf(response)); //ultimately return response in whatever format it needs to be in
    }

    //imgFileName - "image/jpeg" or smth, might just literally be the image file name
    //imgFilePath - "REPLACE_IMAGE_PATH.jpg"
    public JsonObject getReceiptData(String imgFileName, String imgFilePath) throws IOException, JSONException {
        MEDIA_TYPE_JPG = MediaType.parse(imgFileName);

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("data", "[{\"filename\":\"REPLACE_IMAGE_FILENAME.jpg\", \"object\": [{\"name\":\"category1\", \"ocr_text\":\"text inside the bounding box\", \"bndbox\": {\"xmin\": 1,\"ymin\": 1,\"xmax\": 100, \"ymax\": 100}}]}]")
                .addFormDataPart("file", imgFilePath, RequestBody.create(MEDIA_TYPE_JPG, new File(imgFilePath)))
                .build();

        Request request = new Request.Builder()
                .url("https://app.nanonets.com/api/v2/OCR/Model/" + "49289810-b2ea-4227-8e77-244ec6aec526" + "/UploadFile/")
                .post(requestBody)
                .addHeader("Authorization", Credentials.basic("41d2114f-6a73-11ee-b75c-9ab569923c64", ""))
                .build();

        response = client.newCall(request).execute();
//        return gson.toJson(response);
        return null;
    }

}
