package app.biblion.adpater;

import android.content.Context;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import app.biblion.interfacea.BookClick;
import app.biblion.model.CategoryModel;
import app.biblion.model.MyLibraryBookModel;

public class CategoryBookAdepter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    private static final String TAG = "MyLibraryBookAdepter";

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<CategoryModel.ResultBean> dataBean;
    private boolean isLoadingAdded = false;
    BookClick bookClick;
    public CategoryBookAdepter(Context mContext, BookClick bookClick) {
        this.mContext = mContext;
        dataBean = new ArrayList<>();
        this.bookClick=bookClick;
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
        View v1 = inflater.inflate(R.layout.my_library_list, parent, false);
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        switch (getItemViewType(i)) {
            case ITEM:
                final MovieVH movieVH = (MovieVH) holder;
                movieVH.txtBookName.setText(dataBean.get(i).getBookname());
                Log.e(TAG, "onBindViewHolder: "+dataBean.get(i).getBookname() );
                Glide.with(mContext).load("http://frozenkitchen.in/biblion_demo/public/images/" + dataBean.get(i).getImage())
                        .thumbnail(0.5f)
                        .crossFade()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(movieVH.imageViewBook);

                movieVH.imageViewBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    //    bookClick.bookClick(dataBean.get(i).getId());
                    }
                });
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


    public void add(CategoryModel.ResultBean r) {
        dataBean.add(r);
        notifyItemInserted(dataBean.size() - 1);
    }

    public void addAll(List<CategoryModel.ResultBean> Results) {
        for (CategoryModel.ResultBean result : Results) {
            add(result);
        }
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new CategoryModel.ResultBean());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = dataBean.size() - 1;
        CategoryModel.ResultBean result = getItem(position);

        if (result != null) {
            dataBean.remove(position);
            notifyItemRemoved(position);
        }
    }

    public CategoryModel.ResultBean getItem(int position) {
        return dataBean.get(position);
    }

    public class MovieVH extends RecyclerView.ViewHolder {


        TextView txtBookName;
        ImageView imageViewBook;

        public MovieVH(@NonNull View itemView) {
            super(itemView);

            imageViewBook = itemView.findViewById(R.id.imageViewBook);
            txtBookName = itemView.findViewById(R.id.txtBookName);


        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

}
