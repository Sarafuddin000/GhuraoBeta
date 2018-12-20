package com.bitleet.saraf.ghurao_beta;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseAuth firebaseAuth;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        firebaseAuth = FirebaseAuth.getInstance();

        fragmentManager = getSupportFragmentManager();

//        if (firebaseAuth.getCurrentUser() == null){
//            //checking user logged in or not
//            finish();
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(intent);
//        }


//Drawer View
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Toast.makeText(MainActivity.this,
                                "navigationView item clicked",
                                Toast.LENGTH_SHORT).show();
                        int id = menuItem.getItemId();
                        displaySelectedScreen(id);
                        // set item as selected to persist highlight
                        menuItem.setChecked(false);
                        // close drawer when item is tapped
                        drawer.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }

                    private void displaySelectedScreen(int selectedMenuId) {
                        switch (selectedMenuId) {
                            case R.id.action_home:
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                EventFragment eventFragment = new EventFragment();
                                fragmentTransaction.add(R.id.content_frame, eventFragment);
                                fragmentTransaction.commit();
                                //fragment = new EventListFragment();
                                break;
                            case R.id.action_weather_report:
                                startActivity(new Intent(MainActivity.this, WeatherActivity.class));
                                Toast.makeText(MainActivity.this, "Weather Report is arraiving", Toast.LENGTH_SHORT).show();
                                // startActivity(new Intent(MainActivity.this,WeatherActivity.class));
                                break;
                            case R.id.action_map:
                                Toast.makeText(MainActivity.this, "Map is Opening", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_near_by:
                                Toast.makeText(MainActivity.this, "I'm nearby you", Toast.LENGTH_SHORT).show();
                                // fragment = new NearByFragment();
                                break;
                            case R.id.action_Update_user:
                                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ProfileFragment()).commit();
                                break;
                        }
                    }
                });
        //DrawerView Menu button Creation
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Drawer View onMenuIcon Click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);


    }

}
