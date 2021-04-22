package net.fullerton.eren.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.fullerton.eren.R;
import net.fullerton.eren.handlers.ClassObject;
import net.fullerton.eren.handlers.ScheduleObject;

public class ScheduleAdapter extends ArrayAdapter<String> {

    private TextView sName;
    private TextView sDesc;
    private TextView sDate;
    private TextView sProf;
    private TextView sRoom;

    //private ImageView a_classStatus; //TODO: Waitlisted, etc.

    private Activity context;

    private final ScheduleObject[] scheduleObject;

    public ScheduleAdapter(Activity context, ScheduleObject[] sObject) {
        super(context, R.layout.adapter_schedule);
        this.context = context;

        this.scheduleObject = sObject;
    }

    @Override
    public int getCount() {
        return scheduleObject.length;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.adapter_schedule, null);

        sName = rowView.findViewById(R.id.sName);
        sDesc = rowView.findViewById(R.id.sDesc);
        sDate = rowView.findViewById(R.id.sDate);
        sProf = rowView.findViewById(R.id.sProf);
        sRoom = rowView.findViewById(R.id.sRoom);

        sName.setText(scheduleObject[position].getName());
        sDesc.setText(scheduleObject[position].getDesc());
        sDate.setText(scheduleObject[position].getDate());
        sProf.setText(scheduleObject[position].getProf());
        sRoom.setText((scheduleObject[position].getRoom().contains("WEB")) ? "WEB" : scheduleObject[position].getRoom());

        return rowView;

    };
}
