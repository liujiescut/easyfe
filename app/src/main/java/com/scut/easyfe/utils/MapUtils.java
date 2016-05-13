package com.scut.easyfe.utils;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;

import java.util.List;

/**
 * 地图相关操作类
 * Created by jay on 16/3/17.
 */
public class MapUtils {
    /**
     * 采用单例,需要用到时实例化
     */
    private static LocationClient mLocationClient;
    private static RoutePlanSearch mRoutePlanSearch;

    /**
     * 获取定位的代理
     */
    public static synchronized LocationClient getLocationClient() {
        if (null == mLocationClient) {
            mLocationClient = new LocationClient(App.get().getApplicationContext());
            initLocationClient();

        }
        return mLocationClient;
    }

    /**
     * 获取路线规划代理
     */
    public static synchronized RoutePlanSearch getRoutePlanSearch() {
        if (null == mRoutePlanSearch) {
            mRoutePlanSearch = RoutePlanSearch.newInstance();
        }
        return mRoutePlanSearch;
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
                        callback.onSuccess(location.getLatitude(), location.getLongitude(), location.getAddrStr(), location.getCity());
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


    /**
     * 获取两点之间公交时间
     *
     * @param startLatitude  起始点纬度
     * @param startLongitude 起始点经度
     * @param endLatitude    终点纬度
     * @param endLongitude   终点经度
     * @param city           所在城市
     * @param callback       查询回调
     */
    public synchronized static void getDurationFromPosition(double startLatitude, double startLongitude,
                                                            double endLatitude, double endLongitude, String city,
                                                            final GetDurationCallback callback) {
        if(null == city || city.length() == 0){
            Toast.makeText(App.get().getApplicationContext(), "获取公交时间失败", Toast.LENGTH_SHORT).show();
            return;
        }

        getRoutePlanSearch().setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult result) {
//                        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//                            Toast.makeText(mContextReference.get(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
//                        }

//                        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
//                            起终点或途经点地址有岐义，通过以下接口获取建议查询信息
//                            result.getSuggestAddrInfo()
//                            return;
//                        }

                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    List<TransitRouteLine> lines = result.getRouteLines();
                    int minDuration = Integer.MAX_VALUE;
                    for (TransitRouteLine line :
                            lines) {
                        minDuration = minDuration > line.getDuration() ? line.getDuration() : minDuration;
                    }

                    LogUtils.i(Constants.Tag.MAP_TAG, minDuration + "");
                    if (Integer.MAX_VALUE == minDuration) {
                        callback.onFailed("没有可用公交信息");
                    } else {
                        callback.onSuccess(minDuration);
                    }

                } else if (result.error == SearchResult.ERRORNO.ST_EN_TOO_NEAR) {
                    //距离很近
                    callback.onSuccess(0);

                } else {
                    LogUtils.i(Constants.Tag.MAP_TAG, result.error + "");
                    callback.onFailed("获取公交信息失败");
                }
            }


            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
            }
        });

        PlanNode startNode = PlanNode.withLocation(
                new LatLng(startLatitude, startLongitude));
        PlanNode endNode = PlanNode.withLocation(
                new LatLng(endLatitude, endLongitude));
        mRoutePlanSearch.transitSearch(
                (new TransitRoutePlanOption()).city(city).
                        from(startNode).
                        to(endNode));
    }

    /**
     * 定位回调
     */
    public interface LocationCallback {
        void onSuccess(double latitude, double longitude, String address, String city);

        void onFailed();
    }

    /**
     * 获取两点之间公交时间回调
     */
    public interface GetDurationCallback {
        void onSuccess(int durationSeconds);

        void onFailed(String errorMsg);
    }

}
