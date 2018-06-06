package com.poynt.core.cardstates;

import com.poynt.core.di.CoreComponent;
import com.poynt.core.enums.TransactionState;
import com.poynt.core.events.MessageEvent;
import com.poynt.log.Logger;

import org.greenrobot.eventbus.EventBus;

public class CardCanceledState extends BaseCardState {

    private static final String TAG = "CardCanceledState";

    private CardCanceledState(){};

    private static class LazyCardCanceledStateHolder {
        static final CardCanceledState INSTANCE = new CardCanceledState();
    }

    public static CardCanceledState getInstance() {
        return LazyCardCanceledStateHolder.INSTANCE;
    }

    @Override
    protected void injectDependencies() {
        CoreComponent.getComponentInstance().inject(this);
    }

    @Override
    public void onStateApplied() {
        super.onStateApplied();
        Logger.d(TAG, "onStateApplied");
        EventBus.getDefault().post(new MessageEvent());
        getActionInterface().notifyOnStateEntered(TransactionState.TRANSACTION_CANCELED);
    }

    @Override
    public void onStateLeft() {
        super.onStateLeft();
        Logger.d(TAG, "onStateLeft");
        getActionInterface().notifyOnStateLeft(TransactionState.TRANSACTION_CANCELED);
        EventBus.getDefault().post(new MessageEvent());
    }

    @Override
    public void showNextEvent() {
        launchStateForEventID(TransactionState.WELCOME_STATE);
    }
}
