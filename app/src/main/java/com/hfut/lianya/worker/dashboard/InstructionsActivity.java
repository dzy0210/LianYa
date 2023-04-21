package com.hfut.lianya.worker.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.hfut.lianya.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class InstructionsActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener {
    int pageNumber = 0;
    PDFView pdfView;
    Context mContext = this;
    private static final String TAG = InstructionsActivity.class.getSimpleName();
    String url = "https://qcscan.tristateww.com/wt/get_pdf/232030643";
    Uri uri = Uri.parse("https://qcscan.tristateww.com/wt/get_pdf/232030643");
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        pdfView = findViewById(R.id.pdfView);
        Intent intent = getIntent();
//        url = intent.getStringExtra("url");
//        uri = Uri.parse(url);
        Log.d("filename", getFileName(uri));
        initToolBar();
        displayFromUri(url);
    }

    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getFileName(uri));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }

    @Override
    public void loadComplete(int nbPages) {
    }


    private void displayFromUri(String url) {
        AsyncTask.execute(() -> {
            try {
                final InputStream input = new URL( url ).openStream();

                runOnUiThread(() -> pdfView.fromStream( input )
                        .defaultPage( 0 )
                        .enableSwipe( true )
                        .swipeHorizontal( true )
                        .pageSnap( true )
                        .autoSpacing( true )
                        .pageFling( true )
                        .enableAnnotationRendering( true )
                        .scrollHandle( new DefaultScrollHandle( mContext ) )
                        .load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @SuppressLint("Range")
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    @Override
    public void onPageError(int page, Throwable t) {
        Log.e(TAG, "Cannot load page " + page);
    }
}