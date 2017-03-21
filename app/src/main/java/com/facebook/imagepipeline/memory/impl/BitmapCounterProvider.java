package com.facebook.imagepipeline.memory.impl;

/**
 * Created by heshixiyang on 2017/3/17.
 */
//提供一个BitmapCounter
public class BitmapCounterProvider {
    private static final long KB = 1024;
    private static final long MB = 1024 * KB;

    /**
     * Our Bitmaps live in ashmem, meaning that they are pinned in Android's shared native memory.
     *
     * <p> Therefore, we are not constrained by the max heap size of the dalvik heap, but we want to
     * make sure we don't use too much memory on low end devices, so that we don't force other
     * background process to be killed.
     */
    public static final int MAX_BITMAP_TOTAL_SIZE = getMaxSizeHardCap();
    public static final int MAX_BITMAP_COUNT = 384;

    private static BitmapCounter sBitmapCounter;

    private static int getMaxSizeHardCap() {
        final int maxMemory = (int) Math.min(Runtime.getRuntime().maxMemory(), Integer.MAX_VALUE);
        if (maxMemory > 16 * MB) {
            return maxMemory / 4 * 3;
        } else {
            return maxMemory / 2;
        }
    }

    public static BitmapCounter get() {
        if (sBitmapCounter == null) {
            sBitmapCounter = new BitmapCounter(MAX_BITMAP_COUNT, MAX_BITMAP_TOTAL_SIZE);
        }
        return sBitmapCounter;
    }
}
