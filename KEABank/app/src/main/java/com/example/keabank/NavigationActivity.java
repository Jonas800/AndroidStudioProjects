package com.example.keabank;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import models.Account;
import models.Client;
import models.DefaultAccount;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final String CLIENT_KEY = "CLIENT_KEY", ACCOUNT_KEY = "ACCOUNT_KEY";
    protected DrawerLayout drawer;
    ConstraintLayout fragmentHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(findViewById(R.id.FragmentHolder) != null) {
            OverviewFragment overviewFragment = new OverviewFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.FragmentHolder, overviewFragment).commit();
        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.d("m", "onNavigationItemSelected: line 64");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Client client = Client.getDummyData();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CLIENT_KEY, client);

        if (id == R.id.menuAccountOverview) {
            OverviewFragment overviewFragment = new OverviewFragment();
            getSupportActionBar().setTitle(R.string.title_activity_navigation);
            fragmentTransaction.replace(R.id.FragmentHolder, overviewFragment);
            //startActivity(new Intent(getApplicationContext(), AccountOverviewActivity.class));
        } else if (id == R.id.menuDefaultAccount) {
            for(Account account : client.getAccounts()){
                if(account instanceof DefaultAccount){
                    bundle.putParcelable(ACCOUNT_KEY, account);
                }
            }
            DefaultAccountFragment defaultAccountFragment = new DefaultAccountFragment();
            defaultAccountFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.FragmentHolder, defaultAccountFragment);
            getSupportActionBar().setTitle(R.string.menu_default_account);
            //startActivity(new Intent(getApplicationContext(), DefaultAccount.class));
        }
        Log.d("mybp", "onNavigationItemSelected: line 70");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
