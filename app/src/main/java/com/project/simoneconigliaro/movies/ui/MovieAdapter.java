package com.project.simoneconigliaro.movies.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.project.simoneconigliaro.movies.R;
import com.project.simoneconigliaro.movies.models.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> implements Filterable {

    private RequestManager requestManager;

    private List<Movie> listMovies;
    private List<Movie> listMoviesFiltered;

    @Inject
    public MovieAdapter(RequestManager requestManager) {
        this.requestManager = requestManager;
        listMovies = new ArrayList<>();
        listMoviesFiltered = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        String poster = listMoviesFiltered.get(position).getPoster();
        String genre = listMoviesFiltered.get(position).getGenre();

        requestManager
                .load(poster)
                .into(holder.posterImageView);

        holder.genreTextView.setText(genre);
    }

    @Override
    public int getItemCount() {
        return listMoviesFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listMoviesFiltered = listMovies;
                } else {
                    List<Movie> filteredList = new ArrayList<>();
                    for (Movie movie : listMovies) {

                        if (movie.getTitle().toLowerCase().contains(charString.toLowerCase())
                                || movie.getGenre().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(movie);
                        }
                    }
                    listMoviesFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listMoviesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listMoviesFiltered = (ArrayList<Movie>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView posterImageView;
        private TextView genreTextView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.iv_poster);
            genreTextView = itemView.findViewById(R.id.tv_genre);
        }
    }

    public void setListMovies(List<Movie> listMovies) {
        this.listMovies = listMovies;
        this.listMoviesFiltered = listMovies;
        notifyDataSetChanged();
    }
}

