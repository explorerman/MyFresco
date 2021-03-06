package com.facebook.animation.drawable.animator;

/**
 * Created by Administrator on 2017/3/14 0014.
 */

import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.facebook.animation.drawable.AnimatedDrawable2;
import com.facebook.imagepipeline.animated.base.AnimatableDrawable;

import javax.annotation.Nullable;

/**
 * 通过animated drawables来创建一个{@link ValueAnimator}
 * 现在，这个class只支持API 11 和以下
 * Helper class to create {@link ValueAnimator}s for animated drawables.
 * Currently, this class only supports API 11 (Honeycomb) and above.
 *
 * Supported drawable types:
 * - {@link AnimatableDrawable}
 * - {@link AnimatedDrawable2}
 */
public class AnimatedDrawableValueAnimatorHelper {

    /**
     * Create a value animator for the given animation drawable and max animation duration in ms.
     * @param drawable the drawable to create the animator for
     * @param maxDurationMs the max duration in ms
     * @return the animator to use
     */
    @Nullable
    public static ValueAnimator createValueAnimator(Drawable drawable, int maxDurationMs) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return null;
        }

        if (drawable instanceof AnimatableDrawable) {
            return ((AnimatableDrawable) drawable).createValueAnimator(maxDurationMs);
        }

        if (drawable instanceof AnimatedDrawable2) {
            return AnimatedDrawable2ValueAnimatorHelper.createValueAnimator(
                    (AnimatedDrawable2) drawable,
                    maxDurationMs);
        }
        return null;
    }

    /**
     * Create a value animator for the given animation drawable.
     * @param drawable the drawable to create the animator for
     * @return the animator to use
     */
    @Nullable
    public static ValueAnimator createValueAnimator(Drawable drawable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return null;
        }

        if (drawable instanceof AnimatableDrawable) {
            return ((AnimatableDrawable) drawable).createValueAnimator();
        }

        if (drawable instanceof AnimatedDrawable2) {
            return AnimatedDrawable2ValueAnimatorHelper.createValueAnimator((AnimatedDrawable2) drawable);
        }
        return null;
    }
    /**
     * Create an animator update listener to be used to update the drawable to be animated.
     * @param drawable the drawable to create the animator update listener for
     * @return the listener to use
     */
    @Nullable
    public static ValueAnimator.AnimatorUpdateListener createAnimatorUpdateListener(
            final Drawable drawable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return null;
        }

        if (drawable instanceof AnimatableDrawable) {
            return ((AnimatableDrawable) drawable).createAnimatorUpdateListener();
        }

        if (drawable instanceof AnimatedDrawable2) {
            return AnimatedDrawable2ValueAnimatorHelper.createAnimatorUpdateListener(
                    (AnimatedDrawable2) drawable);
        }
        return null;
    }

    private AnimatedDrawableValueAnimatorHelper() {}
}
