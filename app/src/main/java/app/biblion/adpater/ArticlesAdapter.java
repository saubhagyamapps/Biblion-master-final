package app.biblion.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import app.biblion.R;
import app.biblion.model.ArticalModel;

public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<ArticalModel.ResultBean> dataBean;
    private boolean isLoadingAdded = false;

    public ArticlesAdapter(Context mContext) {
        this.mContext = mContext;
        dataBean = new ArrayList<>();

    }


    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }
    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.article_card_list, parent, false);
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        switch (getItemViewType(i)) {
            case ITEM:
                final MovieVH movieVH = (MovieVH) holder;
                movieVH.txt_heading_Art.setText(dataBean.get(i).getHeading());
                movieVH.txt_Title_Art.setText(dataBean.get(i).getTitle());
                movieVH.txt_Desc_Art.setText(dataBean.get(i).getDescription());
                Glide.with(mContext).load("http://192.168.1.200/biblion-API/public/images/" + dataBean.get(i).getImage())
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(movieVH.imageView);
            case LOADING:
                break;
        }

    }



    @Override
    public int getItemCount() {
        return dataBean == null ? 0 : dataBean.size();

    }

    @Override
    public int getItemViewType(int position) {
        return (position == dataBean.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(ArticalModel.ResultBean r) {
        dataBean.add(r);
        notifyItemInserted(dataBean.size() - 1);
    }

    public void addAll(List<ArticalModel.ResultBean> Results) {
        for (ArticalModel.ResultBean result : Results) {
            add(result);
        }
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new ArticalModel.ResultBean());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = dataBean.size() - 1;
        ArticalModel.ResultBean result = getItem(position);

        if (result != null) {
            dataBean.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ArticalModel.ResultBean getItem(int position) {
        return dataBean.get(position);
    }

    public class MovieVH extends RecyclerView.ViewHolder {

        CardView card_Article;
        TextView txt_heading_Art, txt_Title_Art, txt_Desc_Art;
        ImageView imageView;

        public MovieVH(@NonNull View itemView) {
            super(itemView);

            card_Article = itemView.findViewById(R.id.card_article);
            txt_heading_Art = itemView.findViewById(R.id.txt_heading_art);
            txt_Title_Art = itemView.findViewById(R.id.txt_title_art);
            txt_Desc_Art = itemView.findViewById(R.id.txt_desc_art);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
