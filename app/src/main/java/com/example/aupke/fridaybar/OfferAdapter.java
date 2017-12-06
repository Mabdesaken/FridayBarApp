package com.example.aupke.fridaybar;

import android.content.Context;
import android.location.Location;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aupke on 30-11-2017.
 */

public class OfferAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Offer> offerList;

    public OfferAdapter(Context context, ArrayList<Offer> offerList) {
        this.context = context;
        this.offerList = offerList;
    }

    @Override
    public int getCount() {
        return offerList.size();
    }

    @Override
    public Object getItem(int position) {
        return offerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        public TextView locationView;
        public TextView titleView;
        public TextView descriptionView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.offer_list_view, null);
            viewHolder.locationView = convertView.findViewById(R.id.distanceTextView);
            viewHolder.titleView = convertView.findViewById(R.id.titleTextView);
            viewHolder.descriptionView = convertView.findViewById(R.id.descriptionTextView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.locationView.setText( offerList.get(position).getDistanceToLocation() + "m");
        viewHolder.titleView.setText(offerList.get(position).getTitle());
        viewHolder.descriptionView.setText(offerList.get(position).getDescription());

        return convertView;
    }


}
