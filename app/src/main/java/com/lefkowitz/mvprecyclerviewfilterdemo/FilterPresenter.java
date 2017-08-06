package com.lefkowitz.mvprecyclerviewfilterdemo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * Created by yitz on 8/4/2017.
 */

public class FilterPresenter implements FilterContract.Presenter {

    private FilterContract.View _view;
    private DataManager _data;

    private ArrayList<String> _fullWordList = new ArrayList<>();
    private ArrayList<String> _wordsToDisplay = new ArrayList<>();

    private Deque<ArrayList<String>> _pendingUpdates = new ArrayDeque<>();

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

        _pendingUpdates.push(filteredList);
        if (_pendingUpdates.size() > 1) {
            return;
        }

        updateItems(filteredList);
    }

    @Override
    public void beforeListUpdated(ArrayList<String> newItemsToDisplay) {
        _pendingUpdates.remove(newItemsToDisplay);
    }

    @Override
    public void onListUpdated(ArrayList<String> newItemsToDisplay) {
        _view.showProgress(false);
        _wordsToDisplay.clear();
        _wordsToDisplay.addAll(newItemsToDisplay);
        if (_pendingUpdates.size() > 0) {
            ArrayList<String> latest = _pendingUpdates.pop();
            _pendingUpdates.clear();
            updateItems(latest);
        }
    }

    private void updateItems(ArrayList<String> latest) {
        _view.showProgress(true);
        _view.updateItems(latest);
    }

    public FilterPresenter(FilterContract.View view, DataManager data) {
        _view = view;
        _data = data;
    }
}
