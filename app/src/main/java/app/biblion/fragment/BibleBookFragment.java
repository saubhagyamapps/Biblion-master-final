package app.biblion.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.folioreader.Config;
import com.folioreader.FolioReader;
import com.folioreader.model.HighLight;
import com.folioreader.model.ReadPosition;
import com.folioreader.model.ReadPositionImpl;
import com.folioreader.ui.base.OnSaveHighlight;
import com.folioreader.util.AppUtil;
import com.folioreader.util.OnHighlightListener;
import com.folioreader.util.ReadPositionListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.biblion.R;


public class BibleBookFragment extends Fragment implements OnHighlightListener, ReadPositionListener, FolioReader.OnClosedListener,View.OnClickListener {

    private static final String LOG_TAG = BibleBookFragment.class.getSimpleName();
    private FolioReader folioReader;
    View mView;
    ImageView imagesA,imagesB,imagesC,imagesD;
    ReadPosition readPosition;
    Config config;
    String selectedOfferId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.bible_book_fragment, container, false);
        getActivity().setTitle(R.string.bible_book);
        initialization();

        return mView;
    }

    private void initialization() {
        imagesA=mView.findViewById(R.id.imagesA);
        imagesB=mView.findViewById(R.id.imagesB);
        imagesC=mView.findViewById(R.id.imagesC);
        imagesD=mView.findViewById(R.id.imagesD);
        imagesA.setOnClickListener(this);
        imagesB.setOnClickListener(this);
        imagesC.setOnClickListener(this);
        imagesD.setOnClickListener(this);
        folioReader = FolioReader.get()
                .setOnHighlightListener(this)
                .setReadPositionListener(this)
                .setOnClosedListener(this);

        getHighlightsAndSave();

         readPosition = getLastReadPosition();

         config = AppUtil.getSavedConfig(getActivity());
        if (config == null)
            config = new Config();
        config.setAllowedDirection(Config.AllowedDirection.VERTICAL_AND_HORIZONTAL);

    }


    private ReadPosition getLastReadPosition() {

        String jsonString = loadAssetTextAsString("read_positions/read_position.json");
        return ReadPositionImpl.createInstance(jsonString);
    }

    @Override
    public void saveReadPosition(ReadPosition readPosition) {

        Log.i(LOG_TAG, "-> ReadPosition = " + readPosition.toJson());
    }

    /*
     * For testing purpose, we are getting dummy highlights from asset. But you can get highlights from your server
     * On success, you can save highlights to FolioReader DB.
     */
    private void getHighlightsAndSave() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<HighLight> highlightList = null;
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    highlightList = objectMapper.readValue(
                            loadAssetTextAsString("highlights/highlights_data.json"),
                            new TypeReference<List<HighlightData>>() {
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (highlightList == null) {
                    folioReader.saveReceivedHighLights(highlightList, new OnSaveHighlight() {
                        @Override
                        public void onFinished() {
                            //You can do anything on successful saving highlight list
                        }
                    });
                }
            }
        }).start();
    }

    private String loadAssetTextAsString(String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = getActivity().getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ((str = in.readLine()) != null) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            Log.e("HomeActivity", "Error opening asset " + name);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("HomeActivity", "Error closing asset " + name);
                }
            }
        }
        return null;
    }

  /*  @Override
    protected void onDestroy() {
        super.onDestroy();
        FolioReader.clear();
    }*/

    @Override
    public void onHighlight(HighLight highlight, HighLight.HighLightAction type) {
        Toast.makeText(getActivity(),
                "highlight id = " + highlight.getUUID() + " type = " + type,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFolioReaderClosed() {
        Log.v(LOG_TAG, "-> onFolioReaderClosed");
       // getFragmentManager().beginTransaction().replace(R.id.contant_frame, new HomeBookFragment()).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imagesA:
                folioReader.setReadPosition(readPosition)
                        .setConfig(config, true)
                        .openBook("file:///android_asset/BibleBook.epub");
                break; case R.id.imagesB:
                folioReader.setReadPosition(readPosition)
                        .setConfig(config, true)
                        .openBook("file:///android_asset/TheHolyBibleKingJames.epub");
                break; case R.id.imagesC:
                folioReader.setReadPosition(readPosition)
                        .setConfig(config, true)
                        .openBook("file:///android_asset/ASV.epub");
                break; case R.id.imagesD:
                folioReader.setReadPosition(readPosition)
                        .setConfig(config, true)
                        .openBook("file:///android_asset/TheHolyBibleKingJames.epub");
                break;
        }
    }
}