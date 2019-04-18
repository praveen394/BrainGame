/****************************
 * About.java
 * Calls the about.xml in a new activity
 * Author: Praveen Naresh
 */
package com.example.braingame;

import android.app.Activity;
import android.os.Bundle;

public class About extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);// calling the about layout
	}

}
