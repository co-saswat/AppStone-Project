package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.anshutiwari.telemedico.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.NewsActivityAdapter;
import adapter.NewsAdapter;
import api.APIClient;
import api.API_Interface;
import component.Articles;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView mRcNews;
    private ImageView mIvBackButton;
    NewsActivityAdapter newsActivityAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //findViewById of the views
        mRcNews = findViewById(R.id.rv_news_activity);
        mIvBackButton = findViewById(R.id.iv_news_back);
        progressDialog = new ProgressDialog(NewsActivity.this);
        FloatingActionButton mFABScrollToTop = findViewById(R.id.fab_scroll_to_top);

        //Recycler View
        mRcNews.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        progressDialog.setMessage("Loading...");
        getHealthNews();

        //Scroll to top
        mFABScrollToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRcNews.smoothScrollToPosition(0);

            }
        });
    }

    private void getHealthNews() {

        progressDialog.show();
        API_Interface apiInterface = APIClient.getClient().create(API_Interface.class);
        Call<String> getHealthNews = apiInterface.getHealth();
        getHealthNews.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String value = response.body();
                ArrayList<Articles> articles = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(value);

                    JSONArray articlesArray = jsonObject.getJSONArray("articles");

                    for (int i = 0; i < articlesArray.length(); i++) {
                        Articles newArticles = Articles.parseJSONObject(articlesArray.optJSONObject(i));
                        articles.add(newArticles);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressDialog.hide();
                newsActivityAdapter = new NewsActivityAdapter(NewsActivity.this, articles);
                mRcNews.setAdapter(newsActivityAdapter);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.hide();
                Toast.makeText(NewsActivity.this, "Error encounter", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickMoveToDashboard(View view){
        startActivity(new Intent(NewsActivity.this,DashBoardActivity.class));
    }


}