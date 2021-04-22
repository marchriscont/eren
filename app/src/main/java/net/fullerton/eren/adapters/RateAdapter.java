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
import net.fullerton.eren.handlers.RateObject;
import net.fullerton.eren.handlers.ScheduleObject;

public class RateAdapter extends ArrayAdapter<String> {

    private TextView sRate;
    private TextView sDate;
    private TextView sDesc;

    //private ImageView a_classStatus; //TODO: Waitlisted, etc.

    private Activity context;

    private final RateObject[] rateObjects;

    public RateAdapter(Activity context, RateObject[] sObject) {
        super(context, R.layout.adapter_rate);
        this.context = context;

        this.rateObjects = sObject;
    }

    @Override
    public int getCount() {
        return rateObjects.length;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.adapter_rate, null);

        sRate = rowView.findViewById(R.id.textRate);
        sDate = rowView.findViewById(R.id.textDate);
        sDesc = rowView.findViewById(R.id.textContent);

        sRate.setText(rateObjects[position].getRating());
        sDate.setText(rateObjects[position].getDate());
        sDesc.setText(rateObjects[position].getDesc());

        return rowView;

    };
}
