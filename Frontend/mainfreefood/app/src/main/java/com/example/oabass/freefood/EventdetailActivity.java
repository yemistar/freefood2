package com.example.oabass.freefood;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.example.EventObj.Event2;
import com.example.EventObj.EventInfo;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;


public class EventdetailActivity extends AppCompatActivity {
    private static final String TAG = "EventdetailActivity";

    String Day;
    String month;
    String Time;
    String DateOfMonth;
    String EventName;
    String FoodSentence;
    String Location;
    TextView EventNameTextView;
    TextView LocationTextView;
    TextView TimeTextView;
    TextView FoodSentenceTextView;
    TextView DayTextView;

    TextView DatText;
    TextView LocationText;
    TextView TimeText;
    int EventId;
    int EventInfoId;
    boolean isTimeEdited;
    boolean isLocationEdited=true;
    boolean isDayEdited;

    EditText editDay;
    EditText editLocation;
    EditText editTime;
    Button clickEditDay;
    Button clickEditLocation;
    Button clickEditTime;

    Handler handler=new Handler();
    private WebSocketClient cc;
    int count=0;


    int ran;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activty);
        ran=(int)(Math.random() * 10) + 1;
        getEventInfo();
        EventNameTextView= (TextView) findViewById(R.id.eventName);
        LocationTextView= (TextView) findViewById(R.id.LocatonText);
        TimeTextView= (TextView) findViewById(R.id.TimeText);
        FoodSentenceTextView= (TextView) findViewById(R.id.food_sentenceText);
        DayTextView= (TextView) findViewById(R.id.DayText);

        EventNameTextView.setText(EventName);
        LocationTextView.setText(Location);
        TimeTextView.setText(Time);
        FoodSentenceTextView.setText(FoodSentence);

        initViews();
        connect();



        clickEditDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=editDay.getText().toString();
                editDay.getText().clear();
                closeKeyboard();
                if(text!=""){
                    isLocationEdited=true;
                    text+=":day";
                    try {
                        cc.send(text);
                    }
                    catch (Exception e)
                    {
                        Log.d("ExceptionSendMessage:", e.getMessage().toString());
                    }
                }


            }
        });

        clickEditTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=editTime.getText().toString();
                editTime.getText().clear();
                closeKeyboard();
                if(text!=""){
                    isLocationEdited=true;
                    text+=":time";

                    try {
                        cc.send(text);
                    }
                    catch (Exception e)
                    {
                        //Log.d("ExceptionSendMessage:", e.getMessage());
                        e.printStackTrace();
                    }
                }


            }
        });

        clickEditLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=editLocation.getText().toString();
                editLocation.getText().clear();
                closeKeyboard();
                if(text!=""){
                    isLocationEdited=true;
                    text+=":location";

                    try {
                        cc.send(text);
                    }
                    catch (Exception e)
                    {
                        Log.d("ExceptionSendMessage:", e.getMessage().toString());
                    }
                }

            }
        });


        Toast.makeText(getApplicationContext(),"EventId :"+EventId+" EventInfoID :"+EventInfoId,Toast.LENGTH_LONG).show();
    }

    private void getEventInfo(){
        Day=getIntent().getExtras().getString(MainActivity.Day);
        month=getIntent().getExtras().getString(MainActivity.Month);
        Time=getIntent().getExtras().getString(MainActivity.Time);
        DateOfMonth=getIntent().getExtras().getString(MainActivity.DateOfMonth);
        Day=getIntent().getExtras().getString(MainActivity.Day);
        EventName=getIntent().getExtras().getString(MainActivity.EventName);
        FoodSentence=getIntent().getExtras().getString(MainActivity.EventFoodSentence);
        EventId=getIntent().getExtras().getInt(MainActivity.EventID);
        EventInfoId=getIntent().getExtras().getInt(MainActivity.EventInfoId);
        Location=getIntent().getExtras().getString(MainActivity.EventLocation);

    }

    private void initViews(){
        editDay= (EditText) findViewById(R.id.editDate);
        editLocation= (EditText) findViewById(R.id.editLocation);
        editTime= (EditText) findViewById(R.id.editTime);

        clickEditDay = (Button) findViewById(R.id.fixDate);
        clickEditLocation = (Button) findViewById(R.id.fixLocation);
        clickEditTime = (Button) findViewById(R.id.fixTime);

        DatText= (TextView) findViewById(R.id.DayText);
        LocationText=(TextView) findViewById(R.id.LocatonText);
        TimeText=(TextView) findViewById(R.id.TimeText);

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void connect(){

        Draft[] drafts = {new Draft_6455()};

        String w = "ws://10.0.2.2:8080/websocket/yemi"+""+ran+"/"+EventId+"/"+EventInfoId;
        //10.26.13.93

        try {
            Log.d("Socket:", "Trying socket");
            Log.d(TAG, "connect: EventId: "+EventId);
            cc = new WebSocketClient(new URI(w),(Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {

                    //Toast.makeText(getApplicationContext(),"Got message",Toast.LENGTH_SHORT).show();
                    Log.d("", "run() returned: " + message);
                    String s=editDay.getText().toString();
                   // editDay.setText("updating");
                    Log.d("first", "run() returned: " + s);
                    //s=t1.getText().toString();
                    Log.d("second", "run() returned: " + s);
                    //String[] temp=message.split(":");

                   updateEvent(message);

                   if(message.contains(":") && count<=1){
                       isLocationEdited=false;
                       count=0;
                       Log.d(TAG, "onMessage: About to ruun Background Update :"+message);
                       BackGroundUpate(message);
                   }


                    //DatText.setText(temp[1]);
                    //Toast.makeText(getApplicationContext(),"Got message :"+message,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                   // Toast.makeText(getApplicationContext(),"Opened",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                    //Toast.makeText(getApplicationContext(),"Closed",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Exception e)
                {
                    Log.d("Exception:", e.toString());
                }
            };
        }
        catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
        cc.connect();

    }
    private void BackGroundUpate(String message){
        EventUpdateRunable eventUpdateRunable=new EventUpdateRunable(EventId,EventInfoId,message);
        new Thread(eventUpdateRunable).start();
    }

    /**
     *
     * @param text the update text and what text view to update
     */
    private void updateEvent(String text){
        String[] temp=text.split(":");
        String temp2=temp[2];
        String temp1=temp[1];

        Log.d(TAG, "updateEvent: temp2 :"+temp2+" temp1 :"+temp1);
        switch (temp2){
            case "day":
                DatText.setText(temp1);
                break;

            case "location":
                LocationText.setText(temp1);
                break;

            case "time" :
                TimeText.setText(temp1);
                break;

        }
    }


    class EventUpdateRunable implements Runnable{
        int EventId;
        int EventInfoId;
        String message;

        EventUpdateRunable(int EventId, int EventInfoId,String message){
            this.EventId=EventId;
            this.EventInfoId=EventInfoId;
            this.message=message;

        }
        @Override
        public void run() {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"In runmethod",Toast.LENGTH_SHORT).show();
                }
            });
            for(Event2 e: MainActivity.event2ArrayList){
                if(e.getId()==EventId){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Found event2 Id"+EventId,Toast.LENGTH_LONG).show();
                        }
                    });
                    update2Event(message,e.getEventInfos().get(0),e);
                    break;
                }
            }

        }
    }

    void update2Event(String text,EventInfo e2,Event2 event2){
        String[] temp=text.split(":");
        String temp2=temp[2];
        String temp1=temp[1];

        switch (temp2){
            case "day":
                e2.setDay(temp1);
                break;

            case "location":
                event2.setLocation(temp1);

                break;

            case "time" :
                e2.setTime(temp1);
                break;

        }
    }

}
