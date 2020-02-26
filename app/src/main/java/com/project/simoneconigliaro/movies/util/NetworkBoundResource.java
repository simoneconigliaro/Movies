package com.project.simoneconigliaro.movies.util;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NetworkBoundResource<CacheObject, RequestObject> {

    private final MediatorLiveData<Resource<CacheObject>> result = new MediatorLiveData<>();

    @MainThread
    protected NetworkBoundResource() {
        result.setValue(Resource.loading(null));

        // Always load the data from DB intially so that we have
        LiveData<CacheObject> dbSource = loadFromDb();

        // Fetch the data from network and add it to the resource
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> {
                    if (null != newData)
                        result.setValue(Resource.success(newData));
                });
            }
        });
    }

    /**
     * This method fetches the data from remoted service and save it to local db
     *
     * @param dbSource - Database source
     */
    private void fetchFromNetwork(final LiveData<CacheObject> dbSource) {
        result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData)));
        createCall().enqueue(new Callback<RequestObject>() {
            @Override
            public void onResponse(@NonNull Call<RequestObject> call, @NonNull Response<RequestObject> response) {
                result.removeSource(dbSource);
                saveResultAndReInit(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<RequestObject> call, @NonNull Throwable t) {
                result.removeSource(dbSource);
                result.addSource(dbSource, newData -> result.setValue(Resource.error("Error retrieving data, check your internet connection", newData)));
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    @MainThread
    private void saveResultAndReInit(RequestObject response) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                result.addSource(loadFromDb(), newData -> {
                    if (null != newData)
                        result.setValue(Resource.success(newData));
                });
            }
        }.execute();
    }

    @WorkerThread
    protected abstract void saveCallResult(RequestObject item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable CacheObject data);

    @NonNull
    @MainThread
    protected abstract LiveData<CacheObject> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Call<RequestObject> createCall();

    public final LiveData<Resource<CacheObject>> getAsLiveData() {
        return result;
    }
}