package com.example.flipimageview;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	ArrayList<Bitmap> images = new ArrayList<Bitmap>();
	int count = 0;
	FlipImageView flip;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		images.add(((BitmapDrawable)getResources().getDrawable(R.drawable.dropbox_square_black)).getBitmap());	
		images.add(((BitmapDrawable)getResources().getDrawable(R.drawable.facebook_square)).getBitmap());
		images.add(((BitmapDrawable)getResources().getDrawable(R.drawable.flickr_square)).getBitmap());
		images.add(((BitmapDrawable)getResources().getDrawable(R.drawable.github_square_black)).getBitmap());
		images.add(((BitmapDrawable)getResources().getDrawable(R.drawable.google_square)).getBitmap());
		images.add(((BitmapDrawable)getResources().getDrawable(R.drawable.instagram_square_color)).getBitmap());
		images.add(((BitmapDrawable)getResources().getDrawable(R.drawable.twitter_square)).getBitmap());
		images.add(((BitmapDrawable)getResources().getDrawable(R.drawable.youtube_square_color)).getBitmap());
		flip = (FlipImageView) findViewById(R.id.FlipImageView1);
		//Don't forget to call this!
		flip.setView(this);
		flip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				flip.setImageBitmap(images.get(count));
				if (count < images.size() -1) {
					count++;
				} else {
					count = 0;
				}
			}
			
		});
	}



}
