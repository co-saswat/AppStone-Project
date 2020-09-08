package ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.anshutiwari.telemedico.R;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.HealthTipsAdapter;
import adapter.NewsAdapter;
import adapter.SymptomsAdapter;
import api.APIClient;
import api.API_Interface;
import component.Articles;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Global Variable
    static final float END_SCALE = 0.7f;
    private RecyclerView mRcNewsArticle;
    private RecyclerView mRcHealthTips;
    private DrawerLayout mDlMenu;
    private NavigationView navigationView;
    private ImageView mIvDrawerMenu;
    private LinearLayout mLlDashboardContent;
    private LinearLayout mLlMoveToNews;
    private RelativeLayout mRlNewsShowAll;
    NewsAdapter newsAdapter;
    HealthTipsAdapter healthTipsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        //findViewById of the views
        mRcNewsArticle = findViewById(R.id.rv_news_articles);
        RecyclerView mRcCommonSymptoms = findViewById(R.id.rv_common_symptoms);
        mDlMenu = findViewById(R.id.drawer_dashboard);
        navigationView = findViewById(R.id.nv_dashboard);
        mIvDrawerMenu = findViewById(R.id.iv_drawer_menu);
        mLlDashboardContent = findViewById(R.id.ll_dashboard_content);
        mLlMoveToNews = findViewById(R.id.ll_move_to_news);
        mRlNewsShowAll = findViewById(R.id.rl_news_show_all);
        mRcHealthTips = findViewById(R.id.rv_health_tips);

        //Recycler Views and adapters
        mRcNewsArticle.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mRcHealthTips.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mRcCommonSymptoms.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        SymptomsAdapter symptomsAdapter = new SymptomsAdapter(DashBoardActivity.this);
        mRcCommonSymptoms.setAdapter(symptomsAdapter);

        healthTipsAdapter = new HealthTipsAdapter(this);
        mRcHealthTips.setAdapter(healthTipsAdapter);

        getHealthArticles();

        //Navigation Drawer
        navigationDrawerOpener();
    }

    private void navigationDrawerOpener() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.action_home);
        View headerView = navigationView.inflateHeaderView(R.layout.drawer_header);
        ImageView mIvEditProfile = headerView.findViewById(R.id.iv_profileEdit);

        mIvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this,EditProfileActivity.class));
            }
        });

        mIvDrawerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDlMenu.isDrawerVisible(GravityCompat.START)) {
                    mDlMenu.closeDrawer(GravityCompat.START);
                } else {
                    mDlMenu.openDrawer(GravityCompat.START);
                }
            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {

        mDlMenu.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                //Scale the view based on current slide offset
                final float diffScaleOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaleOffset;
                mLlDashboardContent.setScaleX(offsetScale);
                mLlDashboardContent.setScaleY(offsetScale);

                //Translate the View,accounting for the scaled width

                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = mLlDashboardContent.getWidth() * diffScaleOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                mLlDashboardContent.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDlMenu.isDrawerVisible(GravityCompat.START)) {
            mDlMenu.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    private void getHealthArticles() {
                   API_Interface api_interface = APIClient.getClient().create(API_Interface.class);
            Call<String> getHealth = api_interface.getHealth();
        getHealth.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String value = response.body();
                    ArrayList<Articles> articles = new ArrayList<>();

                    try {
                        JSONObject jsonObject = new JSONObject(value);

                        JSONArray articlesArray = jsonObject.getJSONArray("articles");

                        for (int i = 0; i < 6; i++) {
                            Articles newArticles = Articles.parseJSONObject(articlesArray.optJSONObject(i));
                            articles.add(newArticles);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    newsAdapter = new NewsAdapter(DashBoardActivity.this, articles);
                    mRcNewsArticle.setAdapter(newsAdapter);
                }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(DashBoardActivity.this, "Error encounter", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        mDlMenu.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.action_home:
                startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
                break;

            case R.id.action_news:
               startActivity(new Intent(getApplicationContext(), NewsActivity.class));
                break;

            case R.id.action_list_of_doctor:
                Toast.makeText(DashBoardActivity.this, "Move to List of Doctor activity", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_health_files:
                Toast.makeText(DashBoardActivity.this, "Move to Check your health record", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_invoice:
                Toast.makeText(DashBoardActivity.this, "Move to show your bill", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_about_us:
                Toast.makeText(DashBoardActivity.this, "Move to About us page", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_contact_us:
                Toast.makeText(DashBoardActivity.this, "Move to Contact us", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_what_we_treat:
                Toast.makeText(DashBoardActivity.this, "Move to how we treat", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public void moveToNewsActivity(View view){
        startActivity(new Intent(DashBoardActivity.this,NewsActivity.class));
    }


}