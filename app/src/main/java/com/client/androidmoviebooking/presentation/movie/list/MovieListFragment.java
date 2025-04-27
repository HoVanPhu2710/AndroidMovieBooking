package com.client.androidmoviebooking.presentation.movie.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.client.androidmoviebooking.App;
import com.client.androidmoviebooking.di.ViewModelFactory;
import com.client.androidmoviebooking.databinding.FragmentMovieBinding;
import com.client.androidmoviebooking.presentation.layoutmanager.CenterZoomLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

public class MovieListFragment extends Fragment {
    private FragmentMovieBinding binding;
    private MovieListAdapter popularAdapter;
    private GenreMovieAdapter genreAdapter;
    private MovieListViewModel viewModel;

    // Biến để lưu vị trí cuộn của RecyclerView
    private int popularMoviesScrollPosition = 0;
    private int genreMoviesScrollPosition = 0;
    private boolean isDataLoaded = false; // Biến để kiểm tra xem dữ liệu đã được tải chưa

    @Inject
    ViewModelFactory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMovieBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inject Dagger
        ((App) requireActivity().getApplication()).getAppComponent().inject(this);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MovieListViewModel.class);

        // Thiết lập RecyclerView cho phim phổ biến
        CenterZoomLayoutManager layoutManager = new CenterZoomLayoutManager(getContext());
        layoutManager.setInitialPrefetchItemCount(7);
        binding.rvPopularMovies.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.rvPopularMovies);

        // Bật cuộn vô hạn
        popularAdapter = new MovieListAdapter(this::navigateToMovieDetail, true);
        binding.rvPopularMovies.setAdapter(popularAdapter);

        // Tăng phạm vi render để preload các item kế bên
        binding.rvPopularMovies.setItemViewCacheSize(7);
        binding.rvPopularMovies.setPadding(150, 0, 150, 0);
        binding.rvPopularMovies.setClipToPadding(false);

        // Thêm listener để đảm bảo item được căn giữa sau khi cuộn
        binding.rvPopularMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(layoutManager);
                    if (centerView != null) {
                        int position = layoutManager.getPosition(centerView);
                        recyclerView.smoothScrollToPosition(position);
                    }
                }
            }
        });

        // Thiết lập RecyclerView cho phim theo thể loại
        LinearLayoutManager genreLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvGenreMovies.setLayoutManager(genreLayoutManager);
        genreAdapter = new GenreMovieAdapter(this::navigateToMovieDetail);
        binding.rvGenreMovies.setAdapter(genreAdapter);

        // Xử lý nút btn_back để tắt ứng dụng
        binding.btnClose.setOnClickListener(v -> {
            requireActivity().finishAffinity(); // Đóng toàn bộ ứng dụng
        });

        // Quan sát dữ liệu từ ViewModel
        viewModel.getPopularMovies().observe(getViewLifecycleOwner(), movies -> {
            if (movies != null && !movies.isEmpty()) {
                popularAdapter.setMovies(movies);
                // Đảm bảo RecyclerView hoàn tất render trước khi cuộn
                binding.rvPopularMovies.post(() -> {
                    if (movies.size() > 0) {
                        int middlePosition = Integer.MAX_VALUE / 2;
                        int firstItemPosition = middlePosition - (middlePosition % movies.size());

                        binding.rvPopularMovies.post(() -> {
                            layoutManager.findViewByPosition(firstItemPosition - 1);
                            layoutManager.findViewByPosition(firstItemPosition);
                            layoutManager.findViewByPosition(firstItemPosition + 1);
                            layoutManager.findViewByPosition(firstItemPosition + 2);

                            // Khôi phục vị trí cuộn nếu đã có
                            if (popularMoviesScrollPosition != 0) {
                                binding.rvPopularMovies.scrollToPosition(popularMoviesScrollPosition);
                            } else {
                                binding.rvPopularMovies.scrollToPosition(firstItemPosition);
                            }

                            binding.rvPopularMovies.post(() -> {
                                View centerView = snapHelper.findSnapView(layoutManager);
                                if (centerView != null) {
                                    int position = layoutManager.getPosition(centerView);
                                    int offset = (binding.rvPopularMovies.getWidth() - centerView.getWidth()) / 2;
                                    layoutManager.scrollToPositionWithOffset(firstItemPosition, -offset);
                                    binding.rvPopularMovies.post(() -> {
                                        View updatedCenterView = snapHelper.findSnapView(layoutManager);
                                        if (updatedCenterView != null) {
                                            int updatedPosition = layoutManager.getPosition(updatedCenterView);
                                            binding.rvPopularMovies.smoothScrollToPosition(firstItemPosition);
                                        }
                                    });
                                }
                            });
                        });
                    }
                });
            } else {
                popularAdapter.setMovies(new ArrayList<>());
            }
        });

        viewModel.getMoviesByGenre().observe(getViewLifecycleOwner(), moviesByGenre -> {
            if (moviesByGenre != null) {
                genreAdapter.setGenreMovies(moviesByGenre);
                // Khôi phục vị trí cuộn nếu đã có
                if (genreMoviesScrollPosition != 0) {
                    binding.rvGenreMovies.scrollToPosition(genreMoviesScrollPosition);
                }
            } else {
                genreAdapter.setGenreMovies(new HashMap<>());
            }
        });

        // Chỉ tải dữ liệu nếu chưa được tải
        if (!isDataLoaded) {
            viewModel.loadMovies();
            isDataLoaded = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Lưu vị trí cuộn của RecyclerView trước khi fragment tạm dừng
        CenterZoomLayoutManager popularLayoutManager = (CenterZoomLayoutManager) binding.rvPopularMovies.getLayoutManager();
        LinearLayoutManager genreLayoutManager = (LinearLayoutManager) binding.rvGenreMovies.getLayoutManager();
        if (popularLayoutManager != null) {
            popularMoviesScrollPosition = popularLayoutManager.findFirstVisibleItemPosition();
        }
        if (genreLayoutManager != null) {
            genreMoviesScrollPosition = genreLayoutManager.findFirstVisibleItemPosition();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Khôi phục vị trí cuộn của RecyclerView khi fragment tiếp tục
        if (popularMoviesScrollPosition != 0) {
            binding.rvPopularMovies.scrollToPosition(popularMoviesScrollPosition);
        }
        if (genreMoviesScrollPosition != 0) {
            binding.rvGenreMovies.scrollToPosition(genreMoviesScrollPosition);
        }
    }

    private void navigateToMovieDetail(MovieListAdapter.MovieItem movieItem) {
        NavDirections action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movieItem.getSlug());
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Tránh rò rỉ bộ nhớ
    }
}