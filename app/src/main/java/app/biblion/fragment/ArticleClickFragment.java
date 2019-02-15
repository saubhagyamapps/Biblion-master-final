package app.biblion.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.biblion.R;
import app.biblion.model.ArticalModel;
import app.biblion.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleClickFragment extends Fragment {
    View mView;
    String mBookId;
    ImageView clicked_Imagel;
    TextView clicked_Desc_txt, Clicked_Title_txt,Clicked_Heading_txt;
    String Clicked_Heading;
    ActionBar actionBar;
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



    private void init()
    {
        clicked_Imagel = mView.findViewById(R.id.click_article_image);
        clicked_Desc_txt = mView.findViewById(R.id.click_articaldesc);
        Clicked_Title_txt = mView.findViewById(R.id.click_article_title);
        Clicked_Title_txt.setText("Title Name");

    }

    private void getDetailArtical()
    {
        Call<ArticalModel> articalClickModelCall = Constant.apiService.getArticalList(1);
        articalClickModelCall.enqueue(new Callback<ArticalModel>() {
            @Override
            public void onResponse(Call<ArticalModel> call, Response<ArticalModel> response) {

            }

            @Override
            public void onFailure(Call<ArticalModel> call, Throwable t) {

            }
        });
    }
}
