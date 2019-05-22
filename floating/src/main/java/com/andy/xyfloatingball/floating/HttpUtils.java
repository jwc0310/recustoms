package com.andy.xyfloatingball.floating;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * network api
 * Created by Andy on 2016/12/2.
 */
public class HttpUtils {
    private static final String secret = "4ed00ec85039f88eeee413eee185d112";
    private static final String weather_url = "http://192.168.1.118/dashboard/xyweather/weather.php";
    //    private static String base_url = "http://www.andy.com.cn/";
    private static String base_url = "http://192.168.1.88/new_market/market-server/service.php?";


    public static void getWeatherData(final RequestCallback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String city = getCityName();
                String timestamp = System.currentTimeMillis() / 1000 +"";
                String sign = Utils.get32MD5Str("timestamp="+timestamp+"&"+secret);
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("city", city));
                params.add(new BasicNameValuePair("timestamp", timestamp));
                params.add(new BasicNameValuePair("sign", sign));

                String result = Http.post(weather_url, params);
                if (result.equals("") || result.length() == 0){
                    callback.onFailure(0x0, "error");
                    return;
                }
                callback.onSuccess(result);
            }
        }).start();
    }

    /**
     * 获得城市名称
     */
    public static synchronized String getCityName(){
        if (!Utils.city.equals("") && Utils.city.length() != 0){
            return Utils.city;
        }
        String latitude = Utils.getandyLaLo("andyd.gps.latitude");
        String longitude = Utils.getandyLaLo("andyd.gps.longitude");
        String url = "http://api.map.baidu.com/geocoder/v2/?ak=RzuH0o9ytYIjnwX5FKA3BqUB&location="+latitude+","+longitude+"&output=json&pois=0";
        String result = Http.get(url);
        try {
            JSONObject resultObj = new JSONObject(result);
            if (resultObj.getString("status").equals("0")){
                JSONObject res = resultObj.getJSONObject("result");
                String city = res.getJSONObject("addressComponent").getString("city");
                String province = res.getJSONObject("addressComponent").getString("province");
                if (city == null || city.equals("") || city.length() == 0){
                    Utils.city =  province;
                }
                Utils.city = city;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return Utils.city;
    }
    public static void asyncGetCityName(final RequestCallback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String city = getCityName();
                if (city == null || city.equals("") || city.length() == 0){
                    callback.onFailure(0x1, "error");
                    return;
                }
                callback.onSuccess(city);
            }
        }).start();
    }


    /**
     * 请求网络数据接口
     */
    public static void getHotGames(final int position, final RequestCallback callback){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String hot_games = base_url+"action=gethotestrecommend&position="+position+"&channel="+ Utils.getandyChannel();
                String result = Http.get(hot_games);
//                AppBean appBean = new Gson().fromJson(result, AppBean.class);
//                if (appBean != null && appBean.getApps() != null && appBean.getApps().size() != 0){
//                    callback.onSuccess(appBean.getApps());
//                    return;
//                }
                callback.onFailure(0, "error");
            }
        }).start();

    }
}
