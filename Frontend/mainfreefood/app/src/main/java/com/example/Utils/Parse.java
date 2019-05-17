package com.example.Utils;



import com.example.EventObj.Date;
import com.example.EventObj.Event;
import com.example.EventObj.Place;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Parse {

    static  List<Event> event = new ArrayList<>();


    /**
     *
     * @param response the json array of event
     * @return the list of event object
     */
  public static List<Event> Parse(JSONArray response) {



      for (int i = 0; i < response.length(); i++) {
          try {
              JSONObject jsonObject = (JSONObject) response.get(i);

              JSONArray dateArry = jsonObject.getJSONArray("date");
              List<Date> dateList = getDate(dateArry);

              JSONArray placeArry = jsonObject.getJSONArray("places");
              List<Place> placeList = getPlace(placeArry);

              String organization = jsonObject.get("organization").toString();
              String foodSentence = jsonObject.get("foodSentence").toString();
              String organizationSentence = jsonObject.get("organizationSentence").toString();
              event.add(new Event(dateList, placeList, organization, foodSentence));
          } catch (JSONException e) {
              e.printStackTrace();
          }

      }
      return event;
  }


    /**
     *
     * @param dateArry json array of date
     * @return a list of data object
     */
   public static List<Date> getDate(JSONArray dateArry){

       List<Date> dateList=new ArrayList<>();

       for(int i=0; i<dateArry.length(); i++){
           try {
               JSONObject date= (JSONObject) dateArry.get(i);

               String day= date.get("day").toString();
               String month=date.get("month").toString();
               String dateOfMonth=date.get("dateOfMonth").toString();
               String timeOfDay=date.get("timeOfDay").toString();
               dateList.add(new Date(day,month,dateOfMonth,timeOfDay));
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }

       return dateList;
   }

    /**
     *
     * @param placeArry json array of place
     * @return the list containing place object
     */
   public static List<Place> getPlace(JSONArray placeArry){

       List<Place> placeList=new ArrayList<>();

       for(int i=0; i<placeArry.length(); i++){
           try {
               JSONObject place= (JSONObject) placeArry.get(i);

               String bName= place.getJSONObject("bName").toString();
               String roomNumber=place.getJSONObject("roomNumber").toString();

               placeList.add(new Place(bName,roomNumber,"None"));
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }

       return placeList;
   }

   public static List<Event> getEvent(){
      return event;
   }

   public static void clear(){
       event.clear();
   }

}