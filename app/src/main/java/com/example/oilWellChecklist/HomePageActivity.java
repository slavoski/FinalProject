package com.example.oilWellChecklist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    MaterialToolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    private FirebaseAuth _authorizer;
    private FirebaseUser _currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_page);

        _authorizer = FirebaseAuth.getInstance();
        _currentUser = _authorizer.getCurrentUser();

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setOnMenuItemClickListener(this::onMenuItemClick);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_closed)
        {
            @Override
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };

        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


    }

    private void LoadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        boolean result = PerformMenuItem(item);
        drawerLayout.closeDrawer(GravityCompat.START, true);
        return result;
    }

    public boolean onMenuItemClick(MenuItem item) {
        return PerformMenuItem(item);
    }

    private boolean PerformMenuItem(MenuItem item)
    {
        int id = item.getItemId();

        if ( id == R.id.home_menu_option || id == R.id.home_action )
        {
            LoadFragment(new LeasesFragment());
        }
        else if ((id == R.id.logout) )
        {
            _authorizer.signOut();
            startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
        }
       //else if (id == R.id.movie_info_menu_option || id == R.id.movie_info_action)
       //{
       //    //LoadFragment(new RandomMovieDetailFragment());
       //}
       //else if(id == R.id.movie_details_menu_option || id == R.id.movie_details_action)
       //{
       //   // startActivity(new Intent(MainActivity.this, MovieDetailViewPager.class));
       //}

        return true;
    }


}
