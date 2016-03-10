package com.joy.ep.myhttptext;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.TypeReference;
import com.joy.ep.myhttptext.bean.Brand;
import com.joy.ep.myhttptext.bean.DemoResultBean;
import com.joy.ep.myhttptext.bean.Gan;
import com.joy.ep.myhttptext.bean.JsonResult;
import com.joy.ep.myhttptext.bean.Location;
import com.joy.ep.myhttptext.bean.MyJsonResult;
import com.joy.ep.myhttptext.http.AppDao;
import com.joy.ep.myhttptext.http.BaseTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jclick.httpwrapper.callback.MyCallBack;

public class MainActivity extends BaseActivity {

    private TextView test;
    private TextView cache;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        test = (TextView) findViewById(R.id.test);
        cache = (TextView) findViewById(R.id.cache);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton hfab = (FloatingActionButton) findViewById(R.id.button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        hfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String targetURL = "http://222.177.210.200/supplier/news/getNewsList";
                Map<String, String> map = new HashMap<>();
                map.put("newsTypeVal", "CC");
                new BaseTask<JsonResult<Brand>, String>(MainActivity.this, fab) {
                    @Override
                    public TypeReference setTypeToken() {
                        return new TypeReference<Brand>() {

                        };
                    }

                    @Override
                    public void onSuccess() {
                        if (result.isSuccess()) {
                            test.setText(result.getRecord().getNewsList().get(0).getTitle());
                        }
                    }

                    @Override
                    public void onCache() {
                        cache.setText(result.getRecord().getNewsList().get(0).getUserName());
                    }
                }.requestByPost(targetURL, map);
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
        String targetURL = "http://222.177.210.200/supplier/news/getNewsList";
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.IP:
                AppDao.getInstance().fu(new MyCallBack<DemoResultBean<Location>>(this, new TypeReference<DemoResultBean<Location>>() {

                }) {

                    @Override
                    protected void onSuccess(DemoResultBean<Location> result) {
                        test.setText(result.getData().getCity() + "◑﹏◐");
                        cache.setText("Null");
                    }

                    @Override
                    protected void onCache(DemoResultBean<Location> result) {
                        test.setText("Null");
                        cache.setText(result.getData().getArea());
                    }


                });
                break;
            case R.id.News:
                AppDao.getInstance().news(targetURL, new MyCallBack<MyJsonResult<Brand>>(this, new TypeReference<MyJsonResult<Brand>>() {

                }) {
                    @Override
                    protected void onSuccess(MyJsonResult<Brand> result) {
                        Toast.makeText(context, result.getRecord().getNewsList().get(0).getSubtitle(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void onCache(MyJsonResult<Brand> result) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Snackbar.make(fab, result.getRecord().getNewsList().get(0).getUserName(), Snackbar.LENGTH_LONG).setAction("更多", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    context.startActivity(new
                                            Intent(Settings.ACTION_WIFI_SETTINGS));
                                }
                            })
                                    .show();
                        } else {
                            Toast.makeText(context, result.getRecord().getNewsList().get(0).getTitle(), Toast.LENGTH_SHORT).show();
                        }

                        return;
                    }
                });
                break;
            case R.id.Gank:
                AppDao.getInstance().Te(new MyCallBack<List<Gan>>(this) {


                    @Override
                    protected void onSuccess(List<Gan> result) {

                        Toast.makeText(context, result.get(0).getWho(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    protected void onCache(List<Gan> result) {

                    }
                });

                return true;

        }


        return super.onOptionsItemSelected(item);
    }
}
