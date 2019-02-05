package app.biblion.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import app.biblion.R;
import app.biblion.util.Constant;

public class OmrQuizAdapter extends RecyclerView.Adapter<OmrQuizAdapter.MyViewHolder> {
    View view;
    Context mContex;
    int[] num;
    static int count;

    public OmrQuizAdapter(Context mContex, int[] num) {
        this.mContex = mContex;
        this.num = num;
    }

    @NonNull
    @Override
    public OmrQuizAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.omr_quiz_card_list, viewGroup, false);
        return new OmrQuizAdapter.MyViewHolder(view);
    }


    @Override

    public void onBindViewHolder(@NonNull OmrQuizAdapter.MyViewHolder myVeiwHolder, int i) {
        i++;
        myVeiwHolder.txt_Que.setText("New Question " + i);
        Constant.btnNext.setEnabled(false);
        if (myVeiwHolder.radioGroup.getCheckedRadioButtonId() != -1) {
            Constant.btnNext.setEnabled(true);
        }
        myVeiwHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                count = ++count;
                if (R.id.radio_one == checkedId || R.id.radio_two == checkedId
                        || R.id.radio_three == checkedId || R.id.radio_four == checkedId) {
                    Constant.btnNext.setEnabled(true);
                }
            }
        });
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
        }


    }
}