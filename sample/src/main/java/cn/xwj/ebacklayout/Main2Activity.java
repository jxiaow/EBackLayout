package cn.xwj.ebacklayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import cn.xwj.backlayout.SwipeBackHelper;

public class Main2Activity extends AppCompatActivity {
    public static final String TAG = "Main2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        SwipeBackHelper.create(this);
        TextView textView = this.findViewById(R.id.text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Main2Activity destroy");
    }
}
