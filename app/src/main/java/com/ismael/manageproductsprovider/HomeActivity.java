package com.ismael.manageproductsprovider;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by usuario on 1/12/16
 */
public class HomeActivity extends AppCompatActivity implements ManageInvoiceFragment.ManageInvoiceListener,AddLineFragment.addLineListener,InvoiceListFragment.InvoiceListListener, CategoryListFragment.CategoryListListener ,PharmacyListFragment.ListPharmacyListener, MultiListProductFragment.ListProductListener, ManageProductFragment.ManageProductListener {
    private MultiListProductFragment listProductFragment;
    private ManageProductFragment manageProductFragment;
    private PharmacyListFragment chemistFragment;
    private CategoryListFragment catFragment;
    private InvoiceListFragment invoiceFr;
    private ManageInvoiceFragment manageInvoiceFragment;
    AddLineFragment invoiceLineFr;
    private long mBackPressed = 0;
    private static final long MAX_TIME = 2500;

    private Toolbar mToolbar;
    private android.support.v4.widget.DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_navigation);

        Intent i = new Intent(HomeActivity.this, InvoiceService.class);
        startService(i);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        mNavigationView = (NavigationView)findViewById(R.id.navigation_view);
mActionBarDrawerToggle = setupDrawerToggle();
        setupDrawerContent();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);


        if (savedInstanceState == null) {
            showListProduct(null);
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {

        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it

        // and will not render the hamburger icon without it.

        return new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,  R.string.drawer_close);

    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`

    // NOTE 1: Make sure to override the method with only a single `Bundle` argument

    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.

    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.

    @Override

    protected void onPostCreate(Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.

        mActionBarDrawerToggle.syncState();

    }

    /**
     * Method which controls the selected option
     */
    private void setupDrawerContent() {

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.action_products:
                        showListProduct(null);
                        break;
                    case R.id.action_chemist:
                        showListPharmacies();
                        break;
                    case R.id.action_sale:
                        showInvoiceList(null);
                        break;

                    case R.id.action_category:
                        showListCategories();
                        break;

                    case R.id.action_settings_account:
                        intent = new Intent(HomeActivity.this, GeneralSettingsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_settings:
                        intent = new Intent(HomeActivity.this, AccountSettingsActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        item.setChecked(false);
                        break;
                }
                setTitle(item.getTitle());
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Log.d("STACKBACK", String.valueOf(getSupportFragmentManager().getBackStackEntryCount()) );
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        else if (mBackPressed + MAX_TIME > System.currentTimeMillis() || getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

    /**
     * @return true when the event controlled by this has been consumed, false when it hasn't and gets propagated
     */
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_settings_general:
                intent = new Intent(HomeActivity.this, GeneralSettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings_account:
                intent = new Intent(HomeActivity.this, AccountSettingsActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
*/
   /* @Override
    public void showManageProduct(Bundle bundle) {
        manageProductFragment = ManageProductFragment.newInstance(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.framehome, manageProductFragment);
        ft.addToBackStack(null);
        ft.commit();
    }*/

    @Override
    public void showListProduct(Bundle b) {
        listProductFragment = MultiListProductFragment.getInstance(b);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.framehome, listProductFragment);
        //ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void showListPharmacies() {
        chemistFragment = PharmacyListFragment.getInstance(null);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.framehome, chemistFragment);
        //ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void showListCategories() {
        catFragment = CategoryListFragment.getInstance(null);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.framehome, catFragment);
        //ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void showManageProduct(Bundle bundle) {
        manageProductFragment = ManageProductFragment.getInstance(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.framehome, manageProductFragment);
        //ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void showManageInvoice(Bundle invoice) {
        manageInvoiceFragment = ManageInvoiceFragment.getInstance(invoice);
        getSupportFragmentManager().beginTransaction().replace(R.id.framehome, manageInvoiceFragment).commit();

    }

    @Override
    public void showInvoiceList(Bundle b) {
        invoiceFr = InvoiceListFragment.getInstance(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.framehome, invoiceFr).commit();
    }

    @Override
    public void showAddLine(Bundle b) {
        invoiceLineFr = AddLineFragment.getInstance(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.framehome, invoiceLineFr).commit();
    }
}
