package com.webapps.puzzle;

import library.UserFunctions;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionActivity extends Activity {
	private Question question;
	//private String question;
	protected static boolean checkIn1;
	private RatingBar ratingBar;
	private float givenRating;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		Intent intent = getIntent();
		//question = intent.getStringExtra("question");
	    question = (Question) intent.getSerializableExtra("question");
		
		
		
		TextView emailTextView = (TextView) findViewById(R.id.question);
        emailTextView.setText(question.getContent());
        
        addListenerOnRatingBar();
	}
	
	
	
	private void addListenerOnRatingBar() {
		ratingBar = (RatingBar) findViewById(R.id.qRating);
		
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
	 
				givenRating = rating;
				Toast.makeText(QuestionActivity.this, 
						"You have rated this question " + givenRating + 
						" out of five.", Toast.LENGTH_LONG).show();
	 
			}
		});
		
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch(item.getItemId())
	    {
	    case R.id.logout:
	    	new UserFunctions().logoutUser(this);
	        Intent login = new Intent(this, LoginActivity.class);
	        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        startActivity(login);
	        finish();
	    	return true;
	    case R.id.how_to_play:
	    	Intent howTo = new Intent(getApplicationContext(), HowToPlayActivity.class);
	        startActivity(howTo);
            return true;
	    case R.id.action_settings:
	    	Intent settings = new Intent(getApplicationContext(), Settings.class);
	        startActivity(settings);
            return true;
	    case R.id.about:
	    	Intent about = new Intent(getApplicationContext(), AboutScreen.class);
	        startActivity(about);
            return true;
        default:
        	break;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	// called when a hint button is clicked
	public void launchMaps(View view){
		Intent maps = new Intent(this,MapActivity.class); 
		startActivity(maps);
	}
	
	public void askFriends(View view){
		Intent ask = new Intent(this,AskFriendActivity.class);
		startActivity(ask);
	}
}
