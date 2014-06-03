package com.webapps.puzzle;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Settings extends PreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   
	   PrefFragment prefFragment = new PrefFragment();
	   FragmentManager fragmentManager = getFragmentManager();
	   FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	   fragmentTransaction.replace(android.R.id.content, prefFragment);
	   fragmentTransaction.commit();
	
	}
}
