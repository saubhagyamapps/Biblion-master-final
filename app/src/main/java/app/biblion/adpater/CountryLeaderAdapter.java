package app.biblion.adpater;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.biblion.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class CountryLeaderAdapter extends RecyclerView.Adapter<CountryLeaderAdapter.MyViewHolder> {

    Context context;
    int[] num;

    public CountryLeaderAdapter(Context context, int[] num) {
        this.context = context;
        this.num = num;
    }

    @NonNull
    @Override
    public CountryLeaderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_leader_list,viewGroup,false);
        return new CountryLeaderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryLeaderAdapter.MyViewHolder myViewHolder, int i) {

      // myViewHolder.leader_List_Image.setBackgroundResource(R.drawable.abcdedemoimage);
        myViewHolder.Leader_List_Name.setText("NAME");
        myViewHolder.Leader_List_Country.setText("INDIA");
        myViewHolder.Leader_List_Point.setText("100");

    }

    @Override
    public int getItemCount() {
        return num.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView leader_List_Image;
        TextView Leader_List_Name, Leader_List_Country,Leader_List_Point;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            leader_List_Image = itemView.findViewById(R.id.leader_list_image);
            Leader_List_Name = itemView.findViewById(R.id.txt_leader_name);
            Leader_List_Country = itemView.findViewById(R.id.txt_leader_country);
            Leader_List_Point = itemView.findViewById(R.id.txt_leader_point);
        }
    }
}
