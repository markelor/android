package com.markelmendizabal.earthquakes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.markelmendizabal.earthquakes.R;
import com.markelmendizabal.earthquakes.model.EarthQuake;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by cursomovil on 25/03/15.
 */
public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake>{
    private int resource;
    public EarthQuakeAdapter(Context context, int resource, List<EarthQuake> objects) {
        super(context, resource, objects);
        this.resource=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        if(convertView==null){
            layout=new LinearLayout(getContext());
            LayoutInflater li;
            String inflater=Context.LAYOUT_INFLATER_SERVICE;
            li=(LayoutInflater)getContext().getSystemService(inflater);
            li.inflate(resource,layout,true);

        }else{
            layout=(LinearLayout)convertView;
        }
        EarthQuake item=getItem(position);

        TextView lblMagnitude=(TextView)layout.findViewById(R.id.txtMagnitud);
        TextView lblLugar=(TextView)layout.findViewById(R.id.txtLugar);
        TextView lblDate=(TextView)layout.findViewById(R.id.txtDate);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");

        lblMagnitude.setText(Double.toString(item.getMagnitude()));
        lblLugar.setText(item.getPlace());
        lblDate.setText(sdf.format(item.getTime()));

        return layout;
    }
}
