package com.example.serialtest;


import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by CVTE on 2015/12/25.
 */
public class SerialDataUtil {
    private static final String TAG = "SerialDataUtil";
    public static final String DATA = "SerialData";
    /*control code*/
    public static final byte E_SEND_ERROR_CONTROL = 0x00;
    public static final byte E_SEND_DATACODE_CONTROL = 0x01;

    public static final byte E_RECE_UNKONW_CONTROL = (byte) 0xFF;

    /*heard code*/
    private static final byte E_HEAD_CODE_1 = (byte) 0xAA;
    private static final byte E_HEAD_CODE_2 = (byte) 0xBB;
    private static final byte E_HEAD_CODE_3 = (byte) 0xCC;


    /*key code*/
    public static byte SWITCH_CN8_HDMI_USB = 0x00;
    public static byte SWITCH_MS828_USB = 0x01;
    public static byte SWITCH_CN6_PC_USB = 0x02;
    public static byte SWITCH_HUB1_USB = 0x03;
    public static byte SWITCH_J9_DP_USB = 0x04;
    public static byte SWITCH_J7_HDMI_USB = 0x08;
    public static byte SWITCH_J5_VGA_USB = 0x0c;


    /*接收数据使用
* */
    private static byte[] receBuff;
    private static int buffCurrentSize;
    private static byte currentReceControl;
    public static Queue<ReceData> byteArray;

    public static void initSerialDataUtil() {
        buffCurrentSize = 0;
        currentReceControl = E_RECE_UNKONW_CONTROL;
        receBuff = new byte[1024];
        byteArray = new ArrayBlockingQueue<ReceData>(10);
    }

    public static class ReceData {
        private byte control = E_RECE_UNKONW_CONTROL;
        private byte[] data = null;

        public void setControlDate(byte control) {
            this.control = control;
        }

        public void setReceData(byte[] bytes) {
            data = new byte[bytes.length];
            System.arraycopy(bytes, 0, data, 0, bytes.length);
        }

        public byte getControlData() {
            return this.control;
        }

        public byte[] getReceData() {
            return data;
        }
    }

    public static int decodeReceBuff(byte[] buff, int size) {
        int dsize = 0;
        if (addReceBuff(buff, size) == true) {
            ReceData data = decodeData();
            while (data != null) {
                byteArray.offer(data);
                dsize++;
                data = decodeData();
            }
            return dsize;
        } else {
            return 0;
        }
    }

    private static ReceData decodeData() {
        ReceData data = null;
        boolean isDecodeSuccess = false;
        byte length = 0;
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < buffCurrentSize; i++) {
            if (receBuff[i] == E_HEAD_CODE_1) {
                startIndex = i;
                if ((buffCurrentSize > i + 5) && (buffCurrentSize >= receBuff[startIndex + 3] + i) && (receBuff[startIndex + 3] <= 64) && (receBuff[startIndex + 3] >= 0)) {
                    if (receBuff[startIndex + 1] == E_HEAD_CODE_2 && receBuff[startIndex + 2] == E_HEAD_CODE_3) {
                        length = receBuff[startIndex + 3];
                        byte datalength = (byte) (length - 6);
                        byte[] databytes = new byte[datalength];
                        System.arraycopy(receBuff, startIndex + 5, databytes, 0, datalength);
                        if (receBuff[startIndex + length - 1] == getCheckSum(receBuff[startIndex + 4], databytes, (byte) datalength)) {
                            isDecodeSuccess = true;
                            endIndex = startIndex + length;
                        }
                    }
                }
            }
            if (isDecodeSuccess) {
                byte[] recebytes = new byte[length - 6];
                data = new ReceData();
                System.arraycopy(receBuff, startIndex + 5, recebytes, 0, length - 6);
                data.setControlDate(receBuff[startIndex + 4]);
                data.setReceData(recebytes);
                buffCurrentSize -= endIndex;
                byte[] tembytes = new byte[buffCurrentSize];
                System.arraycopy(receBuff, endIndex, tembytes, 0, buffCurrentSize);
                System.arraycopy(tembytes, 0, receBuff, 0, buffCurrentSize);
                return data;
            }
        }
        return data;
    }

    public static boolean addReceBuff(byte[] buff, int size) {
        if (buffCurrentSize + size < 1024) {
            System.arraycopy(buff, 0, receBuff, buffCurrentSize, size);
            buffCurrentSize += size;
            return true;
        } else {
            buffCurrentSize = 0;
            return false;
        }
    }

    public static ReceData getCurrentReceData() {
        ReceData data = byteArray.poll();
        return data;
    }

    public static byte[] getSendDataCodeBuf(byte data) {
        byte[] datas = new byte[1];
        datas[0] = data;
        return getSendConformityData(E_SEND_DATACODE_CONTROL, datas, (byte) 1);
    }

    public static byte[] getSendConformityData(byte control, byte[] buf, byte size) {
        byte[] bytearray;
        byte length = (byte) (size + 6);
        if (length > 64 || length < 0) {
            return null;
        }
        bytearray = new byte[length];
        bytearray[0] = E_HEAD_CODE_1;
        bytearray[1] = E_HEAD_CODE_2;
        bytearray[2] = E_HEAD_CODE_3;
        bytearray[3] = (byte) length;
        bytearray[4] = control;
        for (int i = 0; i < size; i++) {
            bytearray[5 + i] = buf[i];
        }
        bytearray[length - 1] = getCheckSum(control, buf, size);
        return bytearray;
    }

    /*bufsize 是指有效数据的大小，注意
    * */
    public static byte getCheckSum(byte control, byte[] buf, byte bufsize) {
        byte checksumbyte = 0;
        byte length = (byte) (bufsize + 6);//Head code :3  length;1 control:1 checksum:1
        if (length > 64 || length < 0) {
            return 0x00;
        }
        checksumbyte += (byte) 0xAA;
        checksumbyte += (byte) 0xBB;
        checksumbyte += (byte) 0xCC;
        checksumbyte += length;
        checksumbyte += control;
        for (int i = 0; i < bufsize; i++) {
            checksumbyte += buf[i];
        }

        return checksumbyte;
    }

}
