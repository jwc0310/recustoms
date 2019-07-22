package com.andy.materialtest.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Administrator on 2017/6/21.
 */
public class GLUtil {

    /**
     * @param vertexs float 数组
     * @return 获取浮点形缓冲数据
     */
    public static FloatBuffer getFloatBuffer(float[] vertexs) {
        FloatBuffer buffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(vertexs.length * 4);
        qbb.order(ByteOrder.nativeOrder());
        buffer = qbb.asFloatBuffer();
        //写入数组
        buffer.put(vertexs);
        //设置默认的读取位置
        buffer.position(0);
        return buffer;
    }

    public static IntBuffer getIntBuffer(int[] vertexs) {
        IntBuffer buffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(vertexs.length * 4);
        qbb.order(ByteOrder.nativeOrder());
        buffer = qbb.asIntBuffer();
        buffer.put(vertexs);
        buffer.position(0);
        return buffer;
    }

    public static ByteBuffer getByteBuffer(byte[] vertexs) {
        ByteBuffer buffer = null;
        buffer = ByteBuffer.allocateDirect(vertexs.length);
        buffer.put(vertexs);
        buffer.position(0);
        return buffer;
    }

    public static ShortBuffer getShortBuffer(short[] vertexs) {
        ShortBuffer buffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(vertexs.length * 2);
        qbb.order(ByteOrder.nativeOrder());
        buffer = qbb.asShortBuffer();
        buffer.put(vertexs);
        buffer.position(0);

        return buffer;
    }
}
