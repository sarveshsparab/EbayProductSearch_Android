package com.sarveshparab.ebayproductsearch.utility;

import android.content.Context;

public class ValUtil {

    public static final int INITIATE_ZIP_AUTOCOMPLETE = 100;
    public static final long ZIP_AUTOCOMPLETE_DELAY = 300;

    public static final int ZIP_THRESHOLD = 2;

    public static final int TITLE_LENGTH_LIMIT = 39;

    public static int getDPVal(int dp, Context ctx){
        return ((int)(dp * ctx.getResources().getDisplayMetrics().density));
    }
}
