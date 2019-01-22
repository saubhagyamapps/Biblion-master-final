package app.biblion.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.biblion.R;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyHolder> {

    Context mContext;
    int[] num ;

    public ArticlesAdapter(Context mContext, int[] num) {
        this.mContext = mContext;
        this.num = num;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_card_list,viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

        myHolder.txt_heading_Art.setText("Here comes Heading");
        myHolder.txt_Title_Art.setText("Here Comes Title");
        myHolder.txt_Desc_Art.setText("Here Comes Description");

    }

    @Override
    public int getItemCount() {
        return num.length;
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        CardView card_Article;
        TextView txt_heading_Art, txt_Title_Art, txt_Desc_Art;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            card_Article = itemView.findViewById(R.id.card_article);
            txt_heading_Art = itemView.findViewById(R.id.txt_heading_art);
            txt_Title_Art = itemView.findViewById(R.id.txt_title_art);
            txt_Desc_Art =  itemView.findViewById(R.id.txt_desc_art);
        }
    }
}
