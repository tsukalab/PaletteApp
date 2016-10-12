package com.example.user2013.paletteapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.IOException;

public class MainActivity extends ActionBarActivity {

    // 画像を読み込むための変数
    private static final int RESULT_PICK_IMAGEFILE = 1001;
    private ImageView imageView;
    private Button button;
    private TextView dcimPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 参照画像の選択
        dcimPath = (TextView)findViewById(R.id.text_view);
        dcimPath.setText("ギャラリーのPath:　"+getGalleryPath()); // ギャラリーのパスを取得する
        imageView = (ImageView)findViewById(R.id.image_view);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file browser.
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

                // Filter to only show results that can be "opened", such as a
                // file (as opposed to a list of contacts or timezones)
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                // Filter to show only images, using the image MIME data type.
                // it would be "*/*".
                intent.setType("image/*");

                startActivityForResult(intent, RESULT_PICK_IMAGEFILE);
            }
        });
    }

    private String getGalleryPath(){
        return Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("", "Uri: " + uri.toString());

                try {
                    Bitmap bmp = getBitmapFromUri(uri);
                    imageView.setImageBitmap(bmp); // 画像の表示
                    // Paletteで色を抽出
                    // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sky); // 色を抽出する画像(Bitmap)
                    Palette.generateAsync(bmp, new Palette.PaletteAsyncListener() {
                        public void onGenerated(Palette palette) {
                            if  (palette != null) {
                                // VibrantSwatch
                                TextView vibrant_swatch = (TextView)findViewById(R.id.vibrant_swatch);
                                vibrant_swatch.setBackgroundColor(palette.getVibrantSwatch().getRgb());
                                vibrant_swatch.setTextColor(palette.getVibrantSwatch().getTitleTextColor());

                                TextView dark_vibrant_swatch = (TextView)findViewById(R.id.dark_vibrant_swatch);
                                // dark_vibrant_swatch.setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());
                                // vibrant_swatch.setTextColor(palette.getDarkVibrantSwatch().getTitleTextColor());

                                TextView light_vibrant_swatch = (TextView)findViewById(R.id.light_vibrant_swatch);
//                                light_vibrant_swatch.setBackgroundColor(palette.getLightVibrantSwatch().getRgb());
//                                light_vibrant_swatch.setTextColor(palette.getLightVibrantSwatch().getTitleTextColor());

                                //MutedSwatch
                                TextView muted_swatch = (TextView)findViewById(R.id.muted_swatch);
//                                muted_swatch.setBackgroundColor(palette.getMutedSwatch().getRgb());
//                                muted_swatch.setTextColor(palette.getMutedSwatch().getTitleTextColor());

                                TextView dark_muted_swatch = (TextView)findViewById(R.id.dark_muted_swatch);
//                                dark_muted_swatch.setBackgroundColor(palette.getDarkMutedSwatch().getRgb());
//                                dark_muted_swatch.setTextColor(palette.getDarkMutedSwatch().getTitleTextColor());

                                TextView light_muted_swatch = (TextView)findViewById(R.id.light_muted_swatch);
//                                light_muted_swatch.setBackgroundColor(palette.getLightMutedSwatch().getRgb());
//                                light_muted_swatch.setTextColor(palette.getLightMutedSwatch().getTitleTextColor());
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
}
