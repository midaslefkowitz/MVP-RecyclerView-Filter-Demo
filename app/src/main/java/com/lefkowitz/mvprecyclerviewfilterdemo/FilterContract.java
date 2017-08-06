package com.lefkowitz.mvprecyclerviewfilterdemo;

import java.util.ArrayList;

/**
 * Created by yitz on 8/4/2017.
 */

public interface FilterContract {

    interface View {
        void initialWordLoad();

        void showProgress(boolean show);

        void updateItems(ArrayList<String> oldList);
    }

    interface Presenter {
        void start();

        String getItemAt(int position);

        void beforeListUpdated(ArrayList<String> newItemsToDisplay);

        void onListUpdated(ArrayList<String> newItemsToDisplay);

        int getItemsCount();

        void filter(String query);
    }
}
