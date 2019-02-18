package app.biblion.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import app.biblion.R;
import app.biblion.model.ArticalDetailsModel;
import app.biblion.model.ArticalModel;
import app.biblion.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleClickFragment extends Fragment {
    View mView;
    String mArticaleId;
    ImageView clicked_Imagel;
    TextView clicked_Desc_txt, Clicked_Title_txt, Clicked_Heading_txt;
    String Clicked_Heading;
    ActionBar actionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_article_click, container, false);
//      getActivity().setTitle("Love Your Neighbour");
        Bundle args = getArguments();
        mArticaleId = args.getString("id");
        init();
        return mView;
    }


    private void init() {
        clicked_Imagel = mView.findViewById(R.id.click_article_image);
        clicked_Desc_txt = mView.findViewById(R.id.click_articaldesc);
        Clicked_Title_txt = mView.findViewById(R.id.click_article_title);

        getDetailArtical();
    }

    private void getDetailArtical() {
        Call<ArticalDetailsModel> articalClickModelCall = Constant.apiService.getArticalDetails(Integer.parseInt(mArticaleId));
        articalClickModelCall.enqueue(new Callback<ArticalDetailsModel>() {
            @Override
            public void onResponse(Call<ArticalDetailsModel> call, Response<ArticalDetailsModel> response) {

                clicked_Desc_txt.setText(response.body().getResult().get(0).getDescription().replace("\\r\\n",""));
                Clicked_Title_txt.setText(response.body().getResult().get(0).getTitle());
                Glide.with(getActivity()).load(response.body().getImage())
                        .thumbnail(0.5f)
                        .crossFade()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(clicked_Imagel);
            }

            @Override
            public void onFailure(Call<ArticalDetailsModel> call, Throwable t) {

            }
        });
    }
}
