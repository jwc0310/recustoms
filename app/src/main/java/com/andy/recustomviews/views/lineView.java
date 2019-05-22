package com.andy.recustomviews.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/6/13.
 */
public class lineView extends View {

    //坐标轴原点
    private final int xPoint = 60;
    private final int yPoint = 260;

    //刻度长度
    private final int xScale = 8;
    private final int yScale = 40;

    //坐标轴长度
    private final int xLength = 380;
    private final int yLength = 240;

    //横坐标最多可绘制多少个点
    private final int MaxDataSize = xLength / xScale;
    private List<Integer> data = new ArrayList<>();
    private String[] yLabel = new String[yLength / yScale];


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            lineView.this.invalidate();
        }
    };

    public lineView(Context context) {
        super(context);
    }

    public lineView(Context context, AttributeSet attrs) {
        super(context, attrs);

        for (int i = 0; i< yLabel.length; i++){
            yLabel[i] = (i+1)+"M/s";
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (data.size() > MaxDataSize){
                        data.remove(0);
                    }
                    data.add(new Random().nextInt(5)+1);
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

        //绘制Y轴， 以及左右两边箭头箭头, 刻度与文字
        canvas.drawLine(xPoint, yPoint-yLength, xPoint, yPoint, paint);
        canvas.drawLine(xPoint, yPoint-yLength, xPoint-3, yPoint-yLength+6, paint);
        canvas.drawLine(xPoint, yPoint-yLength, xPoint+3, yPoint-yLength+6, paint);

        for (int i = 0; i * yScale < yLength; i++){
            canvas.drawLine(xPoint, yPoint - i * yScale, xPoint+5, yPoint-i * yScale, paint);
            canvas.drawText(yLabel[i], xPoint-50, yPoint-i * yScale, paint);
        }

        //绘制X轴
        canvas.drawLine(xPoint, yPoint, xPoint+xLength, yPoint, paint);

        //实现填充
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);

        //如果集合中有数据
//        if (data.size() > 1){
//            for (int i = 1; i< data.size(); i++){
//                canvas.drawLine(xPoint+(i-1)*xScale, yPoint-data.get(i-1)*yScale,
//                        xPoint+i*xScale, yPoint-data.get(i)*yScale, paint);
//            }
//        }
        //填充
        if (data.size() > 1){
            Path path = new Path();
            path.moveTo(xPoint, yPoint);
            for (int i = 0; i < data.size(); i++){
                path.lineTo(xPoint+i*xScale, yPoint-data.get(i)*yScale);
            }
            path.lineTo(xPoint+(data.size()-1)*xScale, yPoint);
            canvas.drawPath(path, paint);
        }

    }
}
