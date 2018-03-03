package com.example.mohamedsobhy.tourguideapp;

import android.content.Context;
import android.location.Location;
import android.support.annotation.AttrRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mohamed Sobhy on 18/01/2018.
 */

public class AttractionAdapter extends ArrayAdapter<Attraction> {

    private final int MAX_LENGTH = 10;

    public AttractionAdapter(Context context, ArrayList<Attraction> attractions) {
        super(context, 0 , attractions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item , parent , false);
        }

        Attraction currentAttraction = getItem(position);

        ImageView attractionImage = (ImageView) listItemView.findViewById(R.id.attraction_image_view);
        attractionImage.setImageResource(currentAttraction.getmAttractionImageResource());

        TextView attractionName = (TextView) listItemView.findViewById(R.id.place_name_text_view);
        attractionName.setText(currentAttraction.getAttractionName());

        TextView attractionDesc = (TextView) listItemView.findViewById(R.id.place_desc_text_view);
        attractionDesc.setText(currentAttraction.getAttractionDescription());

        Location attractionLocation = currentAttraction.getmAttractionLocation();

        //check if the length of the latitude or longitude greater than 10 then make it 10 to be displayed in good form ..
        String latitude = String.valueOf(attractionLocation.getLongitude());
        if(latitude.length() > MAX_LENGTH)
            latitude = latitude.substring(0 , MAX_LENGTH);
        String longitude = String.valueOf(attractionLocation.getLongitude());
        if(longitude.length() > MAX_LENGTH)
            longitude = longitude.substring(0 , MAX_LENGTH);

        if(latitude.equals("-1.0") || longitude.equals("-1.0"))
            longitude = latitude = "NA";

        TextView attractionCoordinates = (TextView) listItemView.findViewById(R.id.latitude_longitude_text_view);
        attractionCoordinates.setText("Latitude: " + latitude + " , Longitude: " + longitude);

        TextView attractionAddress = (TextView) listItemView.findViewById(R.id.address_text_view);
        attractionAddress.setText(currentAttraction.getAttractionAddress());

        return listItemView;
    }
}
