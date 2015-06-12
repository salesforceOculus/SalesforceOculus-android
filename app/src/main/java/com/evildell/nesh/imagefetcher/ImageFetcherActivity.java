package com.evildell.nesh.imagefetcher;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageFetcherActivity extends Activity {

    String markerURL = "https://aruco-generator.herokuapp.com/markers/";

    int markerID = 1009;

    ImageView imgMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fetcher);


        imgMarker = (ImageView) findViewById(R.id.imgv_aruco);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_fetcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void fetchImage(View view) throws IOException {

        new GetImageTask().execute(markerURL + markerID + ".jpg");

        Toast.makeText(this, "Fetching image from website...", Toast.LENGTH_LONG).show();


    }

    Bitmap getBitmapFromBase64URL(String base64){
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    class GetImageTask extends AsyncTask<String, String, Drawable> {

        @Override
        protected Drawable doInBackground(String... uri) {

            return LoadImageFromWebOperations(uri[0]);
        }

        @Override
        protected void onPostExecute(Drawable markerImg) {

            super.onPostExecute(markerImg);

            imgMarker.setImageDrawable(markerImg);


        }
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}

