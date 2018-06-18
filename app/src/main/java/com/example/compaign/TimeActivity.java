package com.example.compaign;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.example.compaign.util.SMSMethod;
import com.gjiazhe.multichoicescirclebutton.MultiChoicesCircleButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;

/**
 * 定时防护界面
 *
 * @author : tangtang
 */

public class TimeActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.timemap)
    TextureMapView mapView;
    @Bind(R.id.end_time)
    LinearLayout end;
    @Bind(R.id.aim)
    LinearLayout aim;

    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private BitmapDescriptor mMarker;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
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


        initViews((long)getIntent().getExtras().get("time"));
        initData();
    }

    private void initViews(long time){
        baiduMap = mapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);

        /**
         * 浮动菜单按钮实现
         **/
        MultiChoicesCircleButton.Item item1 = new MultiChoicesCircleButton.Item("电话", getResources().getDrawable(R.drawable.little_phone), 30);
        MultiChoicesCircleButton.Item item2 = new MultiChoicesCircleButton.Item("短信", getResources().getDrawable(R.drawable.little_message), 90);

        List<MultiChoicesCircleButton.Item> buttonItems = new ArrayList<>();
        buttonItems.add(item1);
        buttonItems.add(item2);

        MultiChoicesCircleButton multiChoicesCircleButton = (MultiChoicesCircleButton) findViewById(R.id.multiChoicesCircleButton);
        multiChoicesCircleButton.setButtonItems(buttonItems);

        multiChoicesCircleButton.setOnSelectedItemListener(new MultiChoicesCircleButton.OnSelectedItemListener() {
            @Override
            public void onSelected(MultiChoicesCircleButton.Item item, int index) {
                // 电话报警和短信报警实现，电话报名index1，短信报警index2
                if(index==0){
                    Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"15317203520"));
                    startActivity(intent);
                }else if(index==1){
                    SMSMethod.getInstance(getBaseContext()).SendMessage2("15317203520", "Hi Jarvis");
                }
            }
        });

        multiChoicesCircleButton.setOnHoverItemListener(new MultiChoicesCircleButton.OnHoverItemListener(){
            @Override
            public void onHovered(MultiChoicesCircleButton.Item item, int index) {
                // Do something
            }
        });

        CountdownView mCvCountdownView = (CountdownView)findViewById(R.id.cv_countdownViewTest);
        mCvCountdownView.start(time);
        for (int time0=0; time0<1000; time0++) {
            mCvCountdownView.updateShow(time0);
        }

    }

    private void initData(){
        /*定位相关*/
        baiduMap.setMyLocationEnabled(true);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd0911");
        option.setScanSpan(0);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIsNeedLocationDescribe(true);
        mLocationClient.setLocOption(option);

        mLocationClient.start();

        end.setOnClickListener(this);
        aim.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.aim:
                // 点击要去干嘛需要完成的事件
                break;
            case R.id.end_time:
                // 点击结束防护需要完成的事件
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }




    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if(isFirstLocate)
            {
                /*获取经纬度*/
                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                isFirstLocate = false;
                MapStatus.Builder builder = new MapStatus.Builder()
                        .target(latLng)//地图缩放中心点
                        .zoom(18f);//缩放倍数 百度地图支持缩放21Locat级 部分特殊图层为20级
                //改变地图状态
                baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

            /*获取当前位置显示在地图上*/
            MyLocationData locationData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .direction(0)
                    .build();

            baiduMap.setMyLocationData(locationData);
            mMarker = BitmapDescriptorFactory.fromResource(R.drawable.locate_icon);
            MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,mMarker);
            baiduMap.setMyLocationConfiguration(config);

/*                MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
                locationBuilder.latitude(location.getLatitude());
                locationBuilder.longitude(location.getLongitude());
                MyLocationData locationData = locationBuilder.build();
                baiduMap.setMyLocationData(locationData);
*/
        }
    }



}
