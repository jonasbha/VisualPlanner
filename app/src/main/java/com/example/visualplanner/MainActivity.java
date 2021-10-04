package com.example.visualplanner;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);

        AppBarConfiguration configuration = new AppBarConfiguration.Builder(
                R.id.navigation_deadlines, R.id.navigation_events).setOpenableLayout(drawerLayout).build();
        NavController controller = navHostFragment.getNavController();
        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavigationUI.setupWithNavController(navView, controller);
        NavigationUI.setupWithNavController(toolbar, controller, configuration);

    }
}