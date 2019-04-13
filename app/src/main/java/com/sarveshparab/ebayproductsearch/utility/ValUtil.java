package com.sarveshparab.ebayproductsearch.utility;

import android.content.Context;

public class ValUtil {

    public static final int INITIATE_ZIP_AUTOCOMPLETE = 100;
    public static final long ZIP_AUTOCOMPLETE_DELAY = 300;

    public static final int ZIP_THRESHOLD = 2;

    public static final int TITLE_LENGTH_LIMIT = 39;
    public static final int HOR_SCROLL_VIEW_IMG_MARGIN_START = 5;
    public static final int HOR_SCROLL_VIEW_IMG_MARGIN_END = 5;

    public static int ITEM_DETAIL_OFF_SCREEN_PAGE_LIMIT = 3;

    public static int getDPVal(int dp, Context ctx){
        return ((int)(dp * ctx.getResources().getDisplayMetrics().density));
    }
}
