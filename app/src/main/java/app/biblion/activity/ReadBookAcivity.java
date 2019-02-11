package app.biblion.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import app.biblion.fragment.HighlightData;

public class ReadBookAcivity extends AppCompatActivity implements OnHighlightListener, ReadPositionListener, FolioReader.OnClosedListener{
    private static final String LOG_TAG = "ReadBookAcivity";
    ReadPosition readPosition;
    Config config;
    private FolioReader folioReader;
    String mBookPath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("Book_Path", MODE_PRIVATE);
        mBookPath = prefs.getString("path", "No name defined");

        folioReader = FolioReader.get()
                .setOnHighlightListener(this)
                .setReadPositionListener(this)
                .setOnClosedListener(this);

        getHighlightsAndSave();

        readPosition = getLastReadPosition();

        config = AppUtil.getSavedConfig(getApplicationContext());
        if (config == null)
            config = new Config();
        config.setAllowedDirection(Config.AllowedDirection.VERTICAL_AND_HORIZONTAL);
        folioReader.setReadPosition(readPosition)
                .setConfig(config, true)
                .openBook(mBookPath);
    }
    private ReadPosition getLastReadPosition() {

        String jsonString = loadAssetTextAsString("read_positions/read_position.json");
        return ReadPositionImpl.createInstance(jsonString);
    }
    @Override
    public void onFolioReaderClosed() {
        Log.v(LOG_TAG, "-> onFolioReaderClosed");
        Intent intent=new Intent(ReadBookAcivity.this,NavigationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onHighlight(HighLight highlight, HighLight.HighLightAction type) {
        Toast.makeText(ReadBookAcivity.this,
                "highlight id = " + highlight.getUUID() + " type = " + type,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveReadPosition(ReadPosition readPosition) {
        Log.i(LOG_TAG, "-> ReadPosition = " + readPosition.toJson());

    }
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
            InputStream is = getApplicationContext().getAssets().open(name);
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
}
