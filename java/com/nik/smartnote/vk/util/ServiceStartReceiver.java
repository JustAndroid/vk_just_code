package com.nik.smartnote.vk.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nik.smartnote.vk.service.PostService;

public class ServiceStartReceiver extends BroadcastReceiver

{
	String accessToken;
	String comment;
	String id_post;
	String id_page;

	public void onReceive(Context context, Intent intent) {
		System.out.println("Broadcast start");
		if (intent != null) {
			accessToken = intent.getStringExtra("access_token");
			comment = intent.getStringExtra("comment");
			id_post = intent.getStringExtra("id_post");
			id_page = intent.getStringExtra("id_page");
		}
		Intent intentService = new Intent(context, PostService.class);
		intentService.putExtra("access_token", accessToken);
		intentService.putExtra("comment", comment);
		intentService.putExtra("id_post", id_post);
		intentService.putExtra("id_page", id_page);

context.startService(intentService);
		System.out.println("Servise starting...");
	}

}
