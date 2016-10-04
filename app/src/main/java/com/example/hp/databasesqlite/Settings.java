package com.example.hp.databasesqlite;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by HP on 9/29/2016.
 */
public class Settings extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefrences_screen);
    }
}
