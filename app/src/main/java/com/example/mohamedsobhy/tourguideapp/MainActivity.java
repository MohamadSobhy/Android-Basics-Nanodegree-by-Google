package com.example.mohamedsobhy.tourguideapp;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Attr;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FrameLayout backgroundScreen,
                        fragmentsLayout;
    private int count;
    private ArrayList<Attraction> attractions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //this is an object represents the frame layout of the listView
        backgroundScreen =(FrameLayout) findViewById(R.id.default_background);
        //this is an object represents the main frame layout which replaced when the user navigates to any fragment ..
        fragmentsLayout = (FrameLayout) findViewById(R.id.frame_layout);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

        if(count < 1){
            fragmentsLayout.setVisibility(View.INVISIBLE);
            backgroundScreen.setVisibility(View.VISIBLE);
            count++;
            Toast.makeText(this , getString(R.string.press_again_message), Toast.LENGTH_SHORT).show();
            return;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        FragmentManager manager = getFragmentManager();
        if(id == R.id.nav_egypt){
            if(count == 1)
                count = 0;
            backgroundScreen.setVisibility(View.INVISIBLE);
            fragmentsLayout.setVisibility(View.VISIBLE);
            manager.beginTransaction().replace(R.id.frame_layout , new EgyptAttractionsFragment()).commit();
        } else if (id == R.id.nav_cairo) {
            if(count == 1)
                count = 0;
            backgroundScreen.setVisibility(View.INVISIBLE);
            fragmentsLayout.setVisibility(View.VISIBLE);
            manager.beginTransaction().replace(R.id.frame_layout , new CairoAttractionsFragment()).commit();

        } else if (id == R.id.nav_alexandria) {

            if(count == 1)
                count = 0;
            backgroundScreen.setVisibility(View.INVISIBLE);
            fragmentsLayout.setVisibility(View.VISIBLE);
            manager.beginTransaction().replace(R.id.frame_layout , new AlexAttractionsFragment()).commit();

        } else if (id == R.id.nav_luxor) {

            if(count == 1)
                count = 0;
            backgroundScreen.setVisibility(View.INVISIBLE);
            fragmentsLayout.setVisibility(View.VISIBLE);
            manager.beginTransaction().replace(R.id.frame_layout , new LuxorAttractionFragment()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
