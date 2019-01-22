package app.biblion.adpater;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import app.biblion.R;
import app.biblion.interfacea.BookClick;

public class MyLibraryAdapter extends RecyclerView.Adapter<MyLibraryAdapter.MyHolder> {

    Context mContext;
    int[] num;
    BookClick bookClick;

    public MyLibraryAdapter(Context mContext, int[] num, BookClick bookClick) {
        this.mContext = mContext;
        this.num = num;
        this.bookClick = bookClick;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_library_list, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {
        myHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookClick.bookClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
