package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.customView.SimpleHUD.PairProgressHUD;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.MapUtils;
import com.scut.easyfe.utils.OtherUtils;

/**
 * 获取地址信息页面
 * @author jay
 */
public class AddressActivity extends BaseActivity {
    public static final int RESULT_OK = 0;

    private MapView mMapView;
    private TextView mAddressTextView;
    private BaiduMap mBaiduMap;
    private double mLatitude = -1d;    //定位所在的纬度
    private double mLongitude = -1d;   //定位所在的经度
    private String mAddress;           //定位所在地址

    private GeoCoder mGeoCoder;        //地理编码工具
    private ReverseGeoCodeOption mGeoCoderOptions;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_address);
    }

    @Override
    protected void initData() {
        mGeoCoder = GeoCoder.newInstance();
        mGeoCoderOptions = new ReverseGeoCodeOption();

        if (null != getIntent()) {
            Bundle bundle = getIntent().getExtras();
            mLatitude = bundle.getDouble(Constants.Key.LATITUDE);
            mLongitude = bundle.getDouble(Constants.Key.LONGITUDE);
            mAddress = bundle.getString(Constants.Key.ADDRESS);
        }
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.titlebar_tv_title)).setText("选择家庭地址");
        findViewById(R.id.titlebar_tv_right).setVisibility(View.VISIBLE);

        mMapView = OtherUtils.findViewById(this, R.id.address_map);
        mMapView.removeViewAt(1);    // 删除百度地图logo
        mBaiduMap = mMapView.getMap();
        mAddressTextView = OtherUtils.findViewById(this, R.id.address_current);

        if(null != mAddress){
            mAddressTextView.setText(mAddress);
        }
        if (mLatitude != -1) {
            addOverlay(mLatitude, mLongitude);
        }else {
            PairProgressHUD.showLoading(this, "定位中");
            MapUtils.getLocation(new MapUtils.LocationCallback() {
                @Override
                public void onSuccess(double latitude, double longitude, String address) {
                    mLatitude = latitude;
                    mLongitude = longitude;
                    addOverlay(mLatitude, mLongitude);
                    PairProgressHUD.dismiss();
                }

                @Override
                public void onFailed() {
                    LogUtils.i(Constants.Tag.MAP_TAG, "定位失败");
                    toast("定位失败,请确认是否允许相应权限");
                    PairProgressHUD.dismiss();
                    finish();
                }
            });
        }
    }

    @Override
    protected void initListener() {
        mGeoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            public void onGetGeoCodeResult(GeoCodeResult result) {
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    toast("匹配位置失败,再移动定位图标试试");
                    if(null != result){
                        LogUtils.i(Constants.Tag.MAP_TAG, result.error.name());
                    }
                    return;
                }
                mAddressTextView.setText(result.getAddress());
                mAddress = result.getAddress();
            }
        });
        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LogUtils.i(Constants.Tag.MAP_TAG, marker.getPosition().latitude + "  " + marker.getPosition().longitude);

                mLatitude = marker.getPosition().latitude;
                mLongitude = marker.getPosition().longitude;
                mGeoCoderOptions.location(new LatLng(mLatitude, mLongitude));
                mGeoCoder.reverseGeoCode(mGeoCoderOptions);
            }

            @Override
            public void onMarkerDragStart(Marker marker) {

            }
        });
    }

    /**
     * 在经纬度上添加覆盖物
     * @param latitude    纬度
     * @param longitude   经度
     */
    private void addOverlay(double latitude, double longitude) {
        LatLng point = new LatLng(latitude, longitude);
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_map_gold_large);
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap).draggable(true);
        mBaiduMap.addOverlay(option);

        /**
         * 用经纬度定义地图默认显示中心
         */
        MapStatus status = new MapStatus.Builder().target(new LatLng(latitude, longitude)).zoom(16).build();
        MapStatusUpdate statusUpdate = MapStatusUpdateFactory
                .newMapStatus(status);
        mBaiduMap.setMapStatus(statusUpdate);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PairProgressHUD.dismiss();
        finish();
    }

    public void onBackClick(View view){
        onBackPressed();
    }

    public void onRightClick(View view){
        if(mAddress != null && mLatitude != -1 && mLongitude != -1){
            toast("选择位置成功");
            Intent intent = getIntent();
            intent.putExtra(Constants.Key.ADDRESS, mAddress);
            intent.putExtra(Constants.Key.LATITUDE, mLatitude);
            intent.putExtra(Constants.Key.LONGITUDE, mLongitude);
            this.setResult(RESULT_OK, intent);
        }else {
            toast("选择位置失败,请重试");
        }
        finish();
    }
}
