package com.qefee.pj.whotofollow;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.qefee.pj.whotofollow.util.NetUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    /**
     * log tag for MainActivity
     */
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView iv_logo1 = (ImageView) findViewById(R.id.iv_logo1);
        ImageView iv_logo2 = (ImageView) findViewById(R.id.iv_logo2);
        ImageView iv_logo3 = (ImageView) findViewById(R.id.iv_logo3);
        TextView tv_id1 = (TextView) findViewById(R.id.tv_id1);
        TextView tv_id2 = (TextView) findViewById(R.id.tv_id2);
        TextView tv_id3 = (TextView) findViewById(R.id.tv_id3);
        TextView tv_name1 = (TextView) findViewById(R.id.tv_name1);
        TextView tv_name2 = (TextView) findViewById(R.id.tv_name2);
        TextView tv_name3 = (TextView) findViewById(R.id.tv_name3);
        ImageButton ib_delete1 = (ImageButton) findViewById(R.id.ib_delete1);
        ImageButton ib_delete2 = (ImageButton) findViewById(R.id.ib_delete2);
        ImageButton ib_delete3 = (ImageButton) findViewById(R.id.ib_delete3);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Runnable runnable = new Runnable() {
//                    public void run() {
//
//                        String result = getUrlString();
//
//                        Log.i(TAG, String.format("run: result = %s", result));
//                    }
//                };
//
//                Thread t = new Thread(runnable);
//                t.start();

                String urlString = "https://api.github.com/users";
//                Looper backgroundLooper = Looper.
                Observable.just(urlString)
                        .observeOn(Schedulers.io())
                        .map(new Function<String, String>() {
                            @Override
                            public String apply(@io.reactivex.annotations.NonNull String s) throws Exception {
                                return NetUtil.getUrlString(s);
                            }
                        })
                        .flatMap(new Function<String, ObservableSource<JSONObject>>() {
                            @Override
                            public ObservableSource<JSONObject> apply(@io.reactivex.annotations.NonNull String s) throws Exception {
                                JSONArray jsonArray = new JSONArray(s);
                                JSONObject[] jsonObjects = new JSONObject[jsonArray.length()];
                                for (int i = 0; i <jsonArray.length(); i++) {
                                    jsonObjects[i] = jsonArray.getJSONObject(i);
                                }

                                return Observable.fromArray(jsonObjects);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<JSONObject>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull JSONObject jsonObject) throws Exception {
                                Log.i(TAG, String.format("accept: id = %d", jsonObject.getInt("id")));
                            }
                        });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
