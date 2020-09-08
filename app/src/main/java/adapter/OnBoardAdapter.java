package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.anshutiwari.telemedico.R;


public class OnBoardAdapter extends PagerAdapter {
    Context context;
    static LayoutInflater inflater;

    public OnBoardAdapter(Context context) {
        this.context = context;
    }

    public int[] list_image = {
            R.drawable.health_news,
            R.drawable.prescription,
            R.drawable.video_call_doctor
    };

    public String[] slide_title = {
            "Get your health care news on a go.",
            "Prescription from specialist doctor.",
            "Consult a doctor over chat/video call instantly"

    };


    @Override
    public int getCount() {
        return slide_title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (RelativeLayout) object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cell_onboard, container, false);
//        RelativeLayout layout_slide = view.findViewById(R.id.ll_slide);
        ImageView imgSlide = view.findViewById(R.id.iv_slide);
        TextView mTvSlideTitle = view.findViewById(R.id.tv_slide_title);
        imgSlide.setImageResource(list_image[position]);
        mTvSlideTitle.setText(slide_title[position]);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

}
