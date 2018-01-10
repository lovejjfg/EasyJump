/*
 * Copyright (c) 2017.  Joe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.lovejjfg.easyjump.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.lovejjfg.easyjump.MainActivity;
import com.lovejjfg.easyjump.SplashActivity;
import com.lovejjfg.easyjump.Test1Activity;
import com.lovejjfg.easyjump.Test2Activity;
import com.lovejjfg.easyjump.WebActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by joe on 2017/12/28.
 * Email: lovejjfg@gmail.com
 */

public class JumpUtils {
    private static final String TAG = JumpUtils.class.getSimpleName();

    public static void jumpHome(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void jumpWeb(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(Constants.URL, url);
        context.startActivity(intent);
    }

    public static void jumpDefaultWeb(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public static void jumpTest1(Context context) {
        Intent intent = new Intent(context, Test1Activity.class);
        intent.putExtra(Constants.TITLE, "首页点击跳转1！");
        context.startActivity(intent);
    }

    public static void jumpTest2(Context context) {
        Intent intent = new Intent(context, Test2Activity.class);
        intent.putExtra(Constants.TITLE, "首页点击跳转2！");
        context.startActivity(intent);
    }

    public static boolean isKnownSchemes(String url) {
        return !TextUtils.isEmpty(url) && (isYiEr(url) || isHttp(url));
    }


    private static boolean isYiEr(String url) {
        return url.startsWith(Constants.KNOWN_SCHEME);
    }

    @Nullable
    public static Intent parseIntent(Context context, String url, String title) {
        //"url": "test://test1/1011"
        if (!isKnownSchemes(url)) {
            return null;
        }
        if (isHttp(url)) {
            return parseHttp(context, url, title);
        }
        try {
            Uri data = Uri.parse(url);
            String scheme = data.getScheme();
            String host = data.getHost();
            String path = data.getPath();
            Set<String> queryParameterNames = data.getQueryParameterNames();
            HashMap<String, String> map = null;
            if (!queryParameterNames.isEmpty()) {
                map = new HashMap<>();
                for (String name : queryParameterNames) {
                    map.put(name, data.getQueryParameter(name));
                }
            }
            Log.i(TAG, "host: " + host);
            Log.i(TAG, "path: " + path);
            Log.i(TAG, "scheme: " + scheme);
            if (map != null) {
                Log.i(TAG, "query: " + map.toString());
            }
            return parseSchemes(context, host, path, map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isHttp(String url) {
        return url.startsWith(Constants.HTTP_SCHEME) || url.startsWith(Constants.HTTPS_SCHEME);
    }

    public static boolean parseIntentAndJump(Context context, String url, String title) {
        Intent intent = parseIntent(context, url, title);
        if (intent != null) {
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    private static Intent parseHttp(Context context, String url, String title) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(Constants.URL, url);
        intent.putExtra(Constants.TITLE, title);
        return intent;
    }

    @Nullable
    private static Intent parseSchemes(Context context, String host, String path, Map<String, String> queryies) {
        Log.i(TAG, "parse: host:" + host);
        Log.i(TAG, "parse: path:" + path);
        Log.i(TAG, "parse: queryies:" + queryies);
        String[] paths = path.split("/");
        Intent intent;
        // TODO: 2017/12/28  finish your code
        switch (host) {
            case "test1":
                intent = new Intent(context, Test1Activity.class);
                try {
                    intent.putExtra(Constants.TITLE, queryies.get("title"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return intent;
            case "test2":
                intent = new Intent(context, Test2Activity.class);
                try {
                    intent.putExtra(Constants.TITLE, queryies.get("title"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return intent;
        }
        intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }


}
