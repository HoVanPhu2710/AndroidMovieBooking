package com.client.androidmoviebooking.presentation.theater.TheaterBrand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.client.androidmoviebooking.R;
import com.client.androidmoviebooking.data.model.response.TheaterBrandResponse;
import com.client.androidmoviebooking.domain.model.theater.TheaterBrand;

import java.util.ArrayList;
import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {

    private List<TheaterBrand> brandList ;
    private int selectedBrandId;
    private OnBrandClickListener clickListener;

    public interface OnBrandClickListener {
        void onBrandClick(int brandId);
    }

    public BrandAdapter(List<TheaterBrand> brandList, int selectedBrandId, OnBrandClickListener clickListener) {
        this.brandList =brandList;
        this.selectedBrandId = selectedBrandId;
        this.clickListener = clickListener;
    }

    public void setSelectedBrand(int brandId) {
        this.selectedBrandId = brandId;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_brand, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandAdapter.BrandViewHolder holder, int position) {
        TheaterBrand brand = brandList.get(position);
        boolean isSelected = brand.getId().equals(selectedBrandId);

        // Set brand name
        holder.brandName.setText(brand.getName());

        // Set text color based on selection
        holder.brandName.setTextColor(holder.itemView.getContext().getResources().getColor(
                isSelected ? R.color.pink : R.color.black));

        // Set logo background based on selection
        holder.logoContainer.setBackgroundResource( isSelected ?
                R.drawable.brand_background_selected : R.drawable.brand_background);

        // Set logo image
        Glide.with(holder.itemView.getContext())
                .load(brand.getLogo())
                .placeholder(R.drawable.dexuat_icon)
                .into(holder.brandLogo);

        // Set click listener
        holder.itemView.setOnClickListener(v ->{
            clickListener.onBrandClick(brand.getId());
        });
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public class BrandViewHolder extends RecyclerView.ViewHolder {
        View logoContainer;
        ImageView brandLogo;
        TextView brandName;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            logoContainer = itemView.findViewById(R.id.logo_container);
            brandLogo = itemView.findViewById(R.id.brand_logo);
            brandName = itemView.findViewById(R.id.brand_name);
        }
    }
}
