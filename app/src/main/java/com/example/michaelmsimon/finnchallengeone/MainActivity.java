package com.example.michaelmsimon.finnchallengeone;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.GridView;

import com.example.michaelmsimon.finnchallengeone.Accessor.OnClientApiExcuted;
import com.example.michaelmsimon.finnchallengeone.Accessor.RestClient;
import com.example.michaelmsimon.finnchallengeone.Model.Product;
import com.example.michaelmsimon.finnchallengeone.netWork.NetworkCheck;
import com.suke.widget.SwitchButton;

import org.parceler.Parcels;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ProductsForSaleFragment.OnFragmentInteractionListener, PageLoadingFragment.OnFragmentInteractionListener,
        OnClientApiExcuted {

    //BASE_URL should not be removed.
    private static final String BASE_URL = "https://gist.githubusercontent.com/3lvis/3799feea005ed49942dcb56386ecec2b/raw/63249144485884d279d55f4f3907e37098f55c74/discover.json";
    private Toolbar toolbar;
    ProductsForSaleFragment productsFragment;
    FavoriteFragment favoriteFragment;
    //Confirms the active fragment
    private Boolean productDisplayed = true;
    private boolean netWorkStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check internet connection status, should improve.
        //TODO
        //continuous check, inform user between sessions. not only at the beginning.
        if (NetworkCheck.isConnectedToInternet(MainActivity.this)) {
            netWorkStatus = true;
        } else {
            NetworkCheck.informBadInternet(MainActivity.this);
        }

        //Get the toggle button that switch favorites
        //should be removed if the switch button is not used
        com.suke.widget.SwitchButton switchButton = (com.suke.widget.SwitchButton)
                findViewById(R.id.switch_button);

        //Listen switch button click event
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {

                    toolbar.setTitle("Favorites");
                    favoriteFragment = new FavoriteFragment();

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.animator.fade_in,
                            android.R.animator.fade_out);
                    fragmentTransaction.replace(R.id.fragment_switch, favoriteFragment);
                    fragmentTransaction.commit();
                    //Fix naming
                    productDisplayed = true;

                }
                else {

                    if (productDisplayed) {
                        toolbar.setTitle("Finn Challenge");
                        redirectToProductFragment();
                    }
                }
            }
        });

        //Toolbar
        // Find the toolbar view inside the activity layout
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        if (netWorkStatus) {
            //Instantiate Rest Client class that takes care of getting data
            RestClient instance = new RestClient(this, (OnClientApiExcuted) this);
            //Call method that execute API with the BASE_URL as parameter
            instance.excuter(BASE_URL);
        } else {
            toolbar.setTitle("Favorites");
            favoriteFragment = new FavoriteFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.animator.fade_in,
                    android.R.animator.fade_out);
            fragmentTransaction.replace(R.id.fragment_switch, favoriteFragment);
            fragmentTransaction.commit();

            //Set checked and Disable the toggle when there is no network, just to stop crushing
            //when toggling
            //TODO
            //Find a better a way to handle the crush when there network is not available
            switchButton.setChecked(true);
            switchButton.setEnabled(false);
        }
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //TODO
    //Handle activity life cycles
    //Handle positioning of device(portrait and landscape)

    //a call back interface that serves transporting values. It was called right after the data was recieved from
    //..APi and passed in array list
    //Here it passes the result to the view adapter
    @Override
    public void taskCompleted(ArrayList<Product> result) {
        //Pass the returned results from API to products display fragment
        Bundle args = new Bundle();
        args.putParcelable("prods", Parcels.wrap(result));
        productsFragment = new ProductsForSaleFragment();
        productsFragment.setArguments(args);

        //redirects the user to product fragment
        redirectToProductFragment();
    }

    private void redirectToProductFragment() {
        //Reputation can be minimized make a method for this and similar code from toggle listener
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        fragmentTransaction.replace(R.id.fragment_switch, productsFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
