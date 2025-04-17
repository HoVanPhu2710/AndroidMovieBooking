package com.client.androidmoviebooking.presentation.common;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.client.androidmoviebooking.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo ViewPager2 và BottomNavigationView
        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Thiết lập Adapter cho ViewPager2
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Vô hiệu hóa vuốt ngang của ViewPager2
        viewPager.setUserInputEnabled(false);

        // Đồng bộ ViewPager2 với BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_choose_movie) {
                viewPager.setCurrentItem(0, false); // Không dùng animation
                return true;
            } else if (itemId == R.id.action_choose_theater) {
                viewPager.setCurrentItem(1, false);
                return true;
            } else if (itemId == R.id.action_choose_food) {
                viewPager.setCurrentItem(2, false);
                return true;
            } else if (itemId == R.id.action_profile) {
                viewPager.setCurrentItem(3, false);
                return true;
            }
            return false;
        });

        // Đồng bộ BottomNavigationView khi ViewPager2 thay đổi trang
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.action_choose_movie);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.action_choose_theater);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.action_choose_food);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.action_profile);
                        break;
                }
            }
        });
    }
}