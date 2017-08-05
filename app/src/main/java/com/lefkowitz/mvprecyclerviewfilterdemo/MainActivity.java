package com.lefkowitz.mvprecyclerviewfilterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements FilterContract.View {

    private FilterContract.Presenter _presenter;

    private EditText _filterET;
    private RecyclerView _itemsListRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        _presenter = new FilterPresenter(this, new DataManager(getResources()));

        findViews();

        initViews();

        _presenter.start();
    }

    private void findViews() {
        _filterET = (EditText) findViewById(R.id.activity_main_filterET);
        _itemsListRV = (RecyclerView) findViewById(R.id.activity_main_item_listRV);
    }

    private void initViews() {
        initList();
        initFilterET();
    }

    private void initList() {
        _itemsListRV.setLayoutManager(new LinearLayoutManager(this));
        _itemsListRV.setAdapter(new ListItemsAdapter(_presenter));
    }

    private void initFilterET() {
        _filterET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                _presenter.filter(s.toString());
            }
        });
    }

    @Override
    public void itemsLoaded() {
        _itemsListRV.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void itemRemovedAt(int pos) {
        _itemsListRV.getAdapter().notifyItemRemoved(pos);
    }

    @Override
    public void itemAddedAt(int pos) {
        _itemsListRV.getAdapter().notifyItemInserted(pos);
    }

    @Override
    public void itemChangedAt(int pos) {
        _itemsListRV.getAdapter().notifyItemChanged(pos);
    }

    @Override
    public void itemMoved(int from, int to) {
        _itemsListRV.getAdapter().notifyItemMoved(from, to);
    }
}
