package cn.xwj.backlayout;

import android.app.Activity;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import static android.arch.lifecycle.Lifecycle.Event;

public class SwipeBackHelper implements LifecycleObserver, SwipeBackLayout.SwipeListener {
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
        if (owner == null) {
            throw new IllegalArgumentException("owner must not is null");
        }
        this.mOwner = owner;
        if (!isFragment()) {
            onActivityCreate();
        } else {
            onFragmentCreate();
        }
        this.mOwner.getLifecycle().addObserver(this);
    }

    private void onFragmentCreate() {
        mSwipeBackLayout = new SwipeBackLayout(getFragment().getActivity());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mSwipeBackLayout.setLayoutParams(layoutParams);
        mSwipeBackLayout.addSwipeListener(this);
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
        if (this.mOwner instanceof Fragment) {
            return getFragment().getActivity();
        }
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
        mSwipeBackLayout.addSwipeListener(this);
    }

    @OnLifecycleEvent(Event.ON_CREATE)
    public void onPostCreate() {
        if (!isFragment()) {
            mSwipeBackLayout.attachToActivity(getActivity());
        }
    }

    @OnLifecycleEvent(Event.ON_START)
    public void start() {
        if (isFragment()) {
            mSwipeBackLayout.attachToFragment(getFragment());
        }
    }

    private Fragment getFragment() {
        return (Fragment) this.mOwner;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    @OnLifecycleEvent(Event.ON_DESTROY)
    public void onDestroy() {
        if (this.mOwner != null && this.mOwner.getLifecycle() != null) {
            this.mOwner.getLifecycle().removeObserver(this);
        }
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.removeSwipeListener(this);
        }
    }

    private boolean isFragment() {
        return this.mOwner instanceof Fragment;
    }

    @Override
    public void onScrollStateChange(int state, float scrollPercent) {

    }

    @Override
    public void onEdgeTouch(int edgeFlag) {
        if (!isFragment()) {
            SwipeBackUtil.convertActivityToTranslucent(getActivity());
        } else {
//            SwipeBackUtil.convertFragmentToTranslucent(getFragment());
            getFragment().getActivity().getSupportFragmentManager().beginTransaction().remove(getFragment()).commit();
        }
    }

    @Override
    public void onScrollOverThreshold() {
    }
}
