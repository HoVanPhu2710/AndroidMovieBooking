package com.client.androidmoviebooking.presentation.layoutmanager;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CenterZoomLayoutManager extends LinearLayoutManager {

    private final float mShrinkAmount = 0.05f;
    private final float mShrinkDistance = 0.5f;
    private final float mMaxZoom = 1.2f;

    public CenterZoomLayoutManager(Context context) {
        super(context, HORIZONTAL, false);
        setInitialPrefetchItemCount(7);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
        scaleChildren();
        return scrolled;
    }

    private void scaleChildren() {
        float midpoint = getWidth() / 2.f;
        float d0 = 0.f;
        float d1 = mShrinkDistance * midpoint;
        float s0 = mMaxZoom;
        float s1 = 1.f - mShrinkAmount;

        if (getChildCount() == 0) return;

        // Tính toán chiều cao của RecyclerView để căn dọc
        float recyclerHeight = getHeight();

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child == null) continue;

            float childMidpoint = (getDecoratedLeft(child) + getDecoratedRight(child)) / 2.f;
            float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
            float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);

            scale = Math.max(scale, s1);

            // Áp dụng scale
            child.setScaleX(scale);
            child.setScaleY(scale);
            child.setAlpha(1.f);

            // Căn dọc và nâng item lên
            float childHeight = child.getHeight() * scale;
            float verticalOffset = recyclerHeight - childHeight - getDecoratedTop(child);
            float raiseAmount = 80f; // Điều chỉnh giá trị này để nâng cao hơn hoặc thấp hơn
            child.setTranslationY(verticalOffset - raiseAmount);
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        scaleChildren();
        if (getChildCount() > 0) {
            RecyclerView recyclerView = getRecyclerView();
            if (recyclerView != null) {
                recyclerView.post(this::scaleChildren);
                recyclerView.post(this::scaleChildren);
            }
        }
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        super.smoothScrollToPosition(recyclerView, state, position);
        recyclerView.post(() -> scaleChildren());
    }

    private RecyclerView getRecyclerView() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child != null) {
                RecyclerView recyclerView = (RecyclerView) child.getParent();
                if (recyclerView != null) {
                    return recyclerView;
                }
            }
        }
        return null;
    }
}