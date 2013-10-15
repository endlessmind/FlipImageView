package com.example.flipimageview;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class FlipImageView extends RelativeLayout {
	
	ImageView img1;
	ImageView img2;
	
	ImageView img3;
	ImageView img4;
	
	LinearLayout Picture1;
	LinearLayout Picture2;
	RelativeLayout root;
	Context c;
	int height, width;
	private String TAG = "CoverFlipImageView";
	Animation flip_open;
	Animation hyperspaceJump;
	boolean Anim_1_cancel = false;
	boolean Anim_2_cancel = false;
	
	public FlipImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.c = context;
	}
	
	public FlipImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.c = context;
	}

	public FlipImageView(Context context) {
		super(context);
		this.c = context;
	}
	
	public void setView(Context c) {
		this.c = c;
		height = this.getHeight();
		width = this.getWidth();
			
		root = new RelativeLayout(c);
		this.addView(root);
		
		Picture1 = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.image_flip_layout_frist, null);
		//Log.e(TAG, "This: " + this.height + " Pic1: " + Picture1.getHeight());
		root.addView(Picture1);
		Picture1.getLayoutParams().width = LayoutParams.FILL_PARENT;
		Picture1.getLayoutParams().height = LayoutParams.FILL_PARENT;
		
		img1 = (ImageView) Picture1.findViewById(R.id.img1);
		img2 = (ImageView) Picture1.findViewById(R.id.img2);

		
		Bitmap noCoverImage = ((BitmapDrawable)c.getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
		Bitmap bm1 = Bitmap.createBitmap(noCoverImage, 0, 0, noCoverImage.getWidth(), (noCoverImage.getHeight() / 2));
		Bitmap bm2 = Bitmap.createBitmap(noCoverImage, 0, (noCoverImage.getHeight() / 2), noCoverImage.getWidth(), (noCoverImage.getHeight() / 2));
	
		Picture2 = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.image_flip_layout_next, null);
		root.addView(Picture2);
		Picture2.getLayoutParams().width = LayoutParams.FILL_PARENT;
		Picture2.getLayoutParams().height = LayoutParams.FILL_PARENT;
		
		img3 = (ImageView) Picture2.findViewById(R.id.img3);
		img3.setImageBitmap(bm1);
		img4 = (ImageView) Picture2.findViewById(R.id.img4);
		img4.setImageBitmap(bm2);

		//root.getChildAt(0).bringToFront();
		
		Log.e(TAG, "Childs: " + root.getChildCount());
		
	}
	
	public void setImageBitmap(Bitmap bmp) {
		if (bmp == null) {
			return;
		}
		final Bitmap bm1 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), (bmp.getHeight() / 2));
		final Bitmap bm2 = Bitmap.createBitmap(bmp, 0, (bmp.getHeight() / 2), bmp.getWidth(), (bmp.getHeight() / 2));
		
		
		LinearLayout Layout = (LinearLayout) root.getChildAt(0);
		Layout.setTag("Ligger");
		ImageView TopImage = (ImageView) Layout.getChildAt(0);
		final ImageView BottomImage = (ImageView) Layout.getChildAt(1);
		TopImage.setImageBitmap(bm1);
		//BottomImage.setImageBitmap(bm2);
		Layout.setVisibility(View.VISIBLE);
		
		final LinearLayout OldLayout = (LinearLayout)root.getChildAt(1);
		OldLayout.setTag("Fuck");
		final ImageView FlipImage = (ImageView) OldLayout.getChildAt(0);
		final ImageView TempImage = (ImageView) OldLayout.getChildAt(1);
		OldLayout.setVisibility(View.VISIBLE);
		Drawable b = TempImage.getDrawable();
		BottomImage.setImageDrawable(b);
		if (hyperspaceJump != null || (hyperspaceJump != null && !hyperspaceJump.hasEnded()) ) {
			Anim_1_cancel = true;
			hyperspaceJump.cancel();
		}
		hyperspaceJump = AnimationUtils.loadAnimation(this.c, R.anim.anim_flip_cover);
		hyperspaceJump.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation animation) {
				hyperspaceJump = null;
				if (!Anim_1_cancel) {
				Log.e(TAG, "Animation 1 done!");
			//	root.getChildAt(0).bringToFront();
				//((LinearLayout)CoverFlipImageView.this.getChildAt(1)).getChildAt(0).bringToFront();
			//	Log.e(TAG, (String)CoverFlipImageView.this.getChildAt(1).getTag());
				if (flip_open != null || (flip_open != null && !flip_open.hasEnded()) ){
					Anim_2_cancel = true;
					flip_open.cancel();
				}
				flip_open = AnimationUtils.loadAnimation(FlipImageView.this.c, R.anim.anim_flip_bottom);
				flip_open.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationEnd(Animation animation) {
						flip_open = null;
						if (!Anim_2_cancel) {
							Log.e(TAG, "Animation 2 done!");
							BottomImage.setImageBitmap(bm2);
							FlipImage.clearAnimation();
							root.getChildAt(0).bringToFront();
					//		LinearLayout Layout = (LinearLayout) root.getChildAt(0);
							OldLayout.setVisibility(View.INVISIBLE);
					//		TempImage.clearAnimation();
						} else {
							Anim_2_cancel = false;
						}
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
					}

					@Override
					public void onAnimationStart(Animation animation) {
						TempImage.setImageBitmap(bm2);
					}
					
				});
				TempImage.startAnimation(flip_open);
				
				} else {
					Anim_1_cancel = false;
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}
			
		});
		FlipImage.startAnimation(hyperspaceJump);
		
	}
	

}
