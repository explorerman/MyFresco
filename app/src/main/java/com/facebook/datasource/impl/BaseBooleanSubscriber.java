package com.facebook.datasource.impl;

/**
 * Created by Administrator on 2017/3/13 0013.
 */

import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;

/**
 * Base implementation of {@link DataSubscriber} that ensures that the data source is closed when
 * the subscriber has finished with it.
 * <p>
 * Sample usage:
 * <pre>
 * <code>
 * imagePipeline.isInDiskCache(
 * uri,
 * new BaseBooleanSubscriber() {
 *   public void onNewResultImpl(boolean isFound) {
 *     // caller's code here
 *   }
 * });
 * </code>
 * </pre>
 */
public abstract class BaseBooleanSubscriber implements DataSubscriber<Boolean> {
    @Override
    public void onNewResult(DataSource<Boolean> dataSource) {
        try {
            onNewResultImpl(dataSource.getResult());
        } finally {
            dataSource.close();
        }
    }

    @Override
    public void onFailure(DataSource<Boolean> dataSource) {
        try {
            onFailureImpl(dataSource);
        } finally {
            dataSource.close();
        }
    }

    @Override
    public void onCancellation(DataSource<Boolean> dataSource) {
    }

    @Override
    public void onProgressUpdate(DataSource<Boolean> dataSource) {
    }

    protected abstract void onNewResultImpl(boolean isFoundInDisk);

    protected abstract void onFailureImpl(DataSource<Boolean> dataSource);
}

