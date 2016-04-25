package com.nik.smartnote.vk.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.nik.smartnote.vk.R;
import com.nik.smartnote.vk.api.APIFunctions;
import com.nik.smartnote.vk.util.JsonHelper;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class PostService extends Service
{

	String accessToken;



	NotificationManager nm;
	final String LOG_TAG = "My log";
String idPost;
String idPage;
String comment;
	Context context;

	int lastID = 0;

	@Override
	public void onDestroy()
	{

		Log.d("MyLog", "Уничтожилось");
		Toast.makeText(this, "Сервис дохлый!", Toast.LENGTH_LONG).show();
		super.onDestroy();
	}

	@Override
	public void onCreate()
	{
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		System.out.println("Servise start");
//todo тут функи

//--------------------------------------------------
		if (intent != null) {
			accessToken = intent.getStringExtra("access_token");
           comment = intent.getStringExtra("comment");
			idPost = intent.getStringExtra("id_post");
			idPage = intent.getStringExtra("id_page");
			System.out.println("Nulllllll^^^^:::" + idPost + idPage);
		}
		System.out.println(comment);
MyRun myRun = new MyRun();


		return START_REDELIVER_INTENT;
	}




	@Override
	public IBinder onBind(Intent intent)
	{

		return null;
	}




	class MyRun implements Runnable
	{
Thread thread;

		 MyRun(){
			// Создаём новый второй поток
			thread = new Thread(this, "Поток для примера");
			System.out.println("Создан второй поток " + thread);
			thread.start(); // Запускаем поток
		}

		@Override
		public void run()
		{

			JSONObject jsonObject;

			Log.d("MyLog", "Итерация!");


			try {

				String result;
				URL urlComment = null;
				URL urlPost = null;

				urlComment = new URL(
					"https://api.vk.com/method/wall.addComment?owner_id=-"+ idPage +"&post_id="+ idPost +"&text="
						+ URLEncoder.encode(comment, "UTF-8") + "&access_token="
						+ accessToken + "&v=5.30"
				);

				urlPost = new URL(
						"https://api.vk.com/method/wall.post?owner_id=-"+ idPage +"&message="
								+ URLEncoder.encode(comment, "UTF-8") + "&access_token="
								+ accessToken + "&v=5.45"
				);

				HttpURLConnection urlConnection = null;

				urlConnection = (HttpURLConnection) urlPost.openConnection();

				InputStream in = new BufferedInputStream(urlConnection.getInputStream());

				StringBuffer buffer = new StringBuffer();
				int ch;

				while ((ch = in.read()) > -1) {
					buffer.append((char) ch);
				}

	jsonObject = new JSONObject(buffer.toString());


				if(lastID != 0) {
					APIFunctions.Wall.delete(Integer.parseInt(idPage), lastID);
				}
				if(jsonObject.has("response")) {
				JSONObject respounsJsonObject = jsonObject.getJSONObject("response");

					lastID = respounsJsonObject.getInt("post_id");
				}
				result = "Экспиремент";
				Log.d("My logs", result);
				sendNotif(result, 1);

				urlConnection.disconnect();

			} catch (Exception e) {
				System.out.println("Исключение запроса" + e);

			}
			Log.d("MyLog", "Итерация выполнена!");
		}

	}




	void sendNotif(String name, int id)
	{

		Notification notif = new Notification(android.R.drawable.presence_online, name, System.currentTimeMillis());


		Intent intent = new Intent();
		intent.putExtra("File", "somefile");
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);


		notif.setLatestEventInfo(this, name, name, pIntent);


		notif.flags |= Notification.FLAG_AUTO_CANCEL;
		Uri uri = Uri.parse("android.resource://com.nik.smartnote.vk/" + R.raw.notification);
		notif.sound = uri;
		// отправляем
		nm.notify(id, notif);
	}


}
