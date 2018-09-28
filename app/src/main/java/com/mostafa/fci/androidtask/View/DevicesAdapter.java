package com.mostafa.fci.androidtask.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mostafa.fci.androidtask.R;
import java.util.ArrayList;


public class DevicesAdapter extends ArrayAdapter {

    public DevicesAdapter(Context context, ArrayList<String> devices) {
        super(context,R.layout.device_item, devices);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.device_item, parent, false);
        }
        String name = (String) getItem(position);

        TextView deviceName = listItemView.findViewById(R.id.deviceName);
        deviceName.setText(name);

        return  listItemView;
    }
}