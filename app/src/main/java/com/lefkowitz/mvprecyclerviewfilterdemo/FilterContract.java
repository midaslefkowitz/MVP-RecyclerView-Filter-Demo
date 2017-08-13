package com.lefkowitz.mvprecyclerviewfilterdemo;

import android.support.v7.util.DiffUtil;

/**
 * Created by yitz on 8/4/2017.
 */

public interface FilterContract {

    interface View {
        void initialWordLoad();

        void showProgress(boolean show);

        void updateItems(DiffUtil.DiffResult diffResult);
    }

    interface Presenter {
        void start();

        String getItemAt(int position);

        String getPendingItemAt(int position);

        int getItemsCount();

        int getPendingItemsCount();

        void filter(String query);
    }
}
