package org.anddev.andengine.opengl.texture.atlas.bitmap;

import java.util.ArrayList;

import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.TextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.atlas.source.ITextureAtlasSource;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.opengl.util.GLState;
import org.anddev.andengine.util.exception.NullBitmapException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.opengl.GLES20;
import android.opengl.GLUtils;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 14:55:02 - 08.03.2010
 */
public class BitmapTextureAtlas extends TextureAtlas<IBitmapTextureAtlasSource> {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final BitmapTextureFormat mBitmapTextureFormat;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Uses {@link BitmapTextureFormat#RGBA_8888}.
	 */
	public BitmapTextureAtlas(final int pWidth, final int pHeight) {
		this(pWidth, pHeight, BitmapTextureFormat.RGBA_8888);
	}

	/**
	 * @param pBitmapTextureFormat use {@link BitmapTextureFormat#RGBA_8888} for {@link BitmapTextureAtlas}MAGIC_CONSTANT with transparency and {@link BitmapTextureFormat#RGB_565} for {@link BitmapTextureAtlas}MAGIC_CONSTANT without transparency.
	 */
	public BitmapTextureAtlas(final int pWidth, final int pHeight, final BitmapTextureFormat pBitmapTextureFormat) {
		this(pWidth, pHeight, pBitmapTextureFormat, TextureOptions.DEFAULT, null);
	}

	/**
	 * Uses {@link BitmapTextureFormat#RGBA_8888}.
	 *
	 * @param pTextureStateListener to be informed when this {@link BitmapTextureAtlas} is loaded, unloaded or a {@link ITextureAtlasSource} failed to load.
	 */
	public BitmapTextureAtlas(final int pWidth, final int pHeight, final ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureAtlasStateListener) {
		this(pWidth, pHeight, BitmapTextureFormat.RGBA_8888, TextureOptions.DEFAULT, pTextureAtlasStateListener);
	}

	/**
	 * @param pBitmapTextureFormat use {@link BitmapTextureFormat#RGBA_8888} for {@link BitmapTextureAtlas}MAGIC_CONSTANT with transparency and {@link BitmapTextureFormat#RGB_565} for {@link BitmapTextureAtlas}MAGIC_CONSTANT without transparency.
	 * @param pTextureAtlasStateListener to be informed when this {@link BitmapTextureAtlas} is loaded, unloaded or a {@link ITextureAtlasSource} failed to load.
	 */
	public BitmapTextureAtlas(final int pWidth, final int pHeight, final BitmapTextureFormat pBitmapTextureFormat, final ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureAtlasStateListener) {
		this(pWidth, pHeight, pBitmapTextureFormat, TextureOptions.DEFAULT, pTextureAtlasStateListener);
	}

	/**
	 * Uses {@link BitmapTextureFormat#RGBA_8888}.
	 *
	 * @param pTextureOptions the (quality) settings of the BitmapTexture.
	 */
	public BitmapTextureAtlas(final int pWidth, final int pHeight, final TextureOptions pTextureOptions) throws IllegalArgumentException {
		this(pWidth, pHeight, BitmapTextureFormat.RGBA_8888, pTextureOptions, null);
	}

	/**
	 * @param pBitmapTextureFormat use {@link BitmapTextureFormat#RGBA_8888} for {@link BitmapTextureAtlas}MAGIC_CONSTANT with transparency and {@link BitmapTextureFormat#RGB_565} for {@link BitmapTextureAtlas}MAGIC_CONSTANT without transparency.
	 * @param pTextureOptions the (quality) settings of the BitmapTexture.
	 */
	public BitmapTextureAtlas(final int pWidth, final int pHeight, final BitmapTextureFormat pBitmapTextureFormat, final TextureOptions pTextureOptions) throws IllegalArgumentException {
		this(pWidth, pHeight, pBitmapTextureFormat, pTextureOptions, null);
	}

	/**
	 * Uses {@link BitmapTextureFormat#RGBA_8888}.
	 *
	 * @param pTextureOptions the (quality) settings of the BitmapTexture.
	 * @param pTextureAtlasStateListener to be informed when this {@link BitmapTextureAtlas} is loaded, unloaded or a {@link ITextureAtlasSource} failed to load.
	 */
	public BitmapTextureAtlas(final int pWidth, final int pHeight, final TextureOptions pTextureOptions, final ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureAtlasStateListener) throws IllegalArgumentException {
		this(pWidth, pHeight, BitmapTextureFormat.RGBA_8888, pTextureOptions, pTextureAtlasStateListener);
	}

	/**
	 * @param pBitmapTextureFormat use {@link BitmapTextureFormat#RGBA_8888} for {@link BitmapTextureAtlas}MAGIC_CONSTANT with transparency and {@link BitmapTextureFormat#RGB_565} for {@link BitmapTextureAtlas}MAGIC_CONSTANT without transparency.
	 * @param pTextureOptions the (quality) settings of the BitmapTexture.
	 * @param pTextureAtlasStateListener to be informed when this {@link BitmapTextureAtlas} is loaded, unloaded or a {@link ITextureAtlasSource} failed to load.
	 */
	public BitmapTextureAtlas(final int pWidth, final int pHeight, final BitmapTextureFormat pBitmapTextureFormat, final TextureOptions pTextureOptions, final ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureAtlasStateListener) throws IllegalArgumentException {
		super(pWidth, pHeight, pBitmapTextureFormat.getPixelFormat(), pTextureOptions, pTextureAtlasStateListener);

		this.mBitmapTextureFormat = pBitmapTextureFormat;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public BitmapTextureFormat getBitmapTextureFormat() {
		return this.mBitmapTextureFormat;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public BitmapTextureAtlas load() {
		super.load();

		return this;
	}

	@Override
	public BitmapTextureAtlas unload() {
		super.unload();

		return this;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	@Override
	protected void writeTextureToHardware() {
		final PixelFormat pixelFormat = this.mBitmapTextureFormat.getPixelFormat();
		final int glFormat = pixelFormat.getGLFormat();
		final int glType = pixelFormat.getGLType();
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, glFormat, this.mWidth, this.mHeight, 0, glFormat, glType, null);

		final Config bitmapConfig = this.mBitmapTextureFormat.getBitmapConfig();
		final boolean preMultipyAlpha = this.mTextureOptions.mPreMultipyAlpha;

		final ArrayList<IBitmapTextureAtlasSource> textureSources = this.mTextureAtlasSources;
		final int textureSourceCount = textureSources.size();

		// TODO What about GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, 1); in here?

		final ITextureAtlasStateListener<IBitmapTextureAtlasSource> textureStateListener = this.getTextureStateListener();
		for(int j = 0; j < textureSourceCount; j++) {
			final IBitmapTextureAtlasSource bitmapTextureAtlasSource = textureSources.get(j);
			if(bitmapTextureAtlasSource != null) {
				final Bitmap bitmap = bitmapTextureAtlasSource.onLoadBitmap(bitmapConfig);
				try {
					if(bitmap == null) {
						throw new NullBitmapException("Caused by: " + bitmapTextureAtlasSource.toString() + " --> " + bitmapTextureAtlasSource.toString() + " returned a null Bitmap.");
					}

					if(preMultipyAlpha) {
						GLUtils.texSubImage2D(GLES20.GL_TEXTURE_2D, 0, bitmapTextureAtlasSource.getTexturePositionX(), bitmapTextureAtlasSource.getTexturePositionY(), bitmap, glFormat, glType);
					} else {
						GLState.glTexSubImage2D(GLES20.GL_TEXTURE_2D, 0, bitmapTextureAtlasSource.getTexturePositionX(), bitmapTextureAtlasSource.getTexturePositionY(), bitmap, this.mPixelFormat);
					}

					bitmap.recycle();

					if(textureStateListener != null) {
						textureStateListener.onTextureAtlasSourceLoaded(this, bitmapTextureAtlasSource);
					}
				} catch (final NullBitmapException e) {
					// TODO Load some static checkerboard or so to visualize that loading the texture has failed.
					//private Buffer createImage(final int width, final int height) {
					//	final int stride = 3 * width;
					//	final ByteBuffer image = ByteBuffer.allocateDirect(height * stride).order(ByteOrder.nativeOrder());
					//
					//	// Fill with a pretty "munching squares" pattern:
					//	for (int t = 0; t < height; t++) {
					//		final byte red = (byte) (255 - 2 * t);
					//		final byte green = (byte) (2 * t);
					//		final byte blue = 0;
					//		for (int x = 0; x < width; x++) {
					//			final int y = x ^ t;
					//			image.position(stride * y + x * 3);
					//			image.put(red);
					//			image.put(green);
					//			image.put(blue);
					//		}
					//	}
					//	image.position(0);
					//	return image;
					//}

					if(textureStateListener != null) {
						textureStateListener.onTextureAtlasSourceLoadExeption(this, bitmapTextureAtlasSource, e);
					} else {
						throw e;
					}
				}
			}
		}
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
