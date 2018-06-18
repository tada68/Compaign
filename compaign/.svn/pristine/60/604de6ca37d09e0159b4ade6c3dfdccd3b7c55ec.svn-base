package com.example.compaign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tangtang
 */

public class TimePickerActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.wheelview)
    WheelView wheelView;
    @Bind(R.id.close)
    ImageView close;
    @Bind(R.id.next_page)
    ImageButton nextButton;
    String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        ButterKnife.bind(this);
        /**
         * 设置状态栏颜色
         */
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.blue));


        wheelView.setTextSize(22);
        wheelView.setTextColorOut(0xc96d3cb);
        wheelView.setTextColorCenter(R.color.blue);
        wheelView.setDividerColor(0xFFFFFFF);
        wheelView.setTextXOffset(2);
        wheelView.setCyclic(false);

        final List<String> mOptionsItems = new ArrayList<>();
        mOptionsItems.add("30分钟");
        mOptionsItems.add("40分钟");
        mOptionsItems.add("50分钟");
        mOptionsItems.add("60分钟");

        wheelView.setAdapter(new ArrayWheelAdapter(mOptionsItems));
        wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                time = mOptionsItems.get(index);
                Toast.makeText(TimePickerActivity.this, "" + mOptionsItems.get(index), Toast.LENGTH_SHORT).show();
            }
        });

        initViews();

    }

    private void initViews(){
        close.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
            case R.id.close:
                finish();
                break;
            case R.id.next_page:
                Intent intent = new Intent(TimePickerActivity.this,TimeActivity.class);
                intent.putExtra("time",time);
                startActivity(intent);
                finish();
                break;
        }
    }
}
