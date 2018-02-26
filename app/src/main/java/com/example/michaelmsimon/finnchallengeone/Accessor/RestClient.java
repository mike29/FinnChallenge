package com.example.michaelmsimon.finnchallengeone.Accessor;

import android.content.Context;
import android.util.Log;
import com.example.michaelmsimon.finnchallengeone.Model.Product;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Michael M. Simon on 2/12/2018.
 */
// Get data from REST API and create products
public class RestClient extends AsyncHttpClient {

    private Context context;
    private OnClientApiExcuted RestClientListener;
    private AsyncHttpClient asyncHttpClient;
    private RequestParams requestParams;
    private JSONArray reader;
    private JSONObject images;
    //Holds Complete Uri for images
    private String imageFinalURI;
    private JSONObject prices;
    private int priceValue;
    private String locations;
    private String descriptions;
    private ArrayList<Product> productModelArrayList = new ArrayList<>();
    //Base Uri with no end point.Used for images
    final String PRE_URL = "https://images.finncdn.no/dynamic/480x360c/";

    public RestClient(Context context, OnClientApiExcuted listener) {
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        this.context = context;
        this.RestClientListener = listener;
    }

    public void excuter(String uri) {
        asyncHttpClient.get(uri, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    reader = response.getJSONArray("items");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < reader.length(); i++) {

                    try {
                        descriptions = reader.getJSONObject(i).getString("description");
                        locations = reader.getJSONObject(i).getString("location");
                        prices = reader.getJSONObject(i).getJSONObject("price");
                        images = reader.getJSONObject(i).getJSONObject("image");

                        //Parse to int string value of the price
                        priceValue = Integer.parseInt(prices.getString("value"));
                        //Concatenate the image uri
                        imageFinalURI = PRE_URL + images.getString("url");

                    } catch (JSONException e) {

                        //Check if price holds value, if not set the value to null
                        if (e.getMessage().equals("No value for price")) {
                            try {
                                prices.put("value", 0);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    //Make Product with ID
                    productModelArrayList.add(new Product(descriptions, locations, priceValue, imageFinalURI, "", Product.createID()));

                }
                RestClientListener.taskCompleted(productModelArrayList);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("OnFailure", "OnFailure");
            }
        });
    }
}
