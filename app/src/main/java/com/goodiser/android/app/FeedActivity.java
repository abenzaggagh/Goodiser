package com.goodiser.android.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.goodiser.android.R;
import com.goodiser.android.auth.AuthenticationActivity;
import com.goodiser.modal.Database;
import com.goodiser.modal.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FeedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

//    RecyclerView topProductRecycler = (RecyclerView) findViewById(R.id.top_product_recycler);

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    Toolbar toolbar = null;
    DrawerLayout drawer = null;
    NavigationView navigationView = null;
    ConstraintLayout home;
    TabLayout tabLayout;

    private TextView mUsername = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mUsername = (TextView) findViewById(R.id.name);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        toolbar.setTitle("Goodiser");

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

        home = (ConstraintLayout) findViewById(R.id.home_content);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        // mUsername.setText("Amine BEN ZAGGAGH");

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {

            home.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_notification) {

            home.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);

        } else if (id == R.id.nav_cart) {

            home.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);

        } else if (id == R.id.nav_wish_list) {

            home.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);

        } else if (id == R.id.nav_history) {

            home.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);

        } else if (id == R.id.nav_manage) {

            home.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);

        } else if (id == R.id.nav_sign_out) {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(FeedActivity.this, AuthenticationActivity.class));
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }





}
