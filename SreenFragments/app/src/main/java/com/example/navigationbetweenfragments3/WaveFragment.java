package com.example.navigationbetweenfragments3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WaveFragment extends Fragment {

    private View rootView;

    public WaveFragment() {
        super(R.layout.fragment_wave);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_wave, container, false);
        return rootView;
    }

    public void setBackgroundColor(int color) {
        if (rootView != null) {
            rootView.setBackgroundColor(color);
        }
    }
}