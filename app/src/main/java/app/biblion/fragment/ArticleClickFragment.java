package app.biblion.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import app.biblion.R;
import app.biblion.model.ArticleDetailModel;
import app.biblion.model.DevotionModel;
import app.biblion.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleClickFragment extends Fragment {
    View mView;
    String mBookId;
    ImageView clicked_Imagel;
    TextView clicked_Desc_txt, clicked_Title_txt, clicked_Heading_txt;
    String clicked_Heading;
    Spanned dicription;
    int i = 0;
    List<ArticleDetailModel> articleDetailModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_article_click, container, false);
//      getActivity().setTitle("Love Your Neighbour");
        Bundle args = getArguments();
        mBookId = args.getString("id");
        init();
        return mView;
    }


    private void init() {
        clicked_Imagel = mView.findViewById(R.id.click_article_image);
        clicked_Desc_txt = mView.findViewById(R.id.click_articaldesc);
        clicked_Title_txt = mView.findViewById(R.id.click_article_title);
        clicked_Title_txt.setText("Title Name");


    }



}
