package com.nik.smartnote.vk.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsDataHelper
{
	public static String[] parseRedirectUrl(String url)
		throws Exception
	{
		String access_token = extractPattern(url, "access_token=(.*?)&");
		String user_id = extractPattern(url, "user_id=(\\d*)");
		if (user_id == null || user_id.length() == 0 || access_token == null || access_token.length() == 0) {
			throw new Exception("Failed to parse redirect url " + url);
		}
		return new String[] { access_token, user_id };
	}

	public static String getAuthKey(String html){
		String auth_key =extractPattern(html, "auth_key\":\"(.*?)\"");
		if (auth_key != null) {
			return auth_key;
		}
		return null;
	}

	private static String extractPattern(String string, String pattern)
	{
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(string);

		if (!m.find()) {
			return null;
		}
		return m.toMatchResult().group(1);
	}


	public static String getUserId(String url){

		String auth_key =extractPattern(url, "vk.com/(.*)");
		if (auth_key != null) {
			return auth_key;
		}

		return null;
	}
}
