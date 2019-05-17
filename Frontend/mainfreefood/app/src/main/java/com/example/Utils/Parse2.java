package com.example.Utils;

import com.example.EventObj.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Parse2 {

    static List<Event2> event2 = new ArrayList<>();


    /**
     *
     * @param response the json array of event
     * @return the list of event2 object
     */
    public static List<Event2> Parse2(JSONArray response) {



        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = (JSONObject) response.get(i);

                JSONArray dateArry = jsonObject.getJSONArray("eventInfoSet");
                List<EventInfo> eventInfoList = getEventInfo(dateArry);

                String location = jsonObject.getString("location");
                String email = jsonObject.getString("email");
                String foodSentence = jsonObject.getString("food");
                String eventName=jsonObject.getString("eventName");
                int id=jsonObject.getInt("id");
                event2.add(new Event2(
                        location,email,eventInfoList,id,foodSentence,eventName
                ));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return event2;
    }


    /**
     *
     * @param eventInfo json array of date
     * @return a list of data object
     */
    public static List<EventInfo> getEventInfo(JSONArray eventInfo){

        List<EventInfo> eventInfoList=new ArrayList<>();

        for(int i=0; i<eventInfo.length(); i++){
            try {
                JSONObject eventI= (JSONObject) eventInfo.get(i);
                String location;
                String day;
                String dateOfMonth;
                String time;
                String roomNUmber;
                String month;
                int id;

                if(eventI.get("location")!=null){
                    location=eventI.get("location").toString();
                }else{
                    location="empty";
                }
                if(eventI.get("roomNUmber")!=null){
                    roomNUmber=eventI.getString("roomNUmber");
                }else{
                    roomNUmber="empty";
                }
                day=eventI.getString("day");
                dateOfMonth=eventI.getString("dateOfMonth");
                time=eventI.getString("time");
                id=eventI.getInt("id");
                month=eventI.getString("month");
                eventInfoList.add(new EventInfo(
                        location,day,dateOfMonth,month,time,roomNUmber,id));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return eventInfoList;
    }



    public static List<Event2> getEvent(){
        return event2;
    }

    public static void clear(){
        event2.clear();
    }

}
