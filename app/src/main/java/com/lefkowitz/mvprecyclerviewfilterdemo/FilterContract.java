package com.lefkowitz.mvprecyclerviewfilterdemo;

import java.util.ArrayList;

/**
 * Created by yitz on 8/4/2017.
 */

public interface FilterContract {

    interface View {
        void initialWordLoad();

        void showProgress(boolean show);

        void updateItems();
    }

    interface Presenter {
        void start();

        String getItemAt(int position);

        String getPendingItemAt(int position);

        void onListUpdated();

        int getItemsCount();

        int getPendingItemsCount();

        void filter(String query);
    }
}
