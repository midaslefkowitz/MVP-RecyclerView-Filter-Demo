package com.lefkowitz.mvprecyclerviewfilterdemo;

/**
 * Created by yitz on 8/4/2017.
 */

public interface FilterContract {

    interface View {
        void itemsLoaded();

        void itemRemovedAt(int pos);

        void itemAddedAt(int pos);

        void itemChangedAt(int pos);

        void itemMoved(int from, int to);
    }

    interface Presenter {
        void start();

        String getItemAt(int position);

        int getItemsCount();

        void filter(String query);
    }
}
