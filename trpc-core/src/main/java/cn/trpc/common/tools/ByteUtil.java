package cn.trpc.common.tools;

import java.nio.ByteBuffer;

/**
 * @Program: temp
 * @ClassName: ByteUtil
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-19 19:59
 * @Description: 字节处理工具类
 * @Version: V1.0
 */
public class ByteUtil {

    /** short 2字节 int 4 字节 long 8字节 */


    public static byte[] short2bytes(short v) {
        byte[] b = new byte[4];
        b[1] = (byte) v;
        b[0] = (byte) (v >>> 8);
        return b;
    }

    public static byte[] int2bytes(int v) {
        byte[] b = new byte[4];
        b[3] = (byte) v;
        b[2] = (byte) (v >>> 8);
        b[1] = (byte) (v >>> 16);
        b[0] = (byte) (v >>> 24);
        return b;
    }

    public static byte[] long2bytes(long v) {
        byte[] b = new byte[8];
        b[7] = (byte) v;
        b[6] = (byte) (v >>> 8);
        b[5] = (byte) (v >>> 16);
        b[4] = (byte) (v >>> 24);
        b[3] = (byte) (v >>> 32);
        b[2] = (byte) (v >>> 40);
        b[1] = (byte) (v >>> 48);
        b[0] = (byte) (v >>> 56);
        return b;
    }


    /** 字节数组转字符串 */
    public static String byres2HexString(byte[] bs) {
        if (bs == null || bs.length == 0) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        String tmp = null;
        for (byte b : bs) {
            tmp = Integer.toHexString(Byte.toUnsignedInt(b));
            if (tmp.length() < 2) {
                sb.append(0);
            }
            sb.append(tmp);
        }
        return sb.toString();
    }

    public static int bytes2int(byte[] bytes) {
        if (bytes.length < 4) {
            return -1;
        }

        int iRst = (bytes[0] << 24) & 0xFF;
        iRst |= (bytes[1] << 16) & 0xFF;
        iRst |= (bytes[2] << 8) & 0xFF;
        iRst |= bytes[3] & 0xFF;

        return iRst;
    }

    public static long bytes2long(byte[] b) {
        ByteBuffer buffer = ByteBuffer.wrap(b);
        return buffer.getLong();
    }
}
