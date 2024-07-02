package com.mbitadsdk;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mbitsdk.R;


public class AdsWaitingDailog extends BottomSheetDialogFragment {

    Context mContext;
    View root_view;

    public AdsWaitingDailog() {
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setStyle(STYLE_NORMAL, R.style.bottomDialog);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //   setStyle(STYLE_NORMAL, R.style.bottomDialog);
        return inflater.inflate(R.layout.ads_waiting_dailog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View mDialog, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(mDialog, savedInstanceState);

        root_view = mDialog.findViewById(R.id.rl_auto_crop_root);

        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;
                setupFullHeight(d);
            }
        });
    }

    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        if (layoutParams != null) {
            layoutParams.height = root_view.getMeasuredHeight();
        }
        // behavior.setPeekHeight(root_view.getMeasuredHeight());
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
}

