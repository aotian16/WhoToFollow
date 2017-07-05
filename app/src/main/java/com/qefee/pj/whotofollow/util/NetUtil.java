package com.qefee.pj.whotofollow.util;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * NetUtil.
 * <ul>
 * <li>date: 2017/7/5</li>
 * </ul>
 *
 * @author tongjin
 */

public class NetUtil {


    public static String getUrlString(@NonNull String urlString) {
        String result = "";
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(urlString);

            URLConnection urlConnection = url.openConnection();
            httpURLConnection = (HttpURLConnection) urlConnection;

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = httpURLConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                result = sb.toString();
//                Log.i(TAG, String.format("onClick: api string = %s", result));

            } else {
//                Log.i(TAG, String.format("onClick: response code = %d", responseCode));
            }
        } catch (Exception e) {
//            Log.e(TAG, "onClick: ", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return result;
    }
}
