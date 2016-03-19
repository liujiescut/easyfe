package com.scut.easyfe.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;

/**
 * 地图相关操作类
 * Created by jay on 16/3/17.
 */
public class MapUtils {
    /**
     * 采用单例,需要用到时实例化
     */
    private static LocationClient mLocationClient;

    public static synchronized LocationClient getLocationClient() {
        if (null == mLocationClient) {
            mLocationClient = new LocationClient(App.get().getApplicationContext());
            initLocationClient();

        }
        return mLocationClient;
    }

    private static void initLocationClient() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        mLocationClient.setLocOption(option);
    }

    public static void getLocation(@NonNull final LocationCallback callback) {
        getLocationClient().registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                //关闭之前打开的定位
                getLocationClient().stop();

                //Receive Location
                StringBuilder sb = new StringBuilder(256);
                sb.append("time : ");
                sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\naddress : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");

                switch (location.getLocType()) {
                    case BDLocation.TypeGpsLocation:     // GPS定位结果
                    case BDLocation.TypeNetWorkLocation: // 网络定位结果
                    case BDLocation.TypeOffLineLocation: // 离线定位结果
                        callback.onSuccess(location.getLatitude(), location.getLongitude(), location.getAddrStr());
                        break;

                    case BDLocation.TypeServerError:          //服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因
                    case BDLocation.TypeNetWorkException:     //网络不同导致定位失败，请检查网络是否通畅
                    case BDLocation.TypeCriteriaException:    //无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机
                        callback.onFailed();

                    default:
                        break;
                }

                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                Log.i(Constants.Tag.MAP_TAG, sb.toString());
            }
        });

        //开始定位
        getLocationClient().start();
    }

    public interface LocationCallback {
        void onSuccess(double latitude, double longitude, String address);

        void onFailed();
    }

}
