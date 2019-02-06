package app.biblion.adpater;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import app.biblion.R;
import app.biblion.model.AllSongListModel;
import app.biblion.model.AllSongListModel;

public class SongBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<AllSongListModel.ResultBean> dataBean;
    private boolean isLoadingAdded = false;

    public SongBookAdapter(Context mContext) {
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
        View v1 = inflater.inflate(R.layout.sogn_book_list, parent, false);
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        switch (getItemViewType(i)) {
            case ITEM:
                final MovieVH movieVH = (MovieVH) holder;
                movieVH.txt_heading_Art.setText(dataBean.get(i).getSong_title());
                movieVH.txt_Desc_Art.setText(dataBean.get(i).getSong_disc());

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


    public void add(AllSongListModel.ResultBean r) {
        dataBean.add(r);
        notifyItemInserted(dataBean.size() - 1);
    }

    public void addAll(List<AllSongListModel.ResultBean> Results) {
        for (AllSongListModel.ResultBean result : Results) {
            add(result);
        }
    }
    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new AllSongListModel.ResultBean());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = dataBean.size() - 1;
        AllSongListModel.ResultBean result = getItem(position);

        if (result != null) {
            dataBean.remove(position);
            notifyItemRemoved(position);
        }
    }

    public AllSongListModel.ResultBean getItem(int position) {
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
    private Bitmap getBitmapFromURL(String s) {
        try {
            URL url = new URL(s);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
