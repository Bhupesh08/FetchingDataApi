package com.example.fetchingdataapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.ItemCLickListener {

    private RecyclerView recyclerView;

    private MyAdapter myAdapter;

    private ArrayList<ModalClass> list;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isNetworkAvailable(this)) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        // Check if the user is logged in
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            // User is not logged in, redirect to LoginActivity
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
            finish(); // Prevents user from going back to MainActivity
        }

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            list = new ArrayList<>();

            requestQueue = Volley.newRequestQueue(this);
            parseJSON();

            myAdapter = new MyAdapter(list, MainActivity.this, this);

    }

    private void parseJSON() {
        String url = "https://dummyjson.com/products";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, // JSON object request payload
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Gson gson = new Gson();
                            Products  products = gson.fromJson(response.toString(), Products.class);

                            for(Product obj: products.getProducts()) {
                                list.add(new ModalClass(obj.getThumbnail(), obj.getTitle(), obj.getPrice()));
                            }
                        } catch (JsonSyntaxException e) {
                            // JSON parsing error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                        // Handle successful response
                        // Process the JSON response here
//                            JSONArray jsonArray = response.getJSONArray("products");
//
//                            for(int i=0; i<jsonArray.length(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                                String imageurl = jsonObject.getString("thumbnail");
//                                String name = jsonObject.getString("title");
//                                int price = jsonObject.getInt("price");
//
//                                list.add(new ModalClass(imageurl, name, price));
//
//                            }


                        recyclerView.setAdapter(myAdapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error.networkResponse.statusCode
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onItemClick(ModalClass modalClass) {
        Toast.makeText(this, ""+modalClass.getName(), Toast.LENGTH_SHORT).show();
    }


    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        return false;
    }
}