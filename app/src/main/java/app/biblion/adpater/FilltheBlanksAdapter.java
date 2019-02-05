package app.biblion.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import app.biblion.R;
import app.biblion.util.Constant;

public class FilltheBlanksAdapter extends RecyclerView.Adapter<FilltheBlanksAdapter.MyViewHolder> {
        View view;
        Context mContext;
        int[] num;

    public FilltheBlanksAdapter(Context mContext, int[] num) {
        this.mContext = mContext;
        this.num = num;
    }

    @NonNull
    @Override
    public FilltheBlanksAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.quiz_blanks_card_list, viewGroup, false);
        return new FilltheBlanksAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilltheBlanksAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.txt_Que.setText("No." + "The students returned to the classroom to collect_________ belongings," +
                "but they found nothing over________.");

        myViewHolder.radio_one.setText("None of the above");
        myViewHolder.radio_two.setText("Their, their");
        myViewHolder.radio_three.setText("Their, there");
        myViewHolder.radio_four.setText("There, there");

        if (num.length == i+1) {
            myViewHolder.btn_click.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.btn_click.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return num.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Button btn_click;
        TextView txt_Que;
        RadioGroup radioGroup;
        RadioButton radio_one, radio_two, radio_three, radio_four;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_Que = itemView.findViewById(R.id.txt_que);
            radioGroup = itemView.findViewById(R.id.radio_group);
            radio_one = itemView.findViewById(R.id.radio_one);
            radio_two = itemView.findViewById(R.id.radio_two);
            radio_three = itemView.findViewById(R.id.radio_three);
            radio_four = itemView.findViewById(R.id.radio_four);
            btn_click = itemView.findViewById(R.id.btn_click);
        }
    }
}
