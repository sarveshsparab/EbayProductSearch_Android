package com.sarveshparab.ebayproductsearch.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.pojos.PSForm;
import com.sarveshparab.ebayproductsearch.utility.StrUtil;

public class SearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);


        Bundle data = getIntent().getExtras();
        PSForm psForm = (PSForm) data.getParcelable(StrUtil.PSFORM_PARCEL);

        Log.v(StrUtil.LOG_TAG+"|ForwardACK",psForm.toString());
    }
}
