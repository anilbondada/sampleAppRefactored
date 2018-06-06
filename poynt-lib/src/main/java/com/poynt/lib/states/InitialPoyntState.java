package com.poynt.lib.states;


import com.poynt.lib.listener.StateProvider;

public class InitialPoyntState<PROVIDER extends StateProvider, VIEW_INTERFACE>
                                    extends PoyntState<PROVIDER, VIEW_INTERFACE> {

    @Override
    public void onStateApplied() {

    }

    @Override
    public void onStateLeft() {

    }
}
