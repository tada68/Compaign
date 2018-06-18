package com.example.compaign;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";

    @Bind(R.id.mmap)
    TextureMapView mapView;
    @Bind(R.id.main_call)
    LinearLayout call;
    @Bind(R.id.main_message)
    LinearLayout message;
    @Bind(R.id.main_photo)
    LinearLayout photo;
    @Bind(R.id.emergency)
    LinearLayout emergency;
    @Bind(R.id.time)
    FloatingActionButton time;

    //定位相关
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private BitmapDescriptor mMarker;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initViews();
        initData();
        super.onActivityCreated(savedInstanceState);
    }

    private void initViews(){
        baiduMap = mapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mLocationClient = new LocationClient(getActivity());
        mLocationClient.registerLocationListener(myListener);

        /*按钮点击事件相关*/
        call.setOnClickListener(this);
        message.setOnClickListener(this);
        photo.setOnClickListener(this);
        emergency.setOnClickListener(this);
        time.setOnClickListener(this);
    }

    private void initData(){
        /*定位相关*/
        baiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getActivity());
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd0911");
        option.setScanSpan(0);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIsNeedLocationDescribe(true);
        mLocationClient.setLocOption(option);

        mLocationClient.start();

        /*按钮点击事件*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*点击调用电话实现*/
            case R.id.main_call:
                break;
            /*点击调用短信实现*/
            case R.id.main_message:
                break;
            /*点击跳转至急救措施实现*/
            case R.id.emergency:
                break;
            /*点击调用相机逻辑实现*/
            case R.id.main_photo:
                break;
            /* 定时防护逻辑实现*/
            case R.id.time:
                Intent intent = new Intent(getActivity(),TimePickerActivity.class);
                startActivity(intent);
                break;
        }
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
                        .zoom(18f);//缩放倍数 百度地图支持缩放21级 部分特殊图层为20级
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

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }
}
