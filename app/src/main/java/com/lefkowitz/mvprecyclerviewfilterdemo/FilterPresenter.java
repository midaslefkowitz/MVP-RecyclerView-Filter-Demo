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

    @Override
    public void start() {
        _fullWordList.addAll(_data.getWordsList());
        _wordsToDisplay.addAll(_fullWordList);
        _view.itemsLoaded();
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
        final ArrayList<String> filteredList = new ArrayList<>();
        for (int i = 0, len = _fullWordList.size(); i < len; i++) {
            String word = _fullWordList.get(i);
            final String text = word.toLowerCase();
            if (text.contains(query)) {
                filteredList.add(word);
            }
        }
        filterTo(filteredList);
    }

    private void filterTo(ArrayList<String> words) {
        applyRemovals(words);
        applyAdditions(words);
        applyMovedItems(words);
    }

    private void applyRemovals(ArrayList<String> newItems) {
        for (int i = _wordsToDisplay.size() - 1; i >= 0; i--) {
            String word = _wordsToDisplay.get(i);
            if (!newItems.contains(word)) {
                removeItem(i);
            }
        }
    }

    private void applyAdditions(ArrayList<String> newItems) {
        for (int i = 0, count = newItems.size(); i < count; i++) {
            String word = newItems.get(i);
            if (!_wordsToDisplay.contains(word)) {
                addItem(i, word);
            }
        }
    }

    private void applyMovedItems(ArrayList<String> newItems) {
        for (int toPosition = newItems.size() - 1; toPosition >= 0; toPosition--) {
            String word = newItems.get(toPosition);
            int fromPosition = _wordsToDisplay.indexOf(word);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    private String removeItem(int position) {
        String word = _wordsToDisplay.remove(position);
        _view.itemRemovedAt(position);
        return word;
    }

    private void addItem(int position, String word) {
        position = Math.min(position, _wordsToDisplay.size() - 1);
        _wordsToDisplay.add(position, word);
        _view.itemAddedAt(position);
    }

    private void moveItem(int fromPosition, int toPosition) {
        toPosition = Math.min(toPosition, _wordsToDisplay.size() - 1);
        String word = _wordsToDisplay.remove(fromPosition);
        _wordsToDisplay.add(toPosition, word);
        _view.itemMoved(fromPosition, toPosition);
    }

    public FilterPresenter(FilterContract.View view, DataManager data) {
        _view = view;
        _data = data;
    }
}
