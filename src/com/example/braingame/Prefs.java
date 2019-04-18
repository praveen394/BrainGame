/*****************************
 * Prefs.java
 * Setting preferences for the game
 * Author: Praveen Naresh
 */
package com.example.braingame;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Prefs extends PreferenceActivity{

	private static final String OPT_HINTS = "hints";
	private static final boolean OPT_HINTS_DEF = true;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
	//check if hints is selected
	public static boolean getHints(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(OPT_HINTS, OPT_HINTS_DEF);
	}

}
