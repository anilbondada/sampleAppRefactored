package com.poynt.payment.app.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.poynt.payment.app.BuildConfig;
import com.poynt.payment.app.R;
import com.poynt.core.CardStateHandler;
import com.poynt.core.base.MainViewListener;
import com.poynt.core.base.PoyntBaseActivity;
import com.poynt.payment.app.constants.AppFlavors;


public class ModelMainActivity extends PoyntBaseActivity implements MainViewListener {

    private static final String TAG = "ModelMainActivity";

    private CardStateHandler mCardStateableHandler;

    private Fragment destinationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use same name and modify the UI
        setContentView(R.layout.activity_main);

        mCardStateableHandler = new CardStateHandler(this);
        mCardStateableHandler.onCreate(savedInstanceState);

        if (BuildConfig.FLAVOR.equals(AppFlavors.ModelP61)) {
            destinationFragment = ModelP61Fragment.getInstance(mCardStateableHandler);
        } else if (BuildConfig.FLAVOR.equals(AppFlavors.ModelP5)) {
            destinationFragment = ModelP5Fragment.getInstance(mCardStateableHandler);
        }
        this.setDefaultFragment(destinationFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        mCardStateableHandler.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCardStateableHandler.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mCardStateableHandler.onSaveInstanceState(outState);
    }

    // This method is used to set the default fragment that will be shown.
    private void setDefaultFragment(Fragment defaultFragment) {
        this.replaceFragment(defaultFragment);
    }

    // Replace current Fragment with the destination Fragment.
    @Override
    public void replaceFragment(Fragment destFragment) {
        // First get FragmentManager object.
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.main_activity_container, destFragment);

        // Commit the Fragment replace action.
        fragmentTransaction.commit();
    }
}
