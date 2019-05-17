package com.example.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.EventObj.Event;
import org.json.JSONArray;
import org.json.JSONException;


import java.util.ArrayList;
import java.util.List;

import static com.example.Utils.Parse.Parse;

public class EventLoader  extends AsyncTaskLoader<List<Event>> {

    private static final String TAG = "EventLoader";
    private JSONArray url;
    private RequestQueue mRequestQueue;
    List<Event> events = new ArrayList<>();


    public EventLoader(@NonNull Context context, JSONArray url) {
        super(context);
        this.url=url;

    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Event> loadInBackground() {


        List<Event> events2=Parse.Parse(url);
        Log.d(TAG, "loadInBackground: "+events2.size());



        return events2;
    }

//    private JSONArray[] fetchJsonResponse() {
//
//        final JSONArray[] response2 = new JSONArray[1];
//        final ArrayList<Event> eventArrayList=new ArrayList<>();
//
//
//
//        // Pass second argument as "null" for GET requests
//        //localhost:8080/freefood/emaileventout  "https://jsonplaceholder.typicode.com/posts" http://10.0.2.2:8080/ http:// 10.0.2.2:8888
//        String URL="http://10.0.2.2:8080/freefood/emaileventout";
//
//        //final String URL = "/volley/resource/all?count=20";
//        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray> () {
//            @Override
//            public void onResponse(JSONArray response) {
//                response2[0] =response;
//                try {
//                    VolleyLog.v("Response:%n %s", response.toString(4));
//                    Log.d("TEST PARSE", "onResponse:  About to Parse");
//                    eventArrayList.addAll(Parse.Parse(response));
//
//
//
//                    Log.d("TEST PARSE", "onResponse:  finish  Parsing, Testing Event: "+Parse.getEvent().get(0).toString());
//                   // Log.d("TEST PARSE", "onResponse:  finish  Parsing SEE"+Parse.getEvent().get(0).toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.e("Error: ", error.getMessage());
//            }
//        });
//
// /* Add your Requests to the RequestQueue to execute */
//        mRequestQueue.add(req);
//
//        return response2;
//    }

}
