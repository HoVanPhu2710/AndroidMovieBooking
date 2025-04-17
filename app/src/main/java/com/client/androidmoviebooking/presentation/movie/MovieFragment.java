package com.client.androidmoviebooking.presentation.movie;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.client.androidmoviebooking.R;
import com.client.androidmoviebooking.presentation.movie.list.MovieListFragment;

public class MovieFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("MovieFragment", "onCreateView called");
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("MovieFragment", "onViewCreated called");

        // Kiểm tra xem container có tồn tại không
        View containerView = view.findViewById(R.id.movie_list_container);
        if (containerView == null) {
            Log.e("MovieFragment", "Container movie_list_container not found in fragment_movie.xml");
            return;
        }

        if (savedInstanceState == null) {
            try {
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.movie_list_container, new MovieListFragment())
                        .commitNow();
                Log.d("MovieFragment", "MovieListFragment added successfully");
            } catch (Exception e) {
                Log.e("MovieFragment", "Failed to add MovieListFragment", e);
            }
        }
    }
}