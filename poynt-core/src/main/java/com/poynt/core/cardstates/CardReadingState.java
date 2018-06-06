package com.poynt.core.cardstates;

import com.poynt.core.di.CoreComponent;
import com.poynt.core.enums.TransactionState;
import com.poynt.log.Logger;

public class CardReadingState extends BaseCardState {

    private static final String TAG = "CardReadingState";

    private CardReadingState(){};

    private static class LazyCardReadingStateHolder {
        static final CardReadingState INSTANCE = new CardReadingState();
    }

    public static CardReadingState getInstance() {
        return LazyCardReadingStateHolder.INSTANCE;
    }

    @Override
    protected void injectDependencies() {
        CoreComponent.getComponentInstance().inject(this);
    }

    @Override
    public void onStateApplied() {
        super.onStateApplied();
        Logger.d(TAG, "onStateApplied");
        getActionInterface().notifyOnStateEntered(TransactionState.CARD_READING);
    }

    @Override
    public void onStateLeft() {
        super.onStateLeft();
        Logger.d(TAG, "onStateLeft");
        getActionInterface().notifyOnStateLeft(TransactionState.CARD_READING);
    }

    @Override
    public void showNextEvent() {
        launchStateForEventID(TransactionState.TRANSACTION_IN_PROGRESS);
    }
}
