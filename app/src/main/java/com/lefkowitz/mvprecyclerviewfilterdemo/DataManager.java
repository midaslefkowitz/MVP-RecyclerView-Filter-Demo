package com.lefkowitz.mvprecyclerviewfilterdemo;

import android.content.res.Resources;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yitz on 8/4/2017.
 */

public class DataManager {

    private Resources _resources;

    public List<String> getWordsList() {
        String[] wordsArray = _resources.getStringArray(R.array.words);
        return Arrays.asList(wordsArray);
    }

    public DataManager(Resources resources) {
        _resources = resources;
    }
}
