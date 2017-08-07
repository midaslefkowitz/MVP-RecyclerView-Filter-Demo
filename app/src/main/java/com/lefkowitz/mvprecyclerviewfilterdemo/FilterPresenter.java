package com.lefkowitz.mvprecyclerviewfilterdemo;

import java.util.ArrayList;

/**
 * Created by yitz on 8/4/2017.
 */

public class FilterPresenter implements FilterContract.Presenter {

    private FilterContract.View _view;
    private DataManager _data;

    private ArrayList<String> _fullWordList = new ArrayList<>();
    private ArrayList<String> _wordsToDisplay = new ArrayList<>();

    private ArrayList<ArrayList<String>> _pendingUpdates = new ArrayList<>();

    @Override
    public void start() {
        _fullWordList.addAll(_data.getWordsList());
        _wordsToDisplay.addAll(_data.getWordsList());
        _view.initialWordLoad();
    }

    @Override
    public String getItemAt(int position) {
        return _wordsToDisplay.get(position);
    }

    @Override
    public int getItemsCount() {
        return _wordsToDisplay.size();
    }

    @Override
    public void filter(String query) {
        query = query.toLowerCase();
        ArrayList<String> filteredList = new ArrayList<>();
        for (int i = 0, len = _fullWordList.size(); i < len; i++) {
            String word = _fullWordList.get(i);
            final String text = word.toLowerCase();
            if (text.contains(query)) {
                filteredList.add(word);
            }
        }

        _pendingUpdates.add(filteredList);
        if (_pendingUpdates.size() > 1) {
            return;
        }

        updateItems();
    }

    @Override
    public String getPendingItemAt(int position) {
        return _pendingUpdates.get(0).get(position);
    }

    @Override
    public int getPendingItemsCount() {
        return _pendingUpdates.get(0).size();
    }

    @Override
    public void onListUpdated() {
        _view.showProgress(false);
        _wordsToDisplay.clear();
        _wordsToDisplay.addAll(_pendingUpdates.remove(0));
        if (_pendingUpdates.size() > 0) {
            ArrayList<String> latest = _pendingUpdates.remove(_pendingUpdates.size() - 1);
            _pendingUpdates.clear();
            _pendingUpdates.add(0, latest);
            updateItems();
        }
    }

    private void updateItems() {
        _view.showProgress(true);
        _view.updateItems();
    }

    public FilterPresenter(FilterContract.View view, DataManager data) {
        _view = view;
        _data = data;
    }
}
