package com.poynt.core.cardstates;

import com.poynt.core.listener.CardActionInterface;
import com.poynt.core.listener.CardReaderProvider;
import com.poynt.core.listener.NextEventListener;
import com.poynt.lib.states.PoyntState;
import com.poynt.log.Logger;

public abstract class BaseCardState
        extends PoyntState<CardReaderProvider, CardActionInterface> implements NextEventListener {

    private static final String TAG = "BaseCardState";

    protected abstract void injectDependencies();

    public BaseCardState() {
        injectDependencies();
    }

    @Override
    public void onStateApplied() {
        Logger.d(TAG, "onStateApplied");
    }

    @Override
    public void onStateLeft() {
        Logger.d(TAG, "onStateLeft");
    }

    @Override
    public void showNextEvent() {
        Logger.d(TAG, "showNextEvent");
    }
}
