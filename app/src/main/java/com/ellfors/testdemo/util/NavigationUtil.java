package com.ellfors.testdemo.util;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 导航工具类
 * (以火星坐标系为准)
 */
public class NavigationUtil
{
    private static final String GD_PACKAGE = "com.autonavi.minimap";
    private static final String BD_PACKAGE = "com.baidu.BaiduMap";
    private static final String TX_PACKAGE = "com.tencent.map";
    private static final String GD_NAME = "高德地图";
    private static final String BD_NAME = "百度地图";
    private static final String TX_NAME = "腾讯地图";
    private static final String ERROR_TIPS = "请安装高德地图或百度地图";
    private static final String TX_KEY = "KOQBZ-JTYHW-KGBRQ-O72HF-TUG6Z-UQBEI";

    /**
     * 弹出选择地图列表
     *
     * @param context   上下文
     * @param lon       经度
     * @param lat       纬度
     * @param describle 简述
     */
    public static void showMapsList(final Activity context, final double lon, final double lat, final String describle)
    {
        if (context == null || TextUtils.isEmpty(describle) || lon == 0 || lat == 0)
            return;
        DialogUtil.showChooseSelectDialog(
                context,
                getInstallMapNames(),
                new DialogChooseListAdapter.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        switch (getInstallMapNames().get(position))
                        {
                            case GD_NAME:
                                openGaoDeMap(context, lon, lat, describle);
                                break;
                            case BD_NAME:
                                openBaiduMap(context, lon, lat, describle);
                                break;
                            case TX_NAME:
                                openTencentMap(context, lon, lat, describle);
                            default:
                                break;
                        }
                    }
                }
        );
    }

    /**
     * 高德导航
     *
     * @param lon       经度
     * @param lat       纬度
     * @param describle 简述
     */
    private static void openGaoDeMap(Context context, double lon, double lat, String describle)
    {
        try
        {
            if (isInstallPackage(context, GD_PACKAGE))
            {
                StringBuilder loc = new StringBuilder();
                loc
                        .append("amapuri://route/plan")
                        .append("?sourceApplication=")
                        .append("天匠工程师")
                        .append("&dname=")
                        .append(describle)
                        .append("&dlat=")
                        .append(lat)
                        .append("&dlon=")
                        .append(lon)
                        .append("&dev=0")
                        .append("&t=0");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage(GD_PACKAGE);
                intent.setData(Uri.parse(loc.toString()));
                context.startActivity(intent);
            }
            else
            {
                StringBuilder url = new StringBuilder();
                url
                        .append("https://uri.amap.com/navigation")
                        .append("?to=")
                        .append(lon)
                        .append(",")
                        .append(lat)
                        .append(",")
                        .append(describle)
                        .append("&mode=car&policy=0");
                openUrl(context, url.toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 百度导航
     *
     * @param lon       经度
     * @param lat       纬度
     * @param describle 简述
     */
    private static void openBaiduMap(Context context, double lon, double lat, String describle)
    {
        double lonEnd = gaoDeToBaidu(lon, lat)[0];
        double latEnd = gaoDeToBaidu(lon, lat)[1];
        try
        {
            if (isInstallPackage(context, BD_PACKAGE))
            {
                StringBuilder loc = new StringBuilder();
                loc
                        .append("baidumap://map/direction")
                        .append("?origin=我的位置")
                        .append("&destination=latlng:")
                        .append(latEnd)
                        .append(",")
                        .append(lonEnd)
                        .append("|name:")
                        .append(describle)
                        .append("&mode=driving")
                        .append("&coord_type=bd09ll")
                        .append("&src=")
                        .append(TextUtils.isEmpty(context.getPackageName()) ? "" : context.getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage(BD_PACKAGE);
                intent.setData(Uri.parse(loc.toString()));
                context.startActivity(intent);
            }
            else
            {
                /**
                 * http://api.map.baidu.com/direction
                 * ?origin=latlng:34.264642646862,108.95108518068|name:我家
                 * &destination=大雁塔
                 * &mode=driving
                 * &region=西安
                 * &output=html
                 * &src=webapp.baidu.openAPIdemo
                 */
//                StringBuilder url = new StringBuilder();
//                url.append("http://api.map.baidu.com/direction");
//
//                url.append("?origin=latlng:40.047669,116.313082|name:我的位置");
//
//                url.append("&destination=latlng:");
//                url.append(latEnd);
//                url.append(",");
//                url.append(lonEnd);
//                url.append("|name:");
//                url.append(describle);
//
//                url.append("&coord_type=bd09ll");
//                url.append("mode=driving");
//                url.append("&output=html");
//                url.append("&src=com.ellfors.testdemo");

                String url = "http://api.map.baidu.com/direction?origin=我的位置&destination=latlng:" + latEnd + "," + lonEnd + "|name:大雁塔&mode=driving&output=html&src=webapp.baidu.openAPIdemo";

                openUrl(context, url.toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 腾讯导航
     *
     * @param lon       经度
     * @param lat       纬度
     * @param describle 简述
     */
    private static void openTencentMap(Context context, double lon, double lat, String describle)
    {
        try
        {
            if (isInstallPackage(context, TX_PACKAGE))
            {
                StringBuilder loc = new StringBuilder();
                loc
                        .append("qqmap://map/routeplan")
                        .append("?type=drive")
                        .append("&from=我的位置")
                        .append("&fromcoord=CurrentLocation")
                        .append("&to=")
                        .append(describle)
                        .append("&tocoord=")
                        .append(lat)
                        .append(",")
                        .append(lon)
                        .append("&referer=")
                        .append(TX_KEY);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage(TX_PACKAGE);
                intent.setData(Uri.parse(loc.toString()));
                context.startActivity(intent);
            }
            else
            {
                StringBuilder url = new StringBuilder();
                url
                        .append("https://apis.map.qq.com/uri/v1/routeplan")
                        .append("?type=drive")
                        .append("&to=")
                        .append(describle)
                        .append("&tocoord=")
                        .append(lat)
                        .append(",")
                        .append(lon)
                        .append("&policy=2")
                        .append("&referer=")
                        .append(TX_KEY);
                openUrl(context, url.toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 打开浏览器
     */
    private static void openUrl(Context context, String url)
    {
        // 判断url是否有效地址
        if (!URLUtil.isNetworkUrl(url) || context == null)
            return;
        // 调用系统浏览器
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    /**
     * 判断手机是否有APP
     */
    private static boolean isInstallPackage(Context context, String packageName)
    {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null)
        {
            for (int i = 0; i < pinfo.size(); i++)
            {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取可使用的地图名称列表
     */
    private static List<String> getInstallMapNames()
    {
        List<String> list = new ArrayList<>();
        list.add(GD_NAME);
        list.add(BD_NAME);
        list.add(TX_NAME);
        return list;
    }

    /**
     * 百度转高德坐标系
     *
     * @param bd_lon 百度纬度
     * @param bd_lat 百度经度
     * @return double[0]高德经度   double[1]高德纬度
     */
    public static double[] bdToGaoDe(double bd_lon, double bd_lat)
    {
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.cos(theta);
        gd_lat_lon[1] = z * Math.sin(theta);
        return gd_lat_lon;
    }

    /**
     * 高德转百度坐标系
     *
     * @param gd_lon 高德经度
     * @param gd_lat 高德纬度
     * @return double[0]百度经度  double[1]百度纬度
     */
    public static double[] gaoDeToBaidu(double gd_lon, double gd_lat)
    {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }

    /**
     * 计算俩点之间的距离
     *
     * @param locLng    本地经度
     * @param locLat    本地纬度
     * @param targetLng 目标经度
     * @param targetLat 目标纬度
     * @return 距离（单位：米）
     */
    public static float calculateLineDistance(double locLng, double locLat, double targetLng, double targetLat)
    {
        try
        {
            double var = 0.01745329251994329D;
            locLng *= 0.01745329251994329D;
            locLat *= 0.01745329251994329D;
            targetLng *= 0.01745329251994329D;
            targetLat *= 0.01745329251994329D;
            double sinLocLng = Math.sin(locLng);
            double sinLocLat = Math.sin(locLat);
            double cosLocLng = Math.cos(locLng);
            double cosLocLat = Math.cos(locLat);
            double sinTargetLng = Math.sin(targetLng);
            double sinTargetLat = Math.sin(targetLat);
            double cosTargetLng = Math.cos(targetLng);
            double cosTargetLat = Math.cos(targetLat);
            double[] locValues = new double[3];
            double[] targetValues = new double[3];
            locValues[0] = cosLocLat * cosLocLng;
            locValues[1] = cosLocLat * sinLocLng;
            locValues[2] = sinLocLat;
            targetValues[0] = cosTargetLat * cosTargetLng;
            targetValues[1] = cosTargetLat * sinTargetLng;
            targetValues[2] = sinTargetLat;
            double result = Math.sqrt((locValues[0] - targetValues[0]) * (locValues[0] - targetValues[0]) + (locValues[1] - targetValues[1]) * (locValues[1] - targetValues[1]) + (locValues[2] - targetValues[2]) * (locValues[2] - targetValues[2]));
            return (float) (Math.asin(result / 2.0D) * 1.27420015798544E7D);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0.0F;
        }
    }
}
