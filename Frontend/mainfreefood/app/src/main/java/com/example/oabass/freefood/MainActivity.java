package com.example.oabass.freefood;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.EventObj.Date;
import com.example.EventObj.Event;
import com.example.EventObj.Event2;
import com.example.Utils.*;
import com.example.location.MapActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RequestQueue mRequestQueue;

    public static final String Day="Day";
    public static final String Month="Month";
    public static final String Time="Time";
    public static final String DateOfMonth="DateOfMonth";
    public static final String EventName="EventName";
    public static final String EventFoodSentence="EventFoodSentence";
    public static final String EventID="EventID";
    public static final String EventInfoId="EventInfoId";
    public static final String EventLocation="EventLocation";

    public static HashMap<String,Event2> mapToEvent=new HashMap<>();

    public static ArrayList<Event2> event2ArrayList=new ArrayList<>();
   public  EventAdapter eventAdapter;
   public Event2Adapter event2Adapter;
   Handler handler=new Handler();
    ListView listView;
    private static boolean nextActivity=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue= Volley.newRequestQueue(this);
        listView=(ListView) findViewById(R.id.listView);


        event2Adapter=new Event2Adapter(this,0,new ArrayList<Event2>());
        listView.setAdapter(event2Adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event2 event= event2Adapter.getItem(position);
                Intent intent=new Intent(MainActivity.this,EventdetailActivity.class);
                intent.putExtra(EventName,""+event.getEventName());
                intent.putExtra(EventFoodSentence,event.getFoodSentence());
                intent.putExtra(EventID,event.getId());
                intent.putExtra(EventLocation,event.getLocation());
                if(event.getEventInfos().size()>0){
                    intent.putExtra(Day,event.getEventInfos().get(0).getDay());
                    intent.putExtra(Month,event.getEventInfos().get(0).getMonth());
                    intent.putExtra(DateOfMonth,event.getEventInfos().get(0).getDateOfMonth());
                    intent.putExtra(Time,event.getEventInfos().get(0).getTime());
                    intent.putExtra(EventInfoId,event.getEventInfos().get(0).getId());
                }else{
                    intent.putExtra(Day,"HELP");
                    intent.putExtra(Month,"HELP");
                    intent.putExtra(DateOfMonth,"HELP");
                    intent.putExtra(Time,"HELP");
                }



                startActivity(intent);
            }
        });

        fetchJsonResponse();
        event2Adapter.clear();
        event2Adapter.addAll(event2ArrayList);

        BottomNavigationView bottomNavigationView= (BottomNavigationView) findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.Subs:


                        break;

                    case R.id.map:
                        startActivity(new Intent(MainActivity.this, MapActivity.class));
                        break;

                    case R.id.help :
                        onRestart();
                        break;
                }
                return false;
            }
        });

    }

    private void testrun(){
        MainActivityTread mainActivityTread=new MainActivityTread(Parse2.getEvent());
        new Thread(mainActivityTread).start();
    }
    private void fetchJsonResponse() {
//        String URL="http://10.0.2.2:8080/freefood/emaileventout";
        String URL2="http://10.0.2.2:8080/event2test/test";



        JsonArrayRequest req = new JsonArrayRequest(URL2, new Response.Listener<JSONArray> () {
            @Override
            public void onResponse(JSONArray response) {


                try {
                    VolleyLog.v("Response:%n %s", response.toString(4));
                    Log.d("TEST PARSE", "onResponse:  About to Parse");

                    event2ArrayList.addAll(Parse2.Parse2(response));
                    event2Adapter.addAll(event2ArrayList);
                    HashMap<String,Event2> map=new HashMap<>();
                    for(Event2 e: event2ArrayList){
                        String key=""+e.getLocation()+":"+e.getId();
                        map.put(key,e);
                    }
                    mapToEvent.putAll(map);
                    Parse2.clear();
                    Log.w(TAG, "onResponse: responce2"+event2ArrayList.size());
                    Log.w(TAG, "onResponse: responce3"+mapToEvent.size());



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);

    }


    @Override
    protected void onRestart() {
        Toast.makeText(getApplicationContext(),"Rstarting size:"+event2ArrayList.size(),Toast.LENGTH_SHORT).show();
        super.onRestart();
    }

    class MainActivityTread implements Runnable{

        List<Event2> event2List;
        HashMap<String,Event2> map=new HashMap<>();

        MainActivityTread( List<Event2> event2List){
            this.event2List=event2List;

        }
        MainActivityTread(){

        }
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"About to start",Toast.LENGTH_SHORT).show();
                }
            });

            //Parse2.Parse2(jsonArray);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"Finished",Toast.LENGTH_SHORT).show();
                    //event2ArrayList.addAll(Parse2.getEvent());
                }
            });

            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"Size: "+event2ArrayList.size(),Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "run: size:"+Parse2.getEvent().size());
                }
            });
            //Parse2.clear();
            for(Event2 e: event2List){
                String key=""+e.getLocation()+":"+e.getId();
                map.put(key,e);
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"Size: "+map.size(),Toast.LENGTH_SHORT).show();
                    mapToEvent.putAll(map);
                }
            });
        }
    }

}
