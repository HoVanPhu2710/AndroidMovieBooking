package com.client.androidmoviebooking.presentation.common;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.client.androidmoviebooking.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (bottomNavigationView == null) {
            Log.e("MainActivity", "BottomNavigationView not found in layout!");
        }

        // Khởi tạo NavController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            Log.d("MainActivity", "NavController initialized");
            // Đồng bộ BottomNavigationView với NavController
            NavigationUI.setupWithNavController(bottomNavigationView, navController);

            // Lắng nghe thay đổi destination
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                int destinationId = destination.getId();
                Log.d("MainActivity", "Destination changed to: " + destination.getLabel() + " (ID: " + destinationId + ")");
                if (destinationId == R.id.movieDetailFragment|| destinationId == R.id.action_movieDetailFragment_to_trailerFragment || destinationId == R.id.trailerFragment || destinationId == R.id.theaterDetailFragment) {
                    bottomNavigationView.setVisibility(View.GONE);
                    Log.d("MainActivity", "Hiding BottomNavigationView for destination: " + destination.getLabel());
                } else {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    Log.d("MainActivity", "Showing BottomNavigationView for destination: " + destination.getLabel());
                }
            });
        } else {
            Log.e("MainActivity", "NavHostFragment not found!");
        }
    }

    @Override
    public void onBackPressed() {
        if (!navController.popBackStack()) {
            super.onBackPressed();
        }
    }
}