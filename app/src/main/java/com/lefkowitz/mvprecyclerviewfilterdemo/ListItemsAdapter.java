package com.lefkowitz.mvprecyclerviewfilterdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yitz on 8/4/2017.
 */

public class ListItemsAdapter extends RecyclerView.Adapter<ListItemsAdapter.ItemViewHolder> {

    private FilterContract.Presenter _presenter;

    public ListItemsAdapter(FilterContract.Presenter presenter) {
        _presenter = presenter;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ItemViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(_presenter.getItemAt(position));
    }

    @Override
    public int getItemCount() {
        return _presenter.getItemsCount();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView _itemTV;

        public ItemViewHolder(View itemView) {
            super(itemView);
            _itemTV = (TextView) itemView;
        }

        public void bind(String item) {
            _itemTV.setText(item);
        }
    }
}
