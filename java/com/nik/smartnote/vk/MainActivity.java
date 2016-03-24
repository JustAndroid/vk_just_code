package com.nik.smartnote.vk;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nik.smartnote.vk.Model.User;
import com.nik.smartnote.vk.api.APIFunctions;
import com.nik.smartnote.vk.util.HTTPHelper;
import com.nik.smartnote.vk.util.ParsDataHelper;


public class MainActivity extends Activity {

    WebView webView;


    //todo-------------------------------------------
    public static String cookies;

    ProgressDialog progressDialog;
    static final String AUTHORIZE_URL = "https://oauth.vk.com/authorize?";
    static final String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    static final String CLIENT_ID = "4738015";
    static final String API_URL = "https://api.vk.com/method/";
    static final String APLICATION_URL = "http://vk.com/app1979194";



    boolean loadFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            actionBar = getActionBar();

            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


     //   startActivity(new Intent(MainActivity.this, MainMenu.class));


        webView = (WebView) findViewById(R.id.webView);

        loadFinished = true;





        webView.loadUrl(
                AUTHORIZE_URL
                        + "client_id=" + CLIENT_ID + "&"
                        + "redirect_uri=" + REDIRECT_URL + "&"
                        + "display=mobile&"
                        + "response_type=token&scope=" + APIFunctions.Scope.GROUPS +
                        "," + APIFunctions.Scope.FRIENDS +
                        "," + APIFunctions.Scope.WALL +
                        "," + APIFunctions.Scope.OFFLINE
        );

        webView.setWebViewClient(new MyWebViewClient());
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Загрузка");
        progressDialog.setMessage("Грузим страницу...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                webView.stopLoading();
            }
        });

    }


    @Override
    protected void onResume() {
        if(!loadFinished){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CookieManager.getInstance().removeSessionCookies(null);
                CookieManager.getInstance().removeAllCookies(null);
            }else{
                CookieManager.getInstance().removeAllCookie();
            }

progressDialog.setMessage("Выполняем выход с аккаунта...");
        webView.loadUrl(
                AUTHORIZE_URL
                        + "client_id=" + CLIENT_ID + "&"
                        + "redirect_uri=" + REDIRECT_URL + "&"
                        + "display=mobile&"
                        + "response_type=token&scope=" + APIFunctions.Scope.GROUPS +
                        "," + APIFunctions.Scope.FRIENDS +
                        "," + APIFunctions.Scope.WALL +
                        "," + APIFunctions.Scope.OFFLINE
        );
        System.out.println("Повторно");
        }
        super.onResume();

    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressDialog.setMessage("Загрузка страницы...");
            progressDialog.show();
            loadFinished = true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progressDialog.hide();
            webView.setVisibility(WebView.VISIBLE);
            loadFinished = false;

            if (url.startsWith(REDIRECT_URL)) {
                progressDialog.setMessage("Инициализация в приложении...");
                progressDialog.show();
                webView.setVisibility(WebView.INVISIBLE);
                try {

                   User.getInstance().setAccessToken(ParsDataHelper.parseRedirectUrl(url)[0],MainActivity.this);
                 User.getInstance().setUser_id(ParsDataHelper.parseRedirectUrl(url)[1],MainActivity.this);




                  class LoaderTasc extends AsyncTask<String, Void, Void>{


                      @Override
                      protected Void doInBackground(String... params) {

                          HTTPHelper.getInstance().requestGet(
                                  API_URL + "stats.trackVisitor?access_token=" + User.getInstance().getAccessToken(MainActivity.this), null
                          );

                          //	HTTPHelper.getInstance().HTTPHelper("http://vk.com/app1979194_52601950");

                          cookies = CookieManager.getInstance().getCookie(params[0]);
                          Log.d("MyLog", "All the cookies in a string:" + cookies);


                          User.getInstance().setAuth_key(ParsDataHelper.getAuthKey(HTTPHelper.getInstance().requestGet(APLICATION_URL, cookies)),MainActivity.this);




                          return null;
                      }

                      @Override
                      protected void onPostExecute(Void aVoid) {
                          super.onPostExecute(aVoid);
                          progressDialog.hide();
                         startActivity(new Intent(MainActivity.this, MainMenu.class));
                      }
                  }
                    progressDialog.setMessage("Получаем auth_key...");
                    new LoaderTasc().execute(url);

                    System.out.println(url);



                } catch (Exception e) {
                    System.out.print("Какая то ошибка)-" + e);
                }
                // начинаеться ли строка на определённый текст


            }

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.about:
                startActivity(new Intent(MainActivity.this, DeveloperActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}