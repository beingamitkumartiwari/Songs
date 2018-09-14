package com.crownstack.songs;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SongsListFragment fragment = new SongsListFragment();
        // Add fragment one with tag name.
        fragmentTransaction.add(R.id.fragment_container, fragment, SongsListFragment.TAG);
        fragmentTransaction.commit();

//        if (savedInstanceState == null) {
//            SongsListFragment fragment = new SongsListFragment();
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fragment_container, fragment, SongsListFragment.TAG).commit();
//        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fragmentManager.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }
}
