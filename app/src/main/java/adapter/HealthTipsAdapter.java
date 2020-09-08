package adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anshutiwari.telemedico.R;

import java.util.ArrayList;

import component.HealthTips;

public class HealthTipsAdapter extends RecyclerView.Adapter<HealthTipsAdapter.HealthTipsHolder> {

    Context context;
    ArrayList<HealthTips> healthTips;

    public int[] list_video = {
            R.raw.habit_food,
            R.raw.habit_jogging,
            R.raw.habit_meditation,
            R.raw.habit_handwash
    };

    public String[] list_title = {
            "Better nutrition leads to improvement of body immunity ",
            "Jogging helps to build strong bones and improves cardiovascular fitness",
            "Meditation provides mental peace and stability",
            "Washing your hand regularly prevent you from respiratory disease and intestinal problems"
    };

    public HealthTipsAdapter(Context context){
//        this.healthTips = healthTips;
        this.context = context;
    }

    @NonNull
    @Override
    public HealthTipsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HealthTipsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_for_health_tips,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HealthTipsHolder holder, int position) {
        int videoUrl = list_video[position];
        String videoTitle = list_title[position];
        String videoPath = "android.resource://" + context.getPackageName() + "/" + videoUrl ;
        Uri uri = Uri.parse(videoPath);

        holder.mTvTipsTitle.setText(videoTitle);
        holder.mVideoTips.setVideoURI(uri);
        holder.mVideoTips.start();
        holder.mVideoTips.requestFocus();
        holder.mVideoTips.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });


     /*   MediaController mediaController = new MediaController(context);
        holder.mVideoTips.setMediaController(mediaController);
        mediaController.setAnchorView(holder.mVideoTips);*/
    }

    @Override
    public int getItemCount() {
        return list_title.length;
    }

    public class HealthTipsHolder extends RecyclerView.ViewHolder {

        private VideoView mVideoTips;
        private TextView mTvTipsTitle;
        public HealthTipsHolder(@NonNull View itemView) {
            super(itemView);

            mVideoTips = itemView.findViewById(R.id.vv_health_tips);
            mTvTipsTitle = itemView.findViewById(R.id.tv_health_tips_title);
        }
    }
}
