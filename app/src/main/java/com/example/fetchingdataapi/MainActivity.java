package com.example.fetchingdataapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private MyAdapter myAdapter;

    private ArrayList<ModalClass> list;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        parseJSON();

    }


    private void parseJSON() {

        String url = "https://www.purplle.com/api/shop/cart?version=v2&tstamp=0.06464658925667677";
        String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkZXZpY2VfaWQiOiIxME5xMEM3ZWxxRDhjMXFNRnYiLCJtb2RlX2RldmljZSI6ImRlc2t0b3AiLCJtb2RlX2RldmljZV90eXBlIjoid2ViIiwiaWF0IjoxNzA5NjU0OTA3LCJleHAiOjE3MTc0MzA5MDcsImF1ZCI6IndlYiIsImlzcyI6InRva2VubWljcm9zZXJ2aWNlIn0.-3mDWSd73DSEhM2S1aakIFlmYohpXAuNe6srpsgT2Hk";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, // JSON object request payload
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle successful response
                        // Process the JSON response here
                        try {
                            JSONArray jsonArray = response.getJSONArray("cartItems");

                            for(int i=0; i<jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                JSONObject imageobject = jsonObject.getJSONObject("images");
                                String imageurl = imageobject.getString("smallImage");

                                String name = jsonObject.getString("itemName");
                                int price = jsonObject.getInt("itemId");

                                list.add(new ModalClass(imageurl, name, price));

                            }

                            myAdapter = new MyAdapter(list, MainActivity.this);
                            recyclerView.setAdapter(myAdapter);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        // Handle errors such as network errors, server errors, etc.
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
//                headers.put("Accept", "application/json, text/plain, */*");
//                headers.put("Content-Type", "application/x-www-form-urlencoded");
//                headers.put("Is_ssr", "false");
//                headers.put("Mode_device", "mobile");
//                headers.put("Referer", "https://www.purplle.com/");
                headers.put("Token", accessToken);
//                headers.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 16_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.6 Mobile/15E148 Safari/604.1");
                headers.put("Visitorppl", "10Nq0C7elqD8c1qMFv");
                return headers;
            }

        };

        requestQueue.add(jsonObjectRequest);
    }
}




