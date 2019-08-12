package com.example.keabank;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import models.Account;
import models.BudgetAccount;
import models.BusinessAccount;
import models.Client;
import models.DefaultAccount;
import models.PensionAccount;
import models.SavingsAccount;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final String CLIENT_KEY = "CLIENT_KEY", ACCOUNT_KEY = "ACCOUNT_KEY", ACCOUNT_TYPE = "ACCOUNT_TYPE", NEW_ACCOUNT_KEY = "NEW_ACCOUNT_KEY";
    protected DrawerLayout drawer;
    Client client;
    Account account;
    Bundle bundleToFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        client = getIntent().getParcelableExtra(CLIENT_KEY);

        if (findViewById(R.id.FragmentHolder) != null) {
            OverviewFragment overviewFragment = new OverviewFragment();
            overviewFragment.setArguments(getIntent().getExtras());
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
        View navHeader = navigationView.getHeaderView(0);
        TextView affiliateView = navHeader.findViewById(R.id.affiliate);
        affiliateView.setText(String.format(getResources().getString(R.string.affiliate), client.getAffiliate().getName(), client.getAffiliate().getAddress(), client.getAffiliate().getCity()));

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

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        client = (Client) getIntent().getExtras().getParcelable(CLIENT_KEY);
        bundleToFragment = new Bundle();
        bundleToFragment.putParcelable(CLIENT_KEY, client);

        //Log.d("mybp", "onNavigationItemSelected: " + client.toString());

        if (id == R.id.menuAccountOverview) {
            OverviewFragment overviewFragment = new OverviewFragment();
            getSupportActionBar().setTitle(R.string.title_activity_navigation);
            overviewFragment.setArguments(bundleToFragment);
            fragmentTransaction.replace(R.id.FragmentHolder, overviewFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.menuDefaultAccount) {
            accountFragmentRedirect(DefaultAccount.class, R.string.menu_default_account);
        } else if (id == R.id.menuBudgetAccount) {
            accountFragmentRedirect(BudgetAccount.class, R.string.menu_budget_account);
        } else if (id == R.id.menuSavingsAccount) {
            accountFragmentRedirect(SavingsAccount.class, R.string.menu_savings_account);
        } else if (id == R.id.menuPensionAccount) {
            accountFragmentRedirect(PensionAccount.class, R.string.menu_pension_account);
        } else if (id == R.id.menuBusinessAccount) {
            accountFragmentRedirect(BusinessAccount.class, R.string.menu_business_account);
        } else if (id == R.id.menuTransferMoney){
            TransferMoneyFragment transferMoneyFragment = new TransferMoneyFragment();
            getSupportActionBar().setTitle(R.string.menu_transfer_money);
            transferMoneyFragment.setArguments(bundleToFragment);
            fragmentTransaction.replace(R.id.FragmentHolder, transferMoneyFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if(id == R.id.menuRecurringPayments){
            RecurringTransfersFragment recurringTransfersFragment = new RecurringTransfersFragment();
            getSupportActionBar().setTitle(R.string.recurring_transfers);
            recurringTransfersFragment.setArguments(bundleToFragment);
            fragmentTransaction.replace(R.id.FragmentHolder, recurringTransfersFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void accountFragmentRedirect(Class accountClass, int accountType) {

        boolean accountExists = false;
        for (Account account : client.getAccounts()) {
            if (accountClass.isInstance(account)) {
                accountExists = true;
                this.account = account;
            }
        }

        if (accountExists) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            bundleToFragment.putParcelable(ACCOUNT_KEY, account);
            AccountFragment accountFragment = new AccountFragment();
            accountFragment.setArguments(bundleToFragment);
            fragmentTransaction.replace(R.id.FragmentHolder, accountFragment);
            getSupportActionBar().setTitle(accountType);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //Create popup modal for creating new account
            CreateAccountDialogFragment createAccountDialogFragment = new CreateAccountDialogFragment();
            bundleToFragment.putInt(ACCOUNT_TYPE, accountType);
            bundleToFragment.putParcelable(CLIENT_KEY, client);
            try {
                bundleToFragment.putParcelable(NEW_ACCOUNT_KEY, (Account) accountClass.newInstance());
            } catch (IllegalAccessException ex) {

            } catch (InstantiationException ex) {
            }
            createAccountDialogFragment.setArguments(bundleToFragment);
            createAccountDialogFragment.show(fragmentTransaction, "huh");
        }
    }
}
