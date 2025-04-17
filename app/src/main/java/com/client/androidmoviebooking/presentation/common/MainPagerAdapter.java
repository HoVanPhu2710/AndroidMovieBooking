package com.client.androidmoviebooking.presentation.common;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.client.androidmoviebooking.presentation.movie.MovieFragment;
// Thêm các Fragment khác nếu cần
// import com.client.androidmoviebooking.presentation.theater.TheaterFragment;
// import com.client.androidmoviebooking.presentation.food.FoodFragment;
// import com.client.androidmoviebooking.presentation.profile.ProfileFragment;

public class MainPagerAdapter extends FragmentStateAdapter {

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MovieFragment();
            case 1:
                return new Fragment(); // Thay bằng TheaterFragment khi bạn tạo
            case 2:
                return new Fragment(); // Thay bằng FoodFragment khi bạn tạo
            case 3:
                return new Fragment(); // Thay bằng ProfileFragment khi bạn tạo
            default:
                return new MovieFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Có 4 tab: Chọn phim, Chọn rạp, Bắp nước, Tôi
    }
}