package com.webapps.puzzle;

import library.DatabaseHandler;
import library.UserFunctions;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;

public class LoginTask extends AsyncTask<String, Void, Integer> {

	private ProgressDialog progressDialog;
	private LoginActivity activity;
	private static String KEY_SUCCESS = "success";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";
	private int responseCode = 0;

	public LoginTask(LoginActivity activity, ProgressDialog progressDialog)
	{
		this.activity = activity;
		this.progressDialog = progressDialog;
	}

	@Override
	protected void onPreExecute()
	{
		progressDialog.show();
	}

	protected Integer doInBackground(String... arg0) {
		EditText userName = (EditText)activity.findViewById(R.id.loginEmail);
		EditText passwordEdit = (EditText)activity.findViewById(R.id.loginPassword);
		String email = userName.getText().toString();
		String password = passwordEdit.getText().toString();
		UserFunctions userFunction = new UserFunctions();
		JSONObject json = userFunction.loginUser(email, password);
		
		// check for login response
		try {
			if (json.getString(KEY_SUCCESS) != null) {
				String res = json.getString(KEY_SUCCESS);

				if(Integer.parseInt(res) == 1){
					//user successfully logged in
					// Store user details in SQLite Database
					DatabaseHandler db = new DatabaseHandler(activity.getApplicationContext());
					JSONObject json_user = json.getJSONObject("user");
					//Log.v("name", json_user.getString(KEY_NAME));
					// Clear all previous data in database
					userFunction.logoutUser(activity.getApplicationContext());
					db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), 
							json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));                        

					responseCode = 1;
					// Close Login Screen
					//finish();

				}else{
					responseCode = 0;
					// Error in login
				}
			}

		} catch (NullPointerException e) {
			e.printStackTrace();

		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return responseCode;
	}

	@Override
	protected void onPostExecute(Integer responseCode)
	{	

		if (responseCode == 1) {
			progressDialog.dismiss();
			Intent i = new Intent();
			i.setClass(activity.getApplicationContext(), DashboardActivity.class);
			activity.startActivity(i);
			//activity.loginReport(responseCode);	
		}
		else {
			progressDialog.dismiss();
			activity.showLoginError(responseCode);
	
		}
		
	}
}