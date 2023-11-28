package com.example.airscan;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoadingPassChange extends AppCompatActivity {

    private static final int CHECK_INTERVAL_MS = 500; // Every half a second
    private static final int TIMEOUT_MS = 10000; // 10 seconds
    private WebView webview;
    private ProgressBar progress;
    private boolean hasFormSubmitted = false;
    private String username;
    private String dfaultPass = "string";
    private String newpass;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        progress = findViewById(R.id.loading);

        //get data
        username = getIntent().getStringExtra("username");
        newpass = getIntent().getStringExtra("newpass");

        //Configure webview setting
        webview = findViewById(R.id.wv_loading);

        //Clear cookie
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookies(null);


        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setDomStorageEnabled(true);
        Log.i("WebView2", "Load Loaded LoadingPassChange.java");

        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("WebView2", "Base URL loaded: " + url);
                super.onPageFinished(view, url);

                //Login button. Link: https://uiot.ixxc.dev/swagger/#/
                if(url.contains("swagger")){
                    clickButtonWhenAvailable(view, ".btn.authorize.unlocked");
                }

                //Link: https://uiot.ixxc.dev/auth/realms/master/login-actions/authenticate?client_id=openremote&tab_id=2ZTjZBFXhvQ
                //Fill the login form
                if(url.contains("openid-connect/auth") || url.contains("login-actions/authenticate")){
                    Log.i("WebView2", "Login: " + url);
                    String userScript = "document.getElementById('username').value = '" + username + "';";
                    String pwdScript = "document.getElementById('password').value = '"+ dfaultPass + "';";
                    Log.i("WebView2", username);
                    Log.i("WebView2", dfaultPass);

                    view.evaluateJavascript(userScript, null);
                    view.evaluateJavascript(pwdScript, null);
                    if(!hasFormSubmitted) {
                        view.evaluateJavascript("document.querySelector('button[name=\"login\"]').click();", null);
                        hasFormSubmitted = true;
                    }
                }

                //Submit Form, display error, ...
                if (hasFormSubmitted) {
                    view.evaluateJavascript(
                            "(function() { \n" +
                                    " let invalidEmail = document.querySelector('[data-error=\"Invalid email address.\"]'); \n" +
                                    " let passwordError = document.querySelector('[data-error=\"Password confirmation doesn\\'t match.\"]'); \n" +
                                    " let usernameError = document.querySelector('span.red-text');\n" +
                                    " else if (usernameError) return 'usernameError';\n" +
                                    " else if (passwordError) return 'passwordError';\n" +
                                    " else return null\n" +
                                    " })();",
                            value -> {
                                Log.i("WebView2", "hasFormSubmitted: " + url);
                                if (value != null && !value.equals("null")){
                                    switch (value){
                                        case "\"invalidEmail\"":
                                            Toast.makeText(LoadingPassChange.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                                            break;
                                        case "\"passwordError\"":
                                            Toast.makeText(LoadingPassChange.this, "Password confirmation doesn't match", Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                } else {
                                    /*Toast.makeText(LoadingPassChange.this, "Login Form OK!", Toast.LENGTH_SHORT).show();*/
                                    Log.i("WebView2", "Submit Login form ok. Now turn into Change password form!");
                                }
                                //
                            });
                }

                //Fill the update password form
                if(url.contains("login-actions/required-action")){
                    Log.i("WebView2", "Loaded ENTER NEW PASSWORD form: " + url);
                    String newPassScript = "document.getElementById('password-new').value = '" + newpass + "';";
                    String reNewPassScript = "document.getElementById('password-confirm').value = '"+ newpass + "';";
                    Log.i("WebView2", newpass);
                    view.evaluateJavascript(newPassScript, null);
                    view.evaluateJavascript(reNewPassScript, null);
                    view.evaluateJavascript("document.querySelector('button[type=\\\"submit\\\"]').click();", null);
                    Toast.makeText(LoadingPassChange.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();

                    //Make changes done -> go to main to Login again
                    Intent intent = new Intent(LoadingPassChange.this, MainActivity.class);
                    startActivity(intent);
                    finish();  //Stop Activity
                }
            }
        });
        webview.loadUrl("https://uiot.ixxc.dev/swagger/#/");
    }

    private void clickButtonWhenAvailable(WebView webView, String selector) {
        final long startTime = System.currentTimeMillis();

        final Runnable checkInitialButtonExistence  = new Runnable() {
            @Override
            public void run() {
                webView.evaluateJavascript(
                        "(function(selector) { return !!document.querySelector(selector); })('" + selector + "');",
                        value -> {
                            // If button exists or we've reached timeout, try clicking or exit
                            if ("true".equals(value) || System.currentTimeMillis() - startTime > TIMEOUT_MS) {
                                webView.evaluateJavascript("document.querySelector('" + selector + "').click();", null);
                                webView.evaluateJavascript("document.querySelector('.btn.modal-btn.auth.authorize.button').click();",null);
                            } else {
                                // Otherwise, keep checking
                                new Handler().postDelayed(this, CHECK_INTERVAL_MS);
                            }
                        }
                );
            }
        };
        // Start the checking
        new Handler().postDelayed(checkInitialButtonExistence , CHECK_INTERVAL_MS);
    }

}