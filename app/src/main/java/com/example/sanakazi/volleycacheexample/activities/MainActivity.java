package com.example.sanakazi.volleycacheexample.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanakazi.volleycacheexample.R;
import com.example.sanakazi.volleycacheexample.adapter.MyAdapter;
import com.example.sanakazi.volleycacheexample.others.AppController;
import com.example.sanakazi.volleycacheexample.pojos.JsonResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public MyAdapter mAdapter;
    public RecyclerView recyclerView;
    Button btnClearCache;
    StringRequest stringRequest;
    ArrayList<JsonResponse.Categories> categoryList;
    public static final String REGISTER_URL = "http://fitdev.cloudapp.net:8080/B2BAPIPlatform/GetCategoryGroup/v1?cityId=1";
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupLayout();
    }

    public void setupLayout() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btnClearCache=(Button)findViewById(R.id.btnClearCache) ;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
      //  cacheRequest();
        volleyService();
        //  recyclerView.setItemAnimator(new DefaultItemAnimator());
        btnClearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.getInstance().getRequestQueue().getCache().remove(REGISTER_URL);
                Toast.makeText(MainActivity.this,"Cache cleared",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void volleyService() {
         stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w(TAG, response.toString());
                        setData(response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){

             @Override
             protected Response<String> parseNetworkResponse(NetworkResponse response) {
                 try {
                     Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                     if (cacheEntry == null) {
                         cacheEntry = new Cache.Entry();
                     }
                     final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                     final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                     long now = System.currentTimeMillis();
                     final long softExpire = now + cacheHitButRefreshed;
                     final long ttl = now + cacheExpired;
                     cacheEntry.data = response.data;
                     cacheEntry.softTtl = softExpire;
                     cacheEntry.ttl = ttl;
                     String headerValue;
                     headerValue = response.headers.get("Date");
                     if (headerValue != null) {
                         cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                     }
                     headerValue = response.headers.get("Last-Modified");
                     if (headerValue != null) {
                         cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                     }
                     cacheEntry.responseHeaders = response.headers;
                     final String jsonString = new String(response.data,
                             HttpHeaderParser.parseCharset(response.headers));
                     return Response.success(new String(jsonString), cacheEntry);
                 } catch (UnsupportedEncodingException e) {
                     return Response.error(new ParseError(e));
                 }
             }

             @Override
             protected void deliverResponse(String response) {
                 super.deliverResponse(response);
             }

             @Override
             public void deliverError(VolleyError error) {
                 super.deliverError(error);
             }

             @Override
             protected VolleyError parseNetworkError(VolleyError volleyError) {
                 return super.parseNetworkError(volleyError);
             }
         };

        AppController.getInstance().addToRequestQueue(stringRequest);

    }


    public void setData( String response )
    { Gson gson = new Gson();
        categoryList = new ArrayList<>();
        JsonResponse jsonResponse = gson.fromJson(response, JsonResponse.class);
        if (jsonResponse.getData() != null && jsonResponse.getData().getCategories() != null
                && !jsonResponse.getData().getCategories().isEmpty()) {
            categoryList = jsonResponse.getData().getCategories();
        }

        mAdapter = new MyAdapter(categoryList);
        recyclerView.setAdapter(mAdapter);
    }


}

