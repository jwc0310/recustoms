package com.andy.recustomviews.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class ShakeUtils implements SensorEventListener {

    private SensorManager sensorManager;
    private OnShakeListener shakeListener;

    private static final int SENSOR_VALUE = 14;

    public ShakeUtils(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    float x,y,z;

    @Override
    public void onSensorChanged(SensorEvent event) {

        int sensorType = event.sensor.getType();
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            //这里可以调节摇一摇的灵敏度
            Log.e("ShakeUtils", "sensor value == " + " " + values[0] + " " + values[1] + " " + values[2]);
            if ((Math.abs(values[0]) > SENSOR_VALUE || Math.abs(values[1]) > SENSOR_VALUE || Math.abs(values[2]) > SENSOR_VALUE)) {
                System.out.println("sensor value == " + " " + values[0] + " " + values[1] + " " + values[2]);
                if (null != shakeListener) {
                    shakeListener.onShake();
                }
            }

            float x1,y1,z1;
            float f1, f2, f3;
            f1 = values[0];
            f2 = values[1];
            f3 = values[2];

            x1 = f1 - x;
            y1 = f2 - y;
            z1 = f3 - z;

            x = f1;
            y = f2;
            z = f3;

            float res = (float)Math.sqrt(x1*x1 + y1*y1 + z1*z1) / 12 *10000;
            Log.e("ShakeUtils", "sensor value == " + " " + values[0] + " " + values[1] + " " + values[2] + "     res = " + res);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onResume(){
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause(){
        sensorManager.unregisterListener(this);
    }

    public void setShakeListener(OnShakeListener shakeListener) {
        this.shakeListener = shakeListener;
    }


    public interface OnShakeListener {
        public void onShake();
    }
}
