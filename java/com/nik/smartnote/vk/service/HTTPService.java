package com.nik.smartnote.vk.service;

import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by ������� on 15.07.2015.
 */
public class HTTPService extends Service
{
 String[] listIdFriends;

	@Override
	public void onCreate()
	{

		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		return super.onStartCommand(intent, flags, startId);




	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
}
