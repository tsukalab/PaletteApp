package com.example.user2013.paletteapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 色を抽出する画像(Bitmap)
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sky);

        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                if  (palette != null) {
                    // VibrantSwatch
                    TextView vibrant_swatch = (TextView)findViewById(R.id.vibrant_swatch);
                    vibrant_swatch.setBackgroundColor(palette.getVibrantSwatch().getRgb());
                    vibrant_swatch.setTextColor(palette.getVibrantSwatch().getTitleTextColor());

                    TextView dark_vibrant_swatch = (TextView)findViewById(R.id.dark_vibrant_swatch);
                    dark_vibrant_swatch.setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());
                    vibrant_swatch.setTextColor(palette.getDarkVibrantSwatch().getTitleTextColor());

                    TextView light_vibrant_swatch = (TextView)findViewById(R.id.light_vibrant_swatch);
                    light_vibrant_swatch.setBackgroundColor(palette.getLightVibrantSwatch().getRgb());
                    light_vibrant_swatch.setTextColor(palette.getLightVibrantSwatch().getTitleTextColor());

                    //MutedSwatch
                    TextView muted_swatch = (TextView)findViewById(R.id.muted_swatch);
                    muted_swatch.setBackgroundColor(palette.getMutedSwatch().getRgb());
                    muted_swatch.setTextColor(palette.getMutedSwatch().getTitleTextColor());

                    TextView dark_muted_swatch = (TextView)findViewById(R.id.dark_muted_swatch);
                    dark_muted_swatch.setBackgroundColor(palette.getDarkMutedSwatch().getRgb());
                    dark_muted_swatch.setTextColor(palette.getDarkMutedSwatch().getTitleTextColor());

                    TextView light_muted_swatch = (TextView)findViewById(R.id.light_muted_swatch);
                    light_muted_swatch.setBackgroundColor(palette.getLightMutedSwatch().getRgb());
                    light_muted_swatch.setTextColor(palette.getLightMutedSwatch().getTitleTextColor());

                }
            }
        });
    }
}
