package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anshutiwari.telemedico.R;

public class SymptomsAdapter extends RecyclerView.Adapter<SymptomsAdapter.SymptomsHolder> {

    private Context context;
    private String[] symptomsName;
    private int[] symptomsImageValue = {
            R.drawable.symptoms_fever,
            R.drawable.symptoms_cough,
            R.drawable.symptoms_hairfall,
            R.drawable.symptoms_diabetes,
            R.drawable.symptoms_acne,
            R.drawable.symptoms_weight_loss,
            R.drawable.symptoms_depression,
            R.drawable.symptoms_throat,
            R.drawable.symptoms_stomach_ache,
            R.drawable.symptoms_constipitation,
            R.drawable.symptoms_acidity,
            R.drawable.symptoms_back_pain
    };

    public SymptomsAdapter(Context context) {
        this.context = context;
        this.symptomsName = context.getResources().getStringArray(R.array.symptoms_name);
    }

    @NonNull
    @Override
    public SymptomsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SymptomsHolder(LayoutInflater.from(context).inflate(R.layout.cell_for_symptoms, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomsHolder holder, int position) {

        String currentSymptomsName = symptomsName[position];
        holder.mTvSymptomsName.setText(currentSymptomsName);
        holder.mIvSymptomsImage.setImageResource(symptomsImageValue[position]);
    }

    @Override
    public int getItemCount() {
        return symptomsName.length;
    }

    public class SymptomsHolder extends RecyclerView.ViewHolder {
        private TextView mTvSymptomsName;
        private ImageView mIvSymptomsImage;

        public SymptomsHolder(@NonNull View itemView) {
            super(itemView);

            mTvSymptomsName = itemView.findViewById(R.id.tv_symptoms_name);
            mIvSymptomsImage = itemView.findViewById(R.id.iv_symptoms_image);

        }
    }
}
