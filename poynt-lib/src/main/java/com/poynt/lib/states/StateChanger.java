package com.poynt.lib.states;

import android.support.annotation.NonNull;

import com.poynt.lib.listener.StateEventHandlerInterface;
import com.poynt.lib.listener.StateProvider;
import com.poynt.log.Logger;

public class StateChanger<PROVIDER extends StateProvider, ACTION_INTERFACE> {

    public static final String TAG = "StateChanger";

    private final ACTION_INTERFACE actionInterface;

    private final PROVIDER provider;

    private final StateEventHandlerInterface eventHandler;

    private PoyntState<PROVIDER, ACTION_INTERFACE> currentState;

    public StateChanger(PROVIDER stateContext, StateEventHandlerInterface eventHandler, ACTION_INTERFACE actionInterface) {
        this.provider = stateContext;
        this.eventHandler = eventHandler;
        this.actionInterface = actionInterface;
    }

    public void setState(@NonNull PoyntState<PROVIDER, ACTION_INTERFACE> newState) {
        newState.configureState(provider, eventHandler, actionInterface);

        Logger.d(TAG, "Changing state from " + currentState + " to " + newState);
        PoyntState<PROVIDER, ACTION_INTERFACE> previousState = this.currentState;
        if (previousState != null) {
            previousState.onStateLeft();
        }
        this.currentState = newState;
        this.currentState.onStateApplied();
    }

    public PoyntState<PROVIDER, ACTION_INTERFACE> getCurrentState() {
        return currentState;
    }


    public StateEventHandlerInterface getStateEventHandlerInterface() {
        return eventHandler;
    }

    // reset
    public void unSetCurrentState() {
        Logger.d(TAG,"Unseting state " + currentState);
        currentState.onStateLeft();
        currentState = null;
    }
}
