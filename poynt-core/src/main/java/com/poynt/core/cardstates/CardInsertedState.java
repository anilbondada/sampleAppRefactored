package com.poynt.core.cardstates;

import com.poynt.core.di.CoreComponent;
import com.poynt.core.enums.TransactionState;
import com.poynt.log.Logger;

public class CardInsertedState extends BaseCardState {

    private static final String TAG = "CardInsertedState";

    private CardInsertedState(){};

    private static class LazyCardInsertedStateHolder {
        static final CardInsertedState INSTANCE = new CardInsertedState();
    }

    public static CardInsertedState getInstance() {
        return LazyCardInsertedStateHolder.INSTANCE;
    }

    @Override
    protected void injectDependencies() {
        CoreComponent.getComponentInstance().inject(this);
    }

    @Override
    public void onStateApplied() {
        super.onStateApplied();
        Logger.d(TAG, "onStateApplied");
        getActionInterface().notifyOnStateEntered(TransactionState.CARD_INSERTED);
    }

    @Override
    public void onStateLeft() {
        super.onStateLeft();
        Logger.d(TAG, "onStateLeft");
        getActionInterface().notifyOnStateLeft(TransactionState.CARD_INSERTED);
    }

    @Override
    public void showNextEvent() {
        launchStateForEventID(TransactionState.CARD_READING);
    }
}
