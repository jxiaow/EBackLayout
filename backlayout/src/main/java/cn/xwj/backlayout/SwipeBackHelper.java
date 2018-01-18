package cn.xwj.backlayout;

import android.app.Activity;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.FrameLayout;

import static android.arch.lifecycle.Lifecycle.Event;

public class SwipeBackHelper implements LifecycleObserver {
    private LifecycleOwner mOwner;
    private SwipeBackLayout mSwipeBackLayout;

    /**
     * 创建SwipeBackHelper,并将SwipeBackHelper注册到Activity中
     *
     * @param owner {@link LifecycleOwner},传入的必须是activity的子类
     * @return
     */
    public static SwipeBackHelper create(LifecycleOwner owner) {
        return new SwipeBackHelper(owner);
    }

    private SwipeBackHelper(LifecycleOwner owner) {
        if (owner == null || !(owner instanceof Activity)) {
            throw new IllegalArgumentException("owner must be instanceof Activity");
        }
        this.mOwner = owner;
        this.mOwner.getLifecycle().addObserver(this);
        onActivityCreate();
    }

    /**
     * 设置是否需要滑动返回
     *
     * @param enable true, false
     */
    public void setSwipeBackEnable(boolean enable) {
        if (getSwipeBackLayout() != null) {
            getSwipeBackLayout().setEnableGesture(enable);
        }
    }

    /**
     * 设置滑动的触发方向
     *
     * @param edge {@link SwipeBackLayout#EDGE_LEFT,SwipeBackLayout#EDGE_RIGHT,SwipeBackLayout#EDGE_BOTTOM,SwipeBackLayout#EDGE_ALL }
     */
    public void setEdgeTrackingFlags(int edge) {
        if (getSwipeBackLayout() != null) {
            getSwipeBackLayout().setEdgeTrackingFlags(edge);
        }
    }

    private Activity getActivity() {
        return (Activity) this.mOwner;
    }

    @SuppressWarnings("deprecation")
    private void onActivityCreate() {
        getActivity().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getActivity().getWindow().getDecorView().setBackgroundDrawable(null);
        mSwipeBackLayout = new SwipeBackLayout(getActivity());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mSwipeBackLayout.setLayoutParams(layoutParams);
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                SwipeBackUtil.convertActivityToTranslucent(getActivity());
            }

            @Override
            public void onScrollOverThreshold() {
            }
        });
    }

    @OnLifecycleEvent(Event.ON_CREATE)
    public void onPostCreate() {
        mSwipeBackLayout.attachToActivity(getActivity());
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    @OnLifecycleEvent(Event.ON_DESTROY)
    public void onDestroy() {
        if (this.mOwner != null && this.mOwner.getLifecycle() != null) {
            this.mOwner.getLifecycle().removeObserver(this);
        }
    }
}
