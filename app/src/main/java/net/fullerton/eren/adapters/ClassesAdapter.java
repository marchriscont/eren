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

public class ClassesAdapter extends ArrayAdapter<String> {

    private TextView a_className;
    private TextView a_classInfo;
    private TextView a_classProf;
    private TextView a_classRoom;

    private ImageView a_classStatus;

    private Activity context;

    private final String courseName;
    private final ClassObject[] classObject;

    public ClassesAdapter(Activity context, String courseName, ClassObject[] cObject) {
        super(context, R.layout.adapter_classes);
        this.context = context;

        this.courseName = courseName;
        this.classObject = cObject;
    }

    @Override
    public int getCount() {
        return classObject.length;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.adapter_classes, null);

        a_className = rowView.findViewById(R.id.className);
        a_classInfo = rowView.findViewById(R.id.classInfo);
        a_classProf = rowView.findViewById(R.id.classProf);
        a_classRoom = rowView.findViewById(R.id.classRoom);
        a_classStatus = rowView.findViewById(R.id.classStatus);

        //a_className.setText(courseName);
        a_className.setText(classObject[position].getProf());
        a_classInfo.setText(classObject[position].getNumber() + " | " + classObject[position].getDate());
        a_classProf.setText("2.0");
        a_classRoom.setText((classObject[position].getRoom().contains("WEB")) ? "WEB" : classObject[position].getRoom());

        return rowView;

    };
}
