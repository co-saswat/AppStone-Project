package adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anshutiwari.telemedico.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import component.Articles;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder>  {

    Context context;
    ArrayList<Articles> articles;

    public NewsAdapter(Context context, ArrayList<Articles> articles) {
        this.articles = articles;
        this.context = context;

    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsHolder(LayoutInflater.from(context).inflate(R.layout.cell_for_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        Articles currentArticle = articles.get(position);

        if (currentArticle != null) {
            holder.mTvTitle.setText(currentArticle.title);
            holder.mTvDescription.setText(currentArticle.description);
            holder.mTvURL.setText(currentArticle.url);
            Glide.with(context).load(currentArticle.urlToImage).placeholder(R.drawable.no_image_found).into(holder.mIvNewsImage);

        }

    }

    @Override
    public int getItemCount() {
        return articles != null ? articles.size() : 0;
//        return 5 ;
    }


    public class NewsHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        private TextView mTvDescription;
        private ImageView mIvNewsImage;
        private TextView mTvURL;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            mTvTitle = itemView.findViewById(R.id.tv_news_title);
            mTvDescription = itemView.findViewById(R.id.tv_news_description);
            mIvNewsImage = itemView.findViewById(R.id.iv_news_image);
            mTvURL = itemView.findViewById(R.id.tv_news_url);


        }
    }
}

