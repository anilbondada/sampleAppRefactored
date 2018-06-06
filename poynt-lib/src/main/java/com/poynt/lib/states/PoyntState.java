package com.poynt.lib.states;

import com.poynt.lib.listener.StateEventHandlerInterface;
import com.poynt.lib.listener.StateProvider;
import com.poynt.log.Logger;

public abstract class PoyntState<PROVIDER extends StateProvider, ACTION_INTERFACE> {

    private static final String TAG = "PoyntState";

    public abstract void onStateApplied();

    public abstract void onStateLeft();

    private PROVIDER provider;

    private StateEventHandlerInterface eventHandler;

    private ACTION_INTERFACE actionInterface;

    public void configureState(PROVIDER provider,
                               StateEventHandlerInterface eventHandler,
                               ACTION_INTERFACE actionInterface) {
        this.provider = provider;
        this.eventHandler = eventHandler;
        this.actionInterface = actionInterface;
    }
    
    public ACTION_INTERFACE getActionInterface() {
        return actionInterface;
    }

    protected void launchStateForEventID(int eventId) {
        if (eventHandler != null) {
            eventHandler.handleStateEvent(eventId, this.getClass());
        } else {
            Logger.d(TAG, "StateEventHandlerInterface is null, discard transactions ");
        }
    }
}
