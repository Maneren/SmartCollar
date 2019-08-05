package com.maneren.product;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.HashSet;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.settings_activity);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }



    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            final Set<String> stringSet = sharedPref.getStringSet("collars", new HashSet<String>());
            updateCollarsLists(stringSet.toArray(new String[0]));
/*
            selectingID.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Utils.writeStringToFile("selectedIDs.txt", preference.toString(), false);
                    return false;
                }
            });*/

            final EditTextPreference addingID = findPreference("addID");
            if(addingID == null) throw new RuntimeException("addingID EditTextPreference not found");
            addingID.setOnPreferenceChangeListener((preference, object) -> {

                String number = object.toString();

                if(number.length() > 4 || number.length() < 4) return false;

                SharedPreferences sharedPref12 = getActivity().getPreferences(Context.MODE_PRIVATE);
                Set<String> collars = sharedPref12.getStringSet("collars", new HashSet<>());
                collars.add(number);

                SharedPreferences.Editor editor = sharedPref12.edit();
                editor.clear();
                editor.putStringSet("collars", collars);
                editor.commit();
                Log.d("PreferencesChange", object.toString() + "_" + collars);

                updateCollarsLists(collars.toArray(new String[0]));


                return false;
            });

            final ListPreference removingID = findPreference("removeID");
            if(removingID == null) throw new RuntimeException("removeID ListPreference not found");
            removingID.setOnPreferenceChangeListener((preference, object) -> {

                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.remove_collar_title)
                        .setMessage(R.string.remove_collar_text + " " + object.toString())
                        .setPositiveButton(R.string.yes, (dialog, which) ->{}

                        )
                        .setNegativeButton(R.string.no, null)
                        .show();



                SharedPreferences sharedPref1 = getActivity().getPreferences(Context.MODE_PRIVATE);
                Set<String> collars = sharedPref1.getStringSet("collars", new HashSet<String>());
                collars.remove(object.toString());

                SharedPreferences.Editor editor = sharedPref1.edit();
                editor.clear();
                editor.putStringSet("collars", collars);
                editor.commit();
                Log.d("PreferencesChange", object.toString() + "_" + collars);

                updateCollarsLists(collars.toArray(new String[0]));


                return false;
            });
        }

        private void updateCollarsLists(String[] entries){
            final ListPreference selectingID = findPreference("selectID");
            if(selectingID != null){
                selectingID.setEntries(entries);
                selectingID.setEntryValues(entries);
            } else throw new RuntimeException("selectingID ListPreference not found");

            final ListPreference removingID = findPreference("removeID");
            if(removingID != null){
                removingID.setEntries(entries);
                removingID.setEntryValues(entries);
            } else throw new RuntimeException("removeID ListPreference not found");
        }
    }
}
