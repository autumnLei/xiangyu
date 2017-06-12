package com.example.xiangyu.ui.buttons;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.xiangyu.R;
import com.example.xiangyu.adapter.PictureAdapter;
import com.example.xiangyu.entity.Picture;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PictureActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<Picture> mList = new ArrayList<>();
    String searchText;
    SearchView searchView;
    PictureAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
        initCoach();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PictureAdapter(mList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem item = menu.findItem(R.id.search_contact);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText;
                init();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return true;
    }

    private void initCoach() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Document doc_add = Jsoup.connect("http://www.ivsky.com/search.php?q=" + "体育明星").get();
                    Element els_temp2 = doc_add.select("ul.pli").first();
                    Elements els_add = els_temp2.select("li");
                    for (int i = 0; i < els_add.size(); i++) {
                        Element el_add = els_add.get(i).select("a").first();
                        Document doc_detail = Jsoup.connect("http://www.ivsky.com/" + el_add.attr("href")).get();
                        Element el_detail = doc_detail.select("div.left").first();
                        Picture pic = new Picture();
                        pic.setHref(els_add.get(i).select("img").first().attr("src"));
                        pic.setUrl(el_detail.select("img").first().attr("src"));
                        mList.add(pic);
                    }
                } catch (Exception e) {
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();

    }

    public void init() {
        mList.clear();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Document doc_add = Jsoup.connect("http://www.ivsky.com/search.php?q=" + searchText).get();
                    Element els_temp2 = doc_add.select("ul.pli").first();
                    Elements els_add = els_temp2.select("li");
                    for (int i = 0; i < els_add.size(); i++) {
                        Element el_add = els_add.get(i).select("a").first();
                        Document doc_detail = Jsoup.connect("http://www.ivsky.com/" + el_add.attr("href")).get();
                        Element el_detail = doc_detail.select("div.left").first();
                        Picture pic = new Picture();
                        pic.setHref(els_add.get(i).select("img").first().attr("src"));
                        pic.setUrl(el_detail.select("img").first().attr("src"));
                        mList.add(pic);
                    }
                } catch (Exception e) {
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();

    }

}
