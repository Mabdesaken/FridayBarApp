package com.example.aupke.fridaybar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aupke on 06-12-2017.
 */

public class FavoritesOfferAdapter extends OfferAdapter {

    public interface OnDeleteButtonListener {
        public void OnDeleteRequest(int position);
    }

    private Context context;
    private ArrayList<Offer> offerList;
    private OnDeleteButtonListener listener;

    public void setListener(OnDeleteButtonListener listener) {
        this.listener = listener;
    }

    public FavoritesOfferAdapter(Context context, ArrayList<Offer> offerList) {
        super(context, offerList);
        this.context = context;
        this.offerList = offerList;
    }

    private class ViewHolder{
        public Button deleteButton;
        public TextView titleView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.favorites_list_view, null);
            viewHolder.titleView = convertView.findViewById(R.id.fav_title);
            viewHolder.deleteButton = convertView.findViewById(R.id.fav_deleteButton);
            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnDeleteRequest(position);
                }
            });
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.titleView.setText(offerList.get(position).getTitle());

        return convertView;
    }
}
