package com.example.mohamedsobhy.tourguideapp;


import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CairoAttractionsFragment extends Fragment {

    private ArrayList<Attraction> attractions;

    public CairoAttractionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cairo_attractions , container , false);

        ListView listView = (ListView) rootView.findViewById(R.id.cairo_fragment_list_view);

        attractions = new ArrayList<>();

        setListViewAdapter(listView , attractions);

        listView.setOnItemClickListener(new OnListItemClicked());

        return rootView;
    }

    /**
     * sets the data of attractions to th listView object using the attraction ArrayList object ..
     * @param listView ListView Object which will be set by adapter of the attractions data.
     * @param attractions the list of attractions which has all data about attraction.
     */
    private void setListViewAdapter(ListView listView , ArrayList<Attraction> attractions){

        String attractionName = getString(R.string.first_place_name);
        String attractionDesc = getString(R.string.first_place_desc);
        String attractionLatitude = getString(R.string.first_place_latitude);
        String attractionLongitude = getString(R.string.first_place_longitude);
        String attractionAddress = getString(R.string.first_place_address);

        attractions.add(new Attraction(R.drawable.pyramids , attractionName , attractionDesc , attractionLatitude , attractionLongitude , attractionAddress ));

        attractionName = getString(R.string.second_place_name);
        attractionDesc = getString(R.string.second_place_desc);
        attractionLatitude = getString(R.string.second_place_latitude);
        attractionLongitude = getString(R.string.second_place_longitude);
        attractionAddress = getString(R.string.second_place_address);

        attractions.add(new Attraction(R.drawable.azhar_mosque, attractionName , attractionDesc , attractionLatitude , attractionLongitude , attractionAddress ));

        attractionName = getString(R.string.third_place_name);
        attractionDesc = getString(R.string.third_place_desc);
        attractionLatitude = getString(R.string.third_place_latitude);
        attractionLongitude = getString(R.string.third_place_longitude);
        attractionAddress = getString(R.string.third_place_address);

        attractions.add(new Attraction(R.drawable.masjid_mohamad_ali, attractionName , attractionDesc , attractionLatitude , attractionLongitude , attractionAddress ));

        attractionName = getString(R.string.fourth_place_name);
        attractionDesc = getString(R.string.fourth_place_desc);
        attractionLatitude = getString(R.string.fourth_place_latitude);
        attractionLongitude = getString(R.string.fourth_place_longitude);
        attractionAddress = getString(R.string.fourth_place_address);

        attractions.add(new Attraction(R.drawable.egyptian_museum , attractionName , attractionDesc , attractionLatitude , attractionLongitude , attractionAddress ));

        AttractionAdapter adapter = new AttractionAdapter(getActivity() , attractions);

        listView.setAdapter(adapter);

    }

    /**
     * custom OnItemClickListener to open the mao app and refer to the location of the attraction which clicked by the user.
     */
    private class OnListItemClicked implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            //getting the attraction which clicked and getting it's name and location ..
            Attraction currentAttraction = attractions.get(position);
            String attractionName = currentAttraction.getAttractionName();
            Location attractionLocation = currentAttraction.getmAttractionLocation();

            //getting the latitude and longitude of the attraction from it's location object ..
            double latitude = attractionLocation.getLatitude();
            double longitude = attractionLocation.getLongitude();

            //check if the location available or not and return without doing any thing if it not available ..
            if(latitude == -1 || longitude == -1){
                Toast.makeText(getActivity() , getActivity().getString(R.string.location_not_available), Toast.LENGTH_SHORT).show();
                return;
            }

            //parsing an Uri for the location
            Uri uri = Uri.parse("geo:" + latitude + " , " + longitude + "?q=" + Uri.encode(attractionName));

            //create an intent and start it to open map app.
            Intent i = new Intent(Intent.ACTION_VIEW , uri);
            startActivity(i);

        }
    }
}
