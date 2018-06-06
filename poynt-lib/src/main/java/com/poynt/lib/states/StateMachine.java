package com.poynt.lib.states;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.poynt.lib.BuildConfig;
import com.poynt.lib.listener.StateEventHandlerInterface;
import com.poynt.lib.listener.StateProvider;
import com.poynt.log.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateMachine<PROVIDER
        extends StateProvider, ACTION_INTERFACE>
        implements StateEventHandlerInterface<PROVIDER, ACTION_INTERFACE> {

    public static final String TAG = "StateMachine";

    private String currentStateClassBundleKey = "INITIAL_STATE_CLASS";

    Class<?> initialStateClass = InitialPoyntState.class;

    Map<Class<?>, List<Transition>> stateToTransitionsMap = new HashMap<>();

    StateChanger<PROVIDER, ACTION_INTERFACE> stateChanger;

    PoyntState<PROVIDER, ACTION_INTERFACE> reCreatedState;

    PoyntState<PROVIDER, ACTION_INTERFACE> unSetedState;

    public StateMachine(@NonNull PROVIDER provider, @NonNull ACTION_INTERFACE actionInterface) {
        stateChanger = new StateChanger<>(provider, this, actionInterface);
    }

    public StateMachine(@NonNull StateChanger<PROVIDER, ACTION_INTERFACE> stateChanger) {
        this.stateChanger = stateChanger;
    }

    public void setInitialStateClass(@NonNull Class<?> initialStateClass) {
        this.initialStateClass = initialStateClass;
    }

    @Override
    public void handleStateEvent(final int eventId,
                                 @NonNull Class<? extends PoyntState<PROVIDER, ACTION_INTERFACE>>
                                         fromStateClass) {
        List<Transition> transitions = stateToTransitionsMap.get(fromStateClass);
        if (transitions == null) {
            if (BuildConfig.DEBUG) {
                throw new IllegalStateException("No transitions for fromState: " + fromStateClass);
            }
        }

        Transition foundTransition = null;
        for (Transition transition : transitions) {
            if (transition.getOnEvent() == eventId) {
                foundTransition = transition;
                break;
            }
        }

        if (foundTransition == null) {
            if (BuildConfig.DEBUG) {
                throw new IllegalStateException("No transition for fromState: " +
                        fromStateClass + "eventId: " + eventId);
            }
        }

        PoyntState<PROVIDER, ACTION_INTERFACE> newState =
                createStateFromClass(foundTransition.getToState());
        if (newState == null) {
            return;
        }

        Logger.d(TAG, "From PoyntState: " + stateChanger.getCurrentState() +
                ". Transition: " + foundTransition);

        stateChanger.setState(newState);
    }

    public void addTransitionFromClass(@NonNull Class<?> fromClass, int onEvent, Class<?> toClass) {
        addTransitionFromClass(fromClass, new Transition(onEvent, toClass));
    }

    public void addTransitionFromClass(@NonNull Class<?> fromClass, Transition transition) {
        List<Transition> transitions = stateToTransitionsMap.get(fromClass);
        if (transitions == null) {
            transitions = new ArrayList<>();
            stateToTransitionsMap.put(fromClass, transitions);
        }
        transitions.add(transition);
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        PoyntState stateToSave = null;
        if (unSetedState != null) {
            stateToSave = unSetedState;
        } else if (stateChanger.getCurrentState() != null) {
            stateToSave = stateChanger.getCurrentState();
        } else if (reCreatedState != null) {
            stateToSave = reCreatedState;
        }
        if (stateToSave != null) {
            outState.putSerializable(currentStateClassBundleKey, stateToSave.getClass());
        }
    }

    /**
     * StateMachine attached to our mainview
     */
    public void onCreate(Bundle savedInstanceState) {
        reCreateState(savedInstanceState);
    }

    /**
     * StateMachine attached to our mainview
     */
    public void onResume() {
        if (stateChanger.getCurrentState() == null) {
            configureCurrentState();
        }
    }

    /**
     * StateMachine attached to our mainview
     */
    public void onPause() {
        unSetedState = stateChanger.getCurrentState();
        stateChanger.unSetCurrentState();
    }

    @NonNull
    public StateChanger<PROVIDER, ACTION_INTERFACE> getStateChanger() {
        return stateChanger;
    }

    @NonNull
    public PoyntState<PROVIDER, ACTION_INTERFACE> getCurrentState() {
        return stateChanger.getCurrentState();
    }

    public void reset() {
        stateChanger.unSetCurrentState();
        reCreatedState = null;
        unSetedState = null;
        configureCurrentState();
    }

    private void reCreateState(@NonNull Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Class<PoyntState<PROVIDER, ACTION_INTERFACE>> currentStateClass =
                    (Class<PoyntState<PROVIDER, ACTION_INTERFACE>>) savedInstanceState
                            .getSerializable(currentStateClassBundleKey);
            try {
                reCreatedState = currentStateClass.getConstructor().newInstance();
                Logger.d(TAG, "Recreating state: " + reCreatedState);
            } catch (Exception e) {
                Logger.e(TAG, "Error while re creating state: " + e);
            }
        }
    }

    private void configureCurrentState() {
        if (reCreatedState != null) {
            stateChanger.setState(reCreatedState);
        } else if (unSetedState != null) {
            stateChanger.setState(unSetedState);
        } else {
            PoyntState<PROVIDER, ACTION_INTERFACE> baseState = createStateFromClass
                    (initialStateClass);
            if (baseState == null) {
                if (BuildConfig.DEBUG) {
                    throw new IllegalArgumentException("Cannot create state from class " +
                            initialStateClass);
                }
            }
            stateChanger.setState(baseState);
        }
    }

    @Nullable
    private PoyntState<PROVIDER, ACTION_INTERFACE> createStateFromClass(Class<?> stateClass) {
        PoyntState<PROVIDER, ACTION_INTERFACE> contextState = null;
        try {
            contextState = (PoyntState<PROVIDER, ACTION_INTERFACE>) stateClass.getConstructor()
                    .newInstance();
        } catch (Exception e) {
            Logger.e(TAG, "Error while creating state: " + e);
        }
        return contextState;
    }

    public void setCurrentStateClassBundleKey(@NonNull String currentStateClassBundleKey) {
        this.currentStateClassBundleKey = currentStateClassBundleKey;
    }

}
