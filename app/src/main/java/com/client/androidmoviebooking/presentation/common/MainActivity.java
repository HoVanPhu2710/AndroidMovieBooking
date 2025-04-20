package com.client.androidmoviebooking.presentation.common;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
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

        // Khởi tạo NavController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            // Đồng bộ BottomNavigationView với NavController
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }
    }

    // Phương thức để ẩn BottomNavigationView
    public void hideBottomNavigation() {
        bottomNavigationView.setVisibility(View.GONE);
    }

    // Phương thức để hiển thị BottomNavigationView
    public void showBottomNavigation() {
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        // Kiểm tra xem có thể quay lại fragment trước đó không
        if (!navController.popBackStack()) {
            // Nếu không còn fragment nào để quay lại, gọi super để kết thúc Activity
            super.onBackPressed();
        }
    }

    // Hàm để thêm animation khi chuyển giữa các fragment
    // Hàm để thêm animation khi chuyển giữa các fragment
//    private void navigateWithAnimation() {
//        NavOptions navOptions = new NavOptions.Builder()
//                .setEnterAnim(R.anim.slide_in_left)    // Animation khi vào fragment (từ trái sang)
//                .setExitAnim(R.anim.slide_out_right)   // Animation khi rời khỏi fragment (ra phải)
//                .setPopEnterAnim(R.anim.slide_in_right) // Animation khi quay lại fragment (từ phải sang)
//                .setPopExitAnim(R.anim.slide_out_left)  // Animation khi quay lại (ra trái)
//                .build();
//
//        // Ví dụ: Điều hướng đến movieDetailFragment với animation
//        navController.navigate(R.id.action_movieListFragment_to_movieDetailFragment, null, navOptions);
//    }
}