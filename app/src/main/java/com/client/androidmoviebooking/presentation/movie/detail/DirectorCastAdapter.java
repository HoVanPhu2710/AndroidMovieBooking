package com.client.androidmoviebooking.presentation.movie.detail;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.client.androidmoviebooking.R;
import com.client.androidmoviebooking.domain.model.MovieCast;
import com.client.androidmoviebooking.domain.model.MovieDirector;
import com.client.androidmoviebooking.domain.model.PersonInMovie;
import com.client.androidmoviebooking.domain.repository.CastItem;
import com.client.androidmoviebooking.domain.repository.DirectorItem;

import java.util.List;

public class DirectorCastAdapter extends RecyclerView.Adapter<DirectorCastAdapter.PersonViewHolder> {
    private final List<PersonInMovie> items;

    public DirectorCastAdapter(List<PersonInMovie> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_director_cast, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        PersonInMovie item = items.get(position);

        if (item.getViewType() == 0) { // Director
            MovieDirector director = ((DirectorItem) item).getDirector();
            holder.nameTextView.setText(director.getName());
            holder.roleTextView.setText("Đạo diễn");
            Glide.with(holder.photoImageView.getContext())
                    .load(director.getAvatar())
                    .into(holder.photoImageView);
        } else if (item.getViewType() == 1) { // Cast
            MovieCast cast = ((CastItem) item).getCast();
            holder.nameTextView.setText(cast.getName());
            holder.roleTextView.setText("Diễn viên");
            Glide.with(holder.photoImageView.getContext())
                    .load(cast.getAvatarUrl())
                    .into(holder.photoImageView);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;
        TextView nameTextView, roleTextView;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.ivDirectorCastPhoto);
            nameTextView = itemView.findViewById(R.id.tvDirectorCastName);
            roleTextView = itemView.findViewById(R.id.tvDirectorCastRole);
        }
    }
}

