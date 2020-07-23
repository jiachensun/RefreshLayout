package com.sjc.mytestrefresh.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.sjc.myrefresh.BaseRefreshManager;
import com.sjc.myrefresh.DefaultRefreshManager;
import com.sjc.myrefresh.IRefreshListener;
import com.sjc.myrefresh.MyRefreshLayout;
import com.sjc.mytestrefresh.MyApp;
import com.sjc.mytestrefresh.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private MyRefreshLayout refreshLayout;
    private DefaultRefreshManager defaultRefreshManager;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        initRefreshView(root);
        final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    private void initRefreshView(View view) {
        refreshLayout = view.findViewById(R.id.refresh_view);
        defaultRefreshManager = refreshLayout.getDefaultRefreshManager();
        refreshLayout.setRefreshListener(new IRefreshListener() {
            @Override
            public void onRefreshing() {
                delayResult();
            }
        });
    }

    private void delayResult() {
        MyApp.getInstance().getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                defaultRefreshManager.onRefreshSucceed();
//                defaultRefreshManager.onRefreshFailed();
                delayRestore();
            }
        }, 1000);
    }

    private void delayRestore() {
        MyApp.getInstance().getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.restoreRefreshHeader();
            }
        }, 500);
    }
}