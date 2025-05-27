package com.client.androidmoviebooking.presentation.common;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.client.androidmoviebooking.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class TrailerFragment extends Fragment {

    private static final String TAG = "TrailerFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.trailer_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtubePlayerView);
        ImageButton closeButton = view.findViewById(R.id.closeButton);

        if (youTubePlayerView == null) {
            Log.e(TAG, "YouTubePlayerView not found in layout");
            Toast.makeText(requireContext(), "Lỗi giao diện", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thêm YouTubePlayerView vào lifecycle để quản lý trạng thái
        getLifecycle().addObserver(youTubePlayerView);

        // Lấy trailerUrl từ arguments
        String trailerUrl = null;
        if (getArguments() != null) {
            trailerUrl = getArguments().getString("trailerUrl");
        }
        Log.d(TAG, "Received trailerUrl: " + trailerUrl);

        // Trích xuất video ID từ URL
        String videoId = extractVideoId(trailerUrl);
        Log.d(TAG, "Extracted videoId: " + videoId);

        // Khởi tạo YouTube Player
        if (videoId != null && !videoId.isEmpty()) {
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    Log.d(TAG, "YouTubePlayer ready, loading video: " + videoId);
                    youTubePlayer.loadVideo(videoId, 0);
                    BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);
                    bottomNavigationView.setVisibility(View.GONE);
                }

                @Override
                public void onError(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerError error) {
                    Log.e(TAG, "YouTubePlayer error: " + error.name());
                    Toast.makeText(requireContext(), "Lỗi tải video: " + error.name(), Toast.LENGTH_SHORT).show();
                    NavController navController = NavHostFragment.findNavController(TrailerFragment.this);
                    navController.popBackStack();
                }
            });
        } else {
            Log.e(TAG, "Invalid videoId, cannot load trailer");
            Toast.makeText(requireContext(), "Không thể tải trailer", Toast.LENGTH_SHORT).show();
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        }

        // Xử lý sự kiện bấm nút X
        closeButton.setOnClickListener(v -> {
            Log.d(TAG, "Close button clicked");
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack(); // Quay lại fragment trước đó
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView called");
    }

    // Hàm trích xuất video ID từ URL YouTube
    private String extractVideoId(String url) {
        if (url == null || url.isEmpty()) {
            Log.w(TAG, "trailerUrl is null or empty");
            return null;
        }
        String videoId = null;
        String[] patterns = {
                "v=([^&]*)",               // youtube.com/watch?v=VIDEO_ID
                "youtu.be/([^?]*)",         // youtu.be/VIDEO_ID
                "embed/([^?]*)",           // youtube.com/embed/VIDEO_ID
                "v/([^?]*)"                // youtube.com/v/VIDEO_ID
        };
        for (String pattern : patterns) {
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
            java.util.regex.Matcher m = p.matcher(url);
            if (m.find()) {
                videoId = m.group(1);
                break;
            }
        }
        return videoId;
    }
}