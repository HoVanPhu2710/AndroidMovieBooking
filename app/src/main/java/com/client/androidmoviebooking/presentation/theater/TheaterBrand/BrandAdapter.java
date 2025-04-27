package com.client.androidmoviebooking.presentation.theater.TheaterBrand;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.client.androidmoviebooking.data.model.response.TheaterBrandResponse;
import com.client.androidmoviebooking.domain.model.theater.TheaterBrand;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {

    private List<TheaterBrandItem> brandResponseList

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BrandAdapter.BrandViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class BrandViewHolder extends RecyclerView.ViewHolder {
        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
