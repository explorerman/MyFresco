package com.facebook.animated.webp;

/**
 * Created by Administrator on 2017/3/11 0011.
 */

import com.facebook.commom.internal.DoNotStrip;
import com.facebook.commom.internal.Preconditions;
import com.facebook.imagepipeline.animated.base.AnimatedImage;
import com.facebook.imagepipeline.animated.base.impl.AnimatedDrawableFrameInfo;
import com.facebook.imagepipeline.animated.factory.AnimatedImageDecoder;

import java.nio.ByteBuffer;

import javax.annotation.concurrent.ThreadSafe;

import static com.facebook.imagepipeline.nativecode.StaticWebpNativeLoader.ensure;

/**
 * A representation of a WebP image. An instance of this class will hold a copy of the encoded
 * data in memory along with the parsed header data. Frames are decoded on demand via
 * {@link WebPFrame}.
 */
@ThreadSafe
@DoNotStrip
public class WebPImage implements AnimatedImage, AnimatedImageDecoder {

    // Accessed by native methods
    @SuppressWarnings("unused")
    @DoNotStrip
    private long mNativeContext;

    @DoNotStrip
    public WebPImage() {
    }

    /**
     * Constructs the image with the native pointer. This is called by native code.
     *
     * @param nativeContext the native pointer
     */
    @DoNotStrip
    WebPImage(long nativeContext) {
        mNativeContext = nativeContext;
    }

    @Override
    protected void finalize() {
        nativeFinalize();
    }

    @Override
    public void dispose() {
        nativeDispose();
    }

    /**
     * Creates a {@link WebPImage} from the specified encoded data. This will throw if it fails
     * to create. This is meant to be called on a worker thread.
     *
     * @param source the data to the image (a copy will be made)
     */
    public static WebPImage create(byte[] source) {
        ensure();
        Preconditions.checkNotNull(source);

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(source.length);
        byteBuffer.put(source);
        byteBuffer.rewind();

        return nativeCreateFromDirectByteBuffer(byteBuffer);
    }

    public static WebPImage create(long nativePtr, int sizeInBytes) {
        ensure();
        Preconditions.checkArgument(nativePtr != 0);
        return nativeCreateFromNativeMemory(nativePtr, sizeInBytes);
    }

    @Override
    public AnimatedImage decode(long nativePtr, int sizeInBytes) {
        return WebPImage.create(nativePtr, sizeInBytes);
    }

    @Override
    public int getWidth() {
        return nativeGetWidth();
    }

    @Override
    public int getHeight() {
        return nativeGetHeight();
    }

    @Override
    public int getFrameCount() {
        return nativeGetFrameCount();
    }

    @Override
    public int getDuration() {
        return nativeGetDuration();
    }

    @Override
    public int[] getFrameDurations() {
        return nativeGetFrameDurations();
    }

    @Override
    public int getLoopCount() {
        return nativeGetLoopCount();
    }

    @Override
    public WebPFrame getFrame(int frameNumber) {
        return nativeGetFrame(frameNumber);
    }

    @Override
    public int getSizeInBytes() {
        return nativeGetSizeInBytes();
    }

    @Override
    public boolean doesRenderSupportScaling() {
        return true;
    }

    @Override
    public AnimatedDrawableFrameInfo getFrameInfo(int frameNumber) {
        WebPFrame frame = getFrame(frameNumber);
        try {
            return new AnimatedDrawableFrameInfo(
                    frameNumber,
                    frame.getXOffset(),
                    frame.getYOffset(),
                    frame.getWidth(),
                    frame.getHeight(),
                    frame.isBlendWithPreviousFrame() ?
                            AnimatedDrawableFrameInfo.BlendOperation.BLEND_WITH_PREVIOUS :
                            AnimatedDrawableFrameInfo.BlendOperation.NO_BLEND,
                    frame.shouldDisposeToBackgroundColor() ?
                            AnimatedDrawableFrameInfo.DisposalMethod.DISPOSE_TO_BACKGROUND :
                            AnimatedDrawableFrameInfo.DisposalMethod.DISPOSE_DO_NOT);
        } finally {
            frame.dispose();
        }
    }

    private static native WebPImage nativeCreateFromDirectByteBuffer(ByteBuffer buffer);
    private static native WebPImage nativeCreateFromNativeMemory(long nativePtr, int sizeInBytes);
    private native int nativeGetWidth();
    private native int nativeGetHeight();
    private native int nativeGetDuration();
    private native int nativeGetFrameCount();
    private native int[] nativeGetFrameDurations();
    private native int nativeGetLoopCount();
    private native WebPFrame nativeGetFrame(int frameNumber);
    private native int nativeGetSizeInBytes();
    private native void nativeDispose();
    private native void nativeFinalize();
}