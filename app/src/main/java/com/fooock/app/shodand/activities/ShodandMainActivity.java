package com.fooock.app.shodand.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.ShodandApplication;
import com.fooock.app.shodand.fragment.ExploreShodanFragment;
import com.fooock.app.shodand.presenter.ShodandPresenter;
import com.fooock.app.shodand.view.MainView;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 *
 */
public class ShodandMainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    protected NavigationView navigationView;

    private ActionBarDrawerToggle drawerToggle;
    private ShodandPresenter shodandPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shodand_main);

        ButterKnife.bind(this);

        shodandPresenter.attachView(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHomeInDrawer();
                shodandPresenter.clickOnNavigationItem();
            }
        });
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        Timber.d("In onCreate()");

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ExploreShodanFragment exploreShodanFragment = new ExploreShodanFragment();
        transaction.add(R.id.content_main, exploreShodanFragment);
        transaction.commit();
    }

    @Override
    void initializeComponents(@NonNull ShodandApplication application) {
        Timber.d("Initializing components...");
        shodandPresenter = new ShodandPresenter(application.eventBus());

        final String apiKey = application.preferences().getApiKey();

        if (apiKey.isEmpty()) {
            Timber.d("No API key found, opening configuration activity...");
            application.navigator().showConfigurationActivity();

            finish();
        }
        Timber.d("Found API key: %s", apiKey);

        application.initializeApiWith(apiKey);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            Timber.d("Closing drawer layout");
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Timber.d("Selected setting menu option");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Timber.d("Navigation item selected [%s]", item.getTitle());
        return false;
    }

    @Override
    protected void onDestroy() {
        shodandPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showBackOnDrawer() {
        Timber.d("Show back in drawer layout...");
        drawerToggle.setDrawerIndicatorEnabled(false);
    }

    @Override
    public void showHomeInDrawer() {
        Timber.d("Show home in drawer layout...");
        drawerToggle.setDrawerIndicatorEnabled(true);
    }
}
