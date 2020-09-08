package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anshutiwari.telemedico.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

import component.Articles;

public class NewsActivityAdapter extends RecyclerView.Adapter<NewsActivityAdapter.NewsActivityHolder> implements Filterable {

    Context context;
    ArrayList<Articles> newsArticles;
    ArrayList<Articles> displayNewsArticles;
    ArrayList<Articles> suggestNewsArticles;
    private NewsFilter filter;

    public NewsActivityAdapter(Context context,ArrayList<Articles> newsArticles){
        this.newsArticles = newsArticles;
        this.displayNewsArticles = (ArrayList<Articles>) newsArticles.clone();
        this.suggestNewsArticles = new ArrayList<>();
        this.context = context;
        filter = new NewsFilter();
    }

    @NonNull
    @Override
    public NewsActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsActivityHolder(LayoutInflater.from(context).inflate(R.layout.cell_for_news_in_activity,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsActivityHolder holder, int position) {

        Articles currentNewsArticle = newsArticles.get(position);
        if(currentNewsArticle != null){
            holder.mTvNewsTitle.setText(currentNewsArticle.title);
            holder.mTvNewsDescription.setText(currentNewsArticle.description);
            holder.mTvNewsUrl.setText(currentNewsArticle.url);
            Glide.with(context).load(currentNewsArticle.urlToImage)
                    .placeholder(R.drawable.no_image_found)
                    .into(holder.mIvNewsImage);
        }

    }

    @Override
    public int getItemCount() {
        return newsArticles != null ? newsArticles.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public class NewsFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            suggestNewsArticles.clear();
            if (constraint != null){
                for (Articles article : newsArticles){
                    if(article.title.toLowerCase().contains(constraint.toString().toLowerCase().trim())){
                        suggestNewsArticles.add(article);
                    }else if(article.description.toLowerCase().contains(constraint.toString().toLowerCase().trim())){
                        suggestNewsArticles.add(article);
                    }
                }
            }
            results.values = suggestNewsArticles;
            results.count = suggestNewsArticles.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if(results != null){
                if (results.count > 0){
                    displayNewsArticles = (ArrayList<Articles>) results.values;
                    notifyDataSetChanged();
                }
            }
        }
    }

    public class NewsActivityHolder extends RecyclerView.ViewHolder {

        private TextView mTvNewsTitle;
        private TextView mTvNewsDescription;
        private TextView mTvNewsUrl;
        private ImageView mIvNewsImage;

        public NewsActivityHolder(@NonNull View itemView) {
            super(itemView);

            mTvNewsTitle = itemView.findViewById(R.id.tv_news_title_activity);
            mTvNewsDescription = itemView.findViewById(R.id.tv_news_description_activity);
            mTvNewsUrl = itemView.findViewById(R.id.tv_news_url_activity);
            mIvNewsImage = itemView.findViewById(R.id.iv_news_image_activity);
        }
    }
}
