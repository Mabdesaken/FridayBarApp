package com.example.aupke.fridaybar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aupke on 06-12-2017.
 */

class FeaturedOfferAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Offer> offerList;

    public FeaturedOfferAdapter(Context context, ArrayList<Offer> offerList) {
        this.context = context;
        this.offerList = offerList;
    }


    @Override
    public int getCount() {
        return offerList.size();
    }

    @Override
    public Object getItem(int i) {
        return offerList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        public TextView titleView;
        public TextView descriptionView;
        public TextView barView;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.featured_list_view, null);
            viewHolder.barView = convertView.findViewById(R.id.fea_bar);
            viewHolder.titleView = convertView.findViewById(R.id.fea_title);
            viewHolder.descriptionView = convertView.findViewById(R.id.fea_description);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.titleView.setText(offerList.get(i).getTitle());
        viewHolder.descriptionView.setText(offerList.get(i).getDescription());
        viewHolder.barView.setText(offerList.get(i).getBar());

        return convertView;
    }
}
