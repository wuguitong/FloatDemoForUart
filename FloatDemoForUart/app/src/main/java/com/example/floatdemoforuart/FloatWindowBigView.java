package com.example.floatdemoforuart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.serialtest.SerialDataUtil;

public class FloatWindowBigView extends LinearLayout {

	/**
	 * 记录大悬浮窗的宽度
	 */
	public static int viewWidth;

	/**
	 * 记录大悬浮窗的高度
	 */
	public static int viewHeight;

	public FloatWindowBigView(final Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.float_window_big, this);
		View view = findViewById(R.id.big_window_layout);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;
		Button cn8HdmiUsb = (Button) findViewById(R.id.CN8_HDMI_USB);
		Button ms828Usb = (Button) findViewById(R.id.MS828_USB);
		Button cn6PcUsb = (Button) findViewById(R.id.CN6_PC_USB);
		Button hub1Usb = (Button) findViewById(R.id.HUB1_USB);
		Button j9DpUsb = (Button) findViewById(R.id.J9_DP_USB);
		Button j7HdmiUsb = (Button) findViewById(R.id.J7_HDMI_USB);
		Button j5VgaUsb = (Button) findViewById(R.id.J5_VGA_USB);
		Button back = (Button) findViewById(R.id.back);
		cn8HdmiUsb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(context,"CN8_HDMI_USB",Toast.LENGTH_SHORT).show();
				FloatWindowService.mSerialPortUtil.sendBuffer(SerialDataUtil.getSendDataCodeBuf(SerialDataUtil.SWITCH_CN8_HDMI_USB));
			}
		});
		ms828Usb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(context,"MS828_USB",Toast.LENGTH_SHORT).show();
				FloatWindowService.mSerialPortUtil.sendBuffer(SerialDataUtil.getSendDataCodeBuf(SerialDataUtil.SWITCH_MS828_USB));
			}
		});
		cn6PcUsb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(context,"CN6_PC_USB",Toast.LENGTH_SHORT).show();
				FloatWindowService.mSerialPortUtil.sendBuffer(SerialDataUtil.getSendDataCodeBuf(SerialDataUtil.SWITCH_CN6_PC_USB));
			}
		});
		hub1Usb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(context,"HUB1_USB",Toast.LENGTH_SHORT).show();
				FloatWindowService.mSerialPortUtil.sendBuffer(SerialDataUtil.getSendDataCodeBuf(SerialDataUtil.SWITCH_HUB1_USB));
			}
		});
		j9DpUsb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(context,"J9_DP_USB",Toast.LENGTH_SHORT).show();
				FloatWindowService.mSerialPortUtil.sendBuffer(SerialDataUtil.getSendDataCodeBuf(SerialDataUtil.SWITCH_J9_DP_USB));
			}
		});
		j7HdmiUsb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(context,"J7_HDMI_USB",Toast.LENGTH_SHORT).show();
				FloatWindowService.mSerialPortUtil.sendBuffer(SerialDataUtil.getSendDataCodeBuf(SerialDataUtil.SWITCH_J7_HDMI_USB));
			}
		});
		j5VgaUsb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(context,"J5_VGA_USB",Toast.LENGTH_SHORT).show();
				FloatWindowService.mSerialPortUtil.sendBuffer(SerialDataUtil.getSendDataCodeBuf(SerialDataUtil.SWITCH_J5_VGA_USB));
			}
		});


		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击返回的时候，移除大悬浮窗，创建小悬浮窗
				MyWindowManager.removeBigWindow(context);
				MyWindowManager.createSmallWindow(context);
			}
		});
	}
}
