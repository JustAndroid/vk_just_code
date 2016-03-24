package com.nik.smartnote.vk.api;


import android.app.Activity;
import android.content.Context;

import com.nik.smartnote.vk.Model.User;
import com.nik.smartnote.vk.util.HTTPHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Николай on 03.05.2015.
 */
public class APIFunctions
{

	final static String START_URL = "https://api.vk.com/method/";

	public class Scope
	{
		//	//права
	public 	final static String NOTIFY = "notify";
		//	//	Пользователь разрешил отправлять ему уведомления (для flash/ifrаme-приложений).
		public	final static String FRIENDS = "friends";
		//	//	Доступ к друзьям.
//	final static String	photos
//	//	Доступ к фотографиям.
//	final static String	audio
//	//	Доступ к аудиозаписям.
//	final static String	video
//	//	Доступ к видеозаписям.
//	final static String	docs
//	//	Доступ к документам.
//	final static String	notes
//	//	Доступ к заметкам пользователя.
//	final static String	pages
//	//	Доступ к wiki-страницам.
//	// 	Добавление ссылки на приложение в меню слева.
//	final static String		status
//	//	Доступ к статусу пользователя.
//	final static String	offers
//	//	Доступ к предложениям (устаревшие методы).
//	final static String	questions
//	//		Доступ к вопросам (устаревшие методы).
		public	final static String WALL = "wall";
		//	//		Доступ к обычным и расширенным методам работы со стеной.
//	//Внимание, данное право доступа недоступно для сайтов (игнорируется при попытке авторизации).
		public	final static String GROUPS = "groups";
//	//		Доступ к группам пользователя.
//	final static String	messages
//	//	(для Standalone-приложений) Доступ к расширенным методам работы с сообщениями.
//	final static String		email
//	//		Доступ к email пользователя.
//	final static String	notifications
//	// 	Доступ к оповещениям об ответах пользователю.
//	final static String	stats
//	//	 	Доступ к статистике групп и приложений пользователя, администратором которых он является.
//	final static String	ads
//	//	Доступ к расширенным методам работы с рекламным API.
public 	final static String	OFFLINE = "offline";
//	//	Доступ к API в любое время со стороннего сервера (при использовании этой опции параметр expires_in, возвращаемый вместе с access_token, содержит 0 — токен бессрочный).
//	final static String	nohttps 	//Возможность осуществлять запросы к API без HTTPS.

	}

	public class Like
	{

		//типы объекта
		final static String POST = "post"; //запись на стене пользователя или группы
		final static String COMMENT = "comment"; // комментарий к записи на стене
		final static String PHOTO = "photo"; // фотография
		final static String AUDIO = "audio"; // аудиозапись
		final static String VIDEO = "video"; // видеозапись
		final static String NOTE = "note"; // заметка
		final static String PHOTO_COMMENT = "photo_comment"; // комментарий к фотографии;
		final static String VIDEO_COMMENT = "video_comment"; //комментарий к видеозаписи;
		final static String TOPIC_COMMENT = "topic_comment"; // комментарий в обсуждении;

		//методы
		final static String IS_LIKED = "likes.isLiked"; // проверка есть ли лайк на данной записи
		final static String ADD = "likes.add"; // добавляет
		final static String DELETE = "likes.delete"; // удаляет


		public String isLiked(String user_id, String type, String owner_id, String item_id)
		{ // проверка есть ли лайк на данной записи

			return "todo 1, or 0";
		}

		public String add(String type, String owner_id, String item_id, String access_key)
		{ //добавляет лайк

			return "";
		}


	}

	public static class Wall
	{
		public	final static String WALL = "wall.";
		public static  String delete(int onwerId, int postId , Context context){


			return HTTPHelper.getInstance().requestGet(START_URL + WALL + "delete?owner_id=-"+Integer.toString(onwerId)+"&post_id="+Integer.toString(postId)+"&access_token="+ User.getInstance().getAccessToken(context), null);
		}


		public String addComments(String owner_id, String post_id, String text)
		{


			return "comment_id";
		}
	}
    public static class Friends{

        //методы

		final static   String FRENDS = "friends.";

       // public String getRequests(String count, String extended, String need_mutual, String out){

		public static String getFriends(int count, Activity activity){
			String urlRequest = START_URL + FRENDS + "get?count="+count+"&access_token="+ User.getInstance().getAccessToken(activity);

return HTTPHelper.getInstance().requestGet(urlRequest, null);
		}

        public static String getRequests(int count,int out, Context context){

String UrlRequest = START_URL + FRENDS + "getRequests?count="+count+"&extended=0&need_mutual=0&out="+out+"&access_token="+ User.getInstance().getAccessToken(context);

        return  HTTPHelper.getInstance().requestGet(UrlRequest, null);
        }

	    public static String delete(int id, Context context){
String UrlRequest = START_URL + FRENDS + "delete?user_id=" + id + "&access_token=" + User.getInstance().getAccessToken(context);

		    return  HTTPHelper.getInstance().requestGet(UrlRequest, null);
	    }
		public static String add(int id, Context context){
String UrlRequest = START_URL + FRENDS + "add?user_id=" + id + "&access_token=" + User.getInstance().getAccessToken(context);

		    return  HTTPHelper.getInstance().requestGet(UrlRequest, null);
	    }


    }
	public static class Users{


		public static String  usersGet(String id_name){
			return HTTPHelper.getInstance().requestGet(START_URL + "users.get?user_ids=" + id_name , null);

		}
		public static ArrayList<Integer> getFollowers( Activity activity){

			JSONObject jsonObject;
			JSONObject JSONResponse;
			int lastId = 0;
			ArrayList<Integer> userList = new ArrayList<>();

			try {

				jsonObject = new JSONObject(HTTPHelper.getInstance().requestGet(START_URL
						+ "users.getFollowers?user_id="
						+ User.getInstance().getUser_id(activity) + "&offset=" + Integer.toString(lastId) + "&count=1000", null));


				JSONResponse = jsonObject.getJSONObject("response");
				JSONArray allFollovers = JSONResponse.getJSONArray("items");
				int userCount = JSONResponse.getInt("count");
				for(int i = 0; i < allFollovers.length(); i++){

					userList.add(allFollovers.getInt(i));
				}
				userList.size();
				System.out.println(userCount);

				while(userList.size() < userCount){
					lastId += 1000;


					jsonObject = new JSONObject(HTTPHelper.getInstance().requestGet(START_URL
							+ "users.getFollowers?user_id="
							+ User.getInstance().getUser_id(activity) + "&offset=" + Integer.toString(lastId) + "&count=1000", null));
					JSONResponse = jsonObject.getJSONObject("response");
					 allFollovers = JSONResponse.getJSONArray("items");

					for(int i = 0; i < allFollovers.length(); i++){

						userList.add(allFollovers.getInt(i));
					}
userList.size();
				}


			}catch(JSONException e){

			}

			return userList;
		}
	}

}
