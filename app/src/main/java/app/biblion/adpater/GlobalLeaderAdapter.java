package app.biblion.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.biblion.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class GlobalLeaderAdapter extends RecyclerView.Adapter<GlobalLeaderAdapter.MyViewHolder> {

    Context context;
    int[] num;

    public GlobalLeaderAdapter(Context context, int[] num) {
        this.context = context;
        this.num = num;
    }


    @NonNull
    @Override
    public GlobalLeaderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.global_leader_list,viewGroup,false);
        return new GlobalLeaderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GlobalLeaderAdapter.MyViewHolder myViewHolder, int i) {

        myViewHolder.Leader_List_Name.setText("NAME");
        myViewHolder.Leader_List_Country.setText("INDIA");
        myViewHolder.Leader_List_Point.setText("100");

    }

    @Override
    public int getItemCount() {
        return num.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView leader_List_Image;
        private TextView Leader_List_Name;
        private TextView Leader_List_Country;
        private TextView Leader_List_Point;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            leader_List_Image = itemView.findViewById(R.id.leader_list_image);
            Leader_List_Name = itemView.findViewById(R.id.txt_leader_name);
            Leader_List_Country = itemView.findViewById(R.id.txt_leader_country);
            Leader_List_Point = itemView.findViewById(R.id.txt_leader_point);
        }
    }
}
