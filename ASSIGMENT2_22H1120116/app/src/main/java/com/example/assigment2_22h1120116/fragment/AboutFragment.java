package com.example.assigment2_22h1120116.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.assigment2_22h1120116.R;

import java.io.FileReader;

public class AboutFragment extends Fragment {
    private VideoView videoView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about,container,false);
        videoView = view.findViewById(R.id.video);
        String packageName = requireContext().getPackageName();
        String videoPath = "android.resource://" + packageName + "/raw/royalty";
        videoView.setVideoPath(videoPath);
        videoView.start();
        return view;
    }
}
