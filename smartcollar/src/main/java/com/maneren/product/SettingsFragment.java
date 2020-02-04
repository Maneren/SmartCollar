package com.maneren.product;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SettingsFragment extends Fragment {

    private MainActivity activity;
    private Context context;

    private static final String ARG1 = "type";

    // TODO: Rename and change types of parameters
    private String type;

    static SettingsFragment newInstance(@NonNull String param1) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG1, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        if (getArguments() == null || getArguments().getString(ARG1) == null) {
            return inflater.inflate(R.layout.fragment_more, container, false);
        }
        switch (getArguments().getString(ARG1)) {
            case "user":
                view = inflater.inflate(R.layout.fragment_settings_user, container, false);
                break;
            case "pets":
                view = inflater.inflate(R.layout.fragment_settings_pets, container, false);
                break;
            case "general":
                view = inflater.inflate(R.layout.fragment_settings_general, container, false);
                break;
            case "lang":
                view = inflater.inflate(R.layout.fragment_settings_lang, container, false);
                break;
            default:
                return inflater.inflate(R.layout.fragment_more, container, false);
        }

        return view;
    }

    void passActivity(MainActivity activity) {
        this.activity = activity;
        context = activity.getApplicationContext();
    }

}
