package com.bupt.weeat.view.customView;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.bupt.weeat.utils.LogUtils;

public class SwipeLoadRefreshLayout extends SwipeRefreshLayout {
    RecyclerView recyclerView;
    //按下去的时候位置
    private int mLastY, mYDown;
    private OnLoadListener mOnLoadListener;
    //最小出发距离
    private int mTouchSlop;
    /**
     * 是否在加载中 ( 上拉加载更多 )
     */
    private boolean isLoading = false;

    public SwipeLoadRefreshLayout(Context context) {
        this(context, null);
    }

    public SwipeLoadRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        LogUtils.i("SwipeLoadRefreshLayout", mTouchSlop + "");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (recyclerView == null) {
            int childCount = getChildCount();
            if (childCount > 0) {
                for (int i = 0; i < childCount; i++) {
                    View childView = getChildAt(i);
                    if (childView instanceof RecyclerView) {
                        recyclerView = (RecyclerView) childView;
                        addListener(recyclerView);
                        break;
                    }
                }
            }
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @SuppressWarnings("deprecation")
    private void addListener(RecyclerView recyclerView) {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    /**
     * 判断是否到了最底部
     */
    private boolean isBottom() {
        if (recyclerView != null && recyclerView.getAdapter() != null) {
            return ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() == (recyclerView.getAdapter().getItemCount() - 1);
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mYDown = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                if (canLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);

    }

    /**
     * 上拉加载.
     */
    private void loadData() {
        if (mOnLoadListener != null) {
            setLoading(true);
            mOnLoadListener.onLoad();
        }
    }

    /**
     * 是否可以加载更多, 条件是到了最底部, RecyclerView不在加载中, 且为上拉操作.
     */
    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }

    /**
     * 是否是上拉操作,
     * mTouchSlop 在被判定为滚动之前用户手指可以移动的最大值。
     */

    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    public void setOnLoadListener(OnLoadListener mOnLoadListener) {
        this.mOnLoadListener = mOnLoadListener;
    }

    /**
     * 设置加载状态
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (!isLoading) {
            mYDown = 0;
            mLastY = 0;
        }
    }

    public interface OnLoadListener {
        void onLoad();
    }
}
