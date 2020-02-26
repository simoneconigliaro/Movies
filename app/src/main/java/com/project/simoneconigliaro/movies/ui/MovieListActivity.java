package com.project.simoneconigliaro.movies.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import com.bumptech.glide.RequestManager;
import com.project.simoneconigliaro.movies.R;
import com.project.simoneconigliaro.movies.util.SimpleIdlingResource;
import com.project.simoneconigliaro.movies.viewmodels.MovieListViewModel;
import com.project.simoneconigliaro.movies.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MovieListActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    @Inject
    MovieAdapter movieAdapter;

    private MovieListViewModel viewModel;

    private RecyclerView recyclerView;
    private SearchView searchView;
    private ProgressBar progressBar;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(findViewById(R.id.toolbar));
        setContentView(R.layout.activity_movie_list);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recycler_view);
        searchView = findViewById(R.id.search_view);

        viewModel = new ViewModelProvider(this, providerFactory).get(MovieListViewModel.class);

        initRecyclerView();
        initSearchView();
        subscribeObservers();
    }

    private void subscribeObservers() {

        viewModel.getListMovies().observe(this, listResource -> {
            if (listResource != null) {
                switch (listResource.status) {
                    case LOADING: {
                        showProgressBar(true);
                        setIdleResource(false);
                        break;
                    }
                    case SUCCESS: {
                        showProgressBar(false);
                        movieAdapter.setListMovies(listResource.data);
                        setIdleResource(true);
                        break;
                    }
                    case ERROR: {
                        showProgressBar(false);
                        Toast.makeText(MovieListActivity.this, listResource.message, Toast.LENGTH_SHORT).show();
                        setIdleResource(false);
                    }
                }
            }
        });
    }

    private void initRecyclerView() {
        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setAdapter(movieAdapter);
    }

    private void showProgressBar(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void initSearchView() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void setIdleResource(boolean isIdle) {
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(isIdle);
        }
    }

}
