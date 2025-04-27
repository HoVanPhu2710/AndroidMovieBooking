package com.client.androidmoviebooking.presentation.movie.detail;

import com.client.androidmoviebooking.domain.model.movie.PersonInMovie;

public class DirectorCastItem implements PersonInMovie {
    private String name;
    private String avatarUrl;
    private String role;

    public DirectorCastItem(String name, String avatarUrl, String role) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getRole() {
        return role;
    }

    @Override
    public int getViewType() {
        return role.equals("Đạo diễn") ? 0 : 1; // 0 cho đạo diễn, 1 cho diễn viên
    }
}