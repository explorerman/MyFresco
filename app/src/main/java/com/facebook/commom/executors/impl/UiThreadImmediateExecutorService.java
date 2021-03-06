package com.facebook.commom.executors.impl;

/**
 * Created by heshixiyang on 2017/3/12.
 */

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;

/**
 * 将事件提交到主线程运行
 * An {@link ExecutorService} that is backed by the application's main looper.
 *
 * <p/> If the execute is called from the thread of the application's main looper,
 * it will be executed synchronously.
 */
public class UiThreadImmediateExecutorService extends HandlerExecutorServiceImpl {
    private static UiThreadImmediateExecutorService sInstance = null;

    private UiThreadImmediateExecutorService() {
        super(new Handler(Looper.getMainLooper()));
    }

    public static UiThreadImmediateExecutorService getInstance() {
        if (sInstance == null) {
            sInstance = new UiThreadImmediateExecutorService();
        }
        return sInstance;
    }

    @Override
    public void execute(Runnable command) {
        if (isHandlerThread()) {
            command.run();
        } else {
            super.execute(command);
        }
    }
}
