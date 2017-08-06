package com.lefkowitz.mvprecyclerviewfilterdemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FilterContract.View {

    private FilterContract.Presenter _presenter;

    private EditText _filterET;
    private RecyclerView _itemsListRV;
    private ProgressBar _progressBar;

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
        _progressBar = (ProgressBar) findViewById(R.id.activity_main_PB);
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
    public void showProgress(boolean show) {
        _progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void initialWordLoad() {
        _itemsListRV.getAdapter().notifyItemRangeInserted(0, _presenter.getItemsCount());
    }

    @Override
    public void updateItems(final ArrayList<String> newList) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final DiffUtil.DiffResult diffResult =
                        DiffUtil.calculateDiff(new MyDiffCallback(_presenter, newList));
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        _presenter.beforeListUpdated(newList);
                        diffResult.dispatchUpdatesTo(_itemsListRV.getAdapter());
                        _presenter.onListUpdated(newList);
                    }
                });
            }
        }).start();
    }

    public static class MyDiffCallback extends DiffUtil.Callback {

        private FilterContract.Presenter _presenter;
        private ArrayList<String> _newList;

        public MyDiffCallback(FilterContract.Presenter presenter, ArrayList<String> newList) {
            _presenter = presenter;
            _newList = newList;
        }

        @Override
        public int getOldListSize() {
            return _presenter.getItemsCount();
        }

        @Override
        public int getNewListSize() {
            return _newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            String oldString = _presenter.getItemAt(oldItemPosition);
            String newString = _newList.get(newItemPosition);
            if (oldString == null || newString == null) {
                return false;
            }
            return oldString.hashCode() == newString.hashCode();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return _presenter.getItemAt(oldItemPosition).equals(_newList.get(newItemPosition));
        }
    }
}
