package com.maneren.product;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class MoreFragment extends Fragment {

    private Activity activity;
    private Context context;


    public MoreFragment() {
        // Required empty public constructor
    }
    static MoreFragment newInstance() {
        return new MoreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        // Inflate the layout for this fragment

        ConstraintLayout shareMenu = view.findViewById(R.id.shareMenu);
        shareMenu.setOnClickListener(v -> Communication.shareText(context, "github.com/lambda-collars/Application"));
        ConstraintLayout aboutMenu = view.findViewById(R.id.aboutMenu);
        aboutMenu.setOnClickListener(v -> Communication.shareURL(context, "https://github.com/lambda-collars/Application"));
        ConstraintLayout userMenu = view.findViewById(R.id.detailsMenu);
        shareMenu.setOnClickListener(v -> ((MainActivity) activity).displaySettingsFragment("user"));
        ConstraintLayout petsMenu = view.findViewById(R.id.petsMenu);
        shareMenu.setOnClickListener(v -> {
            Communication.shareText(context, "github.com/lambda-collars/Application");
        });
        ConstraintLayout settingsMenu = view.findViewById(R.id.settingsMenu);
        shareMenu.setOnClickListener(v -> {
            Communication.shareText(context, "github.com/lambda-collars/Application");
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    void passActivity(MainActivity activity) {
        this.activity = activity;
        context = activity.getApplicationContext();
    }
}
