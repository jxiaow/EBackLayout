package cn.xwj.ebacklayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.xwj.backlayout.SwipeBackHelper;

/**
 * Author: xw
 * Create: 2018年01月22 10:38
 * Description: this is BaseFragment
 */

public class BaseFragment extends Fragment {
    private SwipeBackHelper mSwipeBackHelper = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSwipeBackHelper = SwipeBackHelper.create(this);
        View view = inflater.inflate(R.layout.activity_main2, container, false);
        return view;
    }
}
