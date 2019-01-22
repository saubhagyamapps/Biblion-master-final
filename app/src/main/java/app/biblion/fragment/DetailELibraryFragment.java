package app.biblion.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.devs.readmoreoption.ReadMoreOption;

import app.biblion.R;

public class DetailELibraryFragment extends Fragment {

    View mView;

    RatingBar bookRatingBar;
    TextView txtDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_detail_elibrary, container, false);
        initialization();

        return mView;
    }

    private void initialization() {
        txtDescription = mView.findViewById(R.id.txtDescription);
        bookRatingBar = mView.findViewById(R.id.book_rating);
        bookRatingBar.setRating((float) 2.5);
        ReadMoreOption readMoreOption = new ReadMoreOption.Builder(getContext())
                .textLength(150)
                .moreLabel("Read More")
                .lessLabel("Read Leas")
                .moreLabelColor(getResources().getColor(R.color.colorPrimaryDark))
                .lessLabelColor(getResources().getColor(R.color.colorPrimaryDark))
                .labelUnderLine(true)
                .build();

        readMoreOption.addReadMoreTo(txtDescription, getString(R.string.long_desc));
    }


}
