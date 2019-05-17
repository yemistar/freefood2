package com.example.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.EventObj.Event;
import com.example.EventObj.Event2;
import com.example.oabass.freefood.R;

import java.util.List;

public class Event2Adapter  extends ArrayAdapter<Event2> {


    public Event2Adapter(@NonNull Context context, int resource, @NonNull List<Event2> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Event2 event=getItem(position);
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.event_card,parent,false);
        }

        TextView Eventname= (TextView) convertView.findViewById(R.id.Organization);
        TextView food= (TextView) convertView.findViewById(R.id.food);
        TextView time= (TextView) convertView.findViewById(R.id.time);
        if(event.getEventInfos().size()>0){
            Eventname.setText(event.getEventInfos().get(0).getDay());
        }else{
            Eventname.setText("empty");
        }

        if(event.getEventInfos().size()>0){
            food.setText(event.getEventInfos().get(0).getTime());
        }else{
            food.setText("HELP");
        }
        if(event.getEventInfos().size()>0){
            time.setText(event.getEventInfos().get(0).getDay()+ " "+event.getEventInfos().get(0).getDateOfMonth()+" "+event.getEventInfos().get(0).getMonth());
        }else{
            time.setText("HELP");
        }

        return convertView;
    }
}
