package com.client.androidmoviebooking.presentation.theater.TheaterDetail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.client.androidmoviebooking.R;
import com.client.androidmoviebooking.domain.model.theater.DateItem;

import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {

    private List<DateItem> dateItems;
    private OnDateClickListener listener;
    private int selectedPosition = 0;

    public interface OnDateClickListener {
        void onDateClick(DateItem dateItem, int position);
    }

    public DateAdapter(List<DateItem> dateItems, OnDateClickListener listener) {
        this.dateItems = dateItems;
        this.listener = listener;
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    @NonNull
    @Override
    public DateAdapter.DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateAdapter.DateViewHolder holder, int position) {
        DateItem dateItem = dateItems.get(position);
        holder.labelTextView.setText(dateItem.getLabel());
        holder.dayTextView.setText(dateItem.getDay());

        // Set selected state
        if (position == selectedPosition) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.pink));
            holder.labelTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
            holder.dayTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
            holder.labelTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
            holder.dayTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDateClick(dateItem, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dateItems.size();
    }

    static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView labelTextView;
        TextView dayTextView;
        CardView cardView;

        DateViewHolder(@NonNull View itemView) {
            super(itemView);
            labelTextView = itemView.findViewById(R.id.text_label);
            dayTextView = itemView.findViewById(R.id.text_day);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
