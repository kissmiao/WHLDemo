package com.hongliang.demo.otherActivity.anim;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.hongliang.demo.BaseFragment;
import com.hongliang.demo.R;

public class TransLateFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.anim_translate, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = view.findViewById(R.id.iv_translate);
        TranslateAnimation alphaAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate);
        imageView.startAnimation(alphaAnimation);
    }
}
