package com.example.floatdemoforuart;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cvte.tv.api.TvApiApplication;
import com.cvte.tv.api.TvServiceConnectListener;
import com.cvte.tv.api.aidl.EntityInputSource;
import com.cvte.tv.api.aidl.EnumInputStatus;
import com.cvte.tv.api.aidl.ITVApiSystemInputSourceAidl;
import com.cvte.tv.api.aidl.ITvApiManager;
import com.example.serialtest.SerialDataUtil;
import com.mstar.android.tvapi.common.AudioManager;
import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;

import java.util.ArrayList;
import java.util.List;

public class FloatWindowBigView extends LinearLayout {
	public static final int HDMI_SWITCH_HDMI1 = 0;
	public static final int HDMI_SWITCH_HDMI2 = 3;
	public static final int HDMI_SWITCH_DP = 1;
	/**
	 * 记录大悬浮窗的宽度
	 */
	public static int viewWidth;
	private ITVApiSystemInputSourceAidl mSourceApi = null;
	private List<EntityInputSource> mSourceList = null;
	private List<Integer> idList = null;
	private AudioManager audioManager = null;
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
		idList = new ArrayList<>();
		audioManager = TvManager.getInstance().getAudioManager();
		TvApiApplication.getTvApi(new TvServiceConnectListener() {
			@Override
			public void OnConnected(ITvApiManager iTvApiManager) {
				try{
					mSourceApi = iTvApiManager.getTVApiSystemInputSource();
					mSourceList = mSourceApi.eventSystemInputSourceGetList();
					for(int i = 0;i < mSourceList.size();i++)
					{
						if (mSourceList.get(i).status == EnumInputStatus.INPUT_STATUS_HIDE)
							continue;
						idList.add(new Integer(mSourceList.get(i).id));
					}

				}catch (RemoteException e){
					e.printStackTrace();
				}

			}
		});
		Button cn8HdmiUsb = (Button) findViewById(R.id.CN8_HDMI_USB);
		Button ms828Usb = (Button) findViewById(R.id.MS828_USB);
		Button cn6PcUsb = (Button) findViewById(R.id.CN6_PC_USB);
		Button hub1Usb = (Button) findViewById(R.id.HUB1_USB);
		Button j9DpUsb = (Button) findViewById(R.id.J9_DP_USB);
		Button j7HdmiUsb = (Button) findViewById(R.id.J7_HDMI_USB);
		Button j5VgaUsb = (Button) findViewById(R.id.J5_VGA_USB);

		//source
		Button sourceTv = (Button)findViewById(R.id.SOURCE_TV);
		Button sourceVga = (Button)findViewById(R.id.SOURCE_VGA);
		Button sourceHdmi1Hdmi1 = (Button)findViewById(R.id.SOURCE_HDMI1_HDMI1);
		Button sourceHdmi1Hdmi2 = (Button)findViewById(R.id.SOURCE_HDMI1_HDMI2);
		Button sourceHdmi1Dp = (Button)findViewById(R.id.SOURCE_HDMI1_DP);
		Button sourceHdmi2 = (Button)findViewById(R.id.SOURCE_HDMI2);
		Button sourceHdmi3 = (Button)findViewById(R.id.SOURCE_HDMI3);

		//hdmi port

		Button home = (Button)findViewById(R.id.home);
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

		//source
		sourceTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(context,"TV",Toast.LENGTH_SHORT).show();
				try {
					MyWindowManager.StartTvActivity(context);
					mSourceApi.eventSystemInputSourceSetInputSource(idList.get(0));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});

		sourceVga.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(context,"VGA",Toast.LENGTH_SHORT).show();
				try {
					MyWindowManager.StartTvActivity(context);
					mSourceApi.eventSystemInputSourceSetInputSource(idList.get(1));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});

		sourceHdmi1Hdmi1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					MyWindowManager.StartTvActivity(context);
					mSourceApi.eventSystemInputSourceSetInputSource(idList.get(2));
					audioManager.setHdmiSwitchPort(HDMI_SWITCH_HDMI1);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (TvCommonException e) {
					e.printStackTrace();
				}
			}
		});
		sourceHdmi1Hdmi2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					MyWindowManager.StartTvActivity(context);
					mSourceApi.eventSystemInputSourceSetInputSource(idList.get(2));
					audioManager.setHdmiSwitchPort(HDMI_SWITCH_HDMI2);
				} catch (TvCommonException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});

		sourceHdmi1Dp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					MyWindowManager.StartTvActivity(context);
					mSourceApi.eventSystemInputSourceSetInputSource(idList.get(2));
					audioManager.setHdmiSwitchPort(HDMI_SWITCH_DP);
				} catch (TvCommonException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});

		sourceHdmi2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					MyWindowManager.StartTvActivity(context);
					mSourceApi.eventSystemInputSourceSetInputSource(idList.get(3));
				} catch (RemoteException e) {
						e.printStackTrace();
				}
			}
		});

		sourceHdmi3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					MyWindowManager.StartTvActivity(context);
					mSourceApi.eventSystemInputSourceSetInputSource(idList.get(4));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});

		home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				MyWindowManager.StartHomeActivity(context);
			}
		});

	}
}
