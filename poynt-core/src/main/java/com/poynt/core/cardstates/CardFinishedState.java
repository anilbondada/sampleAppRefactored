package com.poynt.core.cardstates;

import com.poynt.core.di.CoreComponent;
import com.poynt.core.enums.TransactionState;
import com.poynt.log.Logger;

public class CardFinishedState extends BaseCardState {

    private static final String TAG = "CardFinishedState";

    private CardFinishedState(){};

    private static class LazyCardFinishedStateHolder {
        static final CardFinishedState INSTANCE = new CardFinishedState();
    }

    public static CardFinishedState getInstance() {
        return LazyCardFinishedStateHolder.INSTANCE;
    }

    @Override
    protected void injectDependencies() {
        CoreComponent.getComponentInstance().inject(this);
    }

    @Override
    public void onStateApplied() {
        super.onStateApplied();
        Logger.d(TAG, "onStateApplied");
        getActionInterface().notifyOnStateEntered(TransactionState.TRANSACTION_FINISHED);
    }

    @Override
    public void onStateLeft() {
        super.onStateLeft();
        Logger.d(TAG, "onStateLeft");
        getActionInterface().notifyOnStateLeft(TransactionState.TRANSACTION_FINISHED);
    }

    @Override
    public void showNextEvent() {
        launchStateForEventID(TransactionState.WELCOME_STATE);
    }
}
