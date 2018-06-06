package com.poynt.lib.listener;


import com.poynt.lib.states.PoyntState;

public interface StateEventHandlerInterface<PROVIDER extends StateProvider, VIEW_INTERFACE> {

    void handleStateEvent(int eventId,
                          Class<? extends PoyntState<PROVIDER, VIEW_INTERFACE>> fromStateClass);
}
