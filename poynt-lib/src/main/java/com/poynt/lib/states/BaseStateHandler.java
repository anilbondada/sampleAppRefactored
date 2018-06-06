package com.poynt.lib.states;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.poynt.lib.listener.StateProvider;
import com.poynt.lib.listener.Stateable;


public abstract class BaseStateHandler<STATE_PROVIDER extends StateProvider, ACTION_INTERFACE>
        implements Stateable<STATE_PROVIDER, ACTION_INTERFACE>, StateProvider {

    //private final Context context;

    private StateMachine<STATE_PROVIDER, ACTION_INTERFACE> stateMachine;

    protected BaseStateHandler() {
    }

    public StateChanger<STATE_PROVIDER, ACTION_INTERFACE> getStateChanger() {
        return stateMachine.getStateChanger();
    }

    @Override
    public Context provideContext() {
        return null;
        //return context;
    }

    @Override
    public StateMachine<STATE_PROVIDER, ACTION_INTERFACE> getStateMachine() {
        return stateMachine;
    }

    public void onCreate(Bundle savedInstanceState) {
        stateMachine = new StateMachine<>(getStateProvider(), getActionInterface());
        stateMachine.setInitialStateClass(getInitialStateClass());
        onStateMachineDescribe(stateMachine);
        stateMachine.onCreate(savedInstanceState);
    }

    public void onResume() {
        stateMachine.onResume();
    }

    public void onSaveInstanceState(Bundle outState) {
        stateMachine.onSaveInstanceState(outState);
    }

    public void onPause() {
        stateMachine.onPause();
    }

    public void setNewState(@NonNull PoyntState<STATE_PROVIDER, ACTION_INTERFACE> newState) {
        stateMachine.getStateChanger().setState(newState);
    }
}
