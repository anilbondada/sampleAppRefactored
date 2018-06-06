package com.poynt.payment.app.ui;import com.poynt.core.CardStateHandler;import com.poynt.core.cardstates.CardCanceledState;import com.poynt.core.cardstates.CardFinishedState;import com.poynt.core.cardstates.CardInsertedState;import com.poynt.core.cardstates.CardProcessingState;import com.poynt.core.cardstates.CardReadingState;import com.poynt.core.cardstates.WelcomeScreenState;import com.poynt.core.enums.TransactionState;import com.poynt.core.events.MessageEvent;import com.poynt.core.listener.CurrentStateListener;import com.poynt.log.Logger;import com.poynt.payment.app.listener.ModelP61View;import com.poynt.payment.app.listener.ModelP61ViewListener;import com.poynt.payment.app.presenter.CardInsertedStatePresenter;import com.poynt.payment.app.presenter.CardReadingStatePresenter;import com.poynt.payment.app.presenter.IPresenter;import com.poynt.payment.app.presenter.PresenterManager;import com.poynt.payment.app.presenter.TransactionFailedPresenter;import com.poynt.payment.app.presenter.TransactionFinishedPresenter;import com.poynt.payment.app.presenter.TransactionInProgressPresenter;import com.poynt.payment.app.presenter.WelcomeStatePresenter;import org.greenrobot.eventbus.EventBus;import org.greenrobot.eventbus.Subscribe;import org.greenrobot.eventbus.ThreadMode;import java.util.HashMap;public class ModelP61ViewImpl        implements        ModelP61View,        ModelP61ViewListener,        CurrentStateListener {    private static final String TAG = "ModelP61ViewImpl";    public ModelP61Fragment mMainView;    public CardStateHandler mCardStateableHandler;    public static int count = 0;    private HashMap<Integer,IPresenter> presentersList;    private PresenterManager manager = new PresenterManager();    private void init(){/*        presentersList =new HashMap<>();        presentersList.put(TransactionState.WELCOME_STATE,WelcomeStatePresenter.getInstance(this));*/        manager.init(this);    }    public ModelP61ViewImpl(ModelP61Fragment view, CardStateHandler stateHandler) {        //super(view, stateHandler);        this.mMainView = view;        this.mCardStateableHandler = stateHandler;        mCardStateableHandler.initiallizeListener(this);        init();    }    @Override    public void onViewStarted() {        Logger.d(TAG, "onViewStarted ");    }    @Override    public void onViewResumed() {        Logger.d(TAG, "onViewResumed ");        EventBus.getDefault().register(this);    }    @Override    public void onViewPaused() {        Logger.d(TAG, "onViewPaused ");        EventBus.getDefault().unregister(this);    }    @Override    public void onViewStopped() {        Logger.d(TAG, "onViewStopped ");    }    @Subscribe(threadMode = ThreadMode.MAIN)    public void onMessageEvent(MessageEvent event) {        Logger.d(TAG, "onMessageEvent ");        toggleButtonStatus(mMainView.mButtonCancel.isEnabled() ? false : true);    }    @Override    public void setNewState() {        Logger.d(TAG, "setNewState " + count);        if (count <= 4) {            if (count == 0) {                mCardStateableHandler.setNewState(CardInsertedState.getInstance());            } else if (count == 1) {                mCardStateableHandler.setNewState(CardReadingState.getInstance());            } else if (count == 2) {                mCardStateableHandler.setNewState(CardProcessingState.getInstance());            } else if (count == 3) {                mCardStateableHandler.setNewState(CardFinishedState.getInstance());            } else if (count == 4) {                mCardStateableHandler.setNewState(CardCanceledState.getInstance());            }        } else {            mCardStateableHandler.setNewState(WelcomeScreenState.getInstance());            count = -1;        }        count++;    }    @Override    public void setCancelState() {        Logger.d(TAG, "setCancelState ");        mCardStateableHandler.setNewState(CardCanceledState.getInstance());        count = 5;    }    @Override    public void setWelcomeState() {        Logger.d(TAG, "setWelcomeState ");        mCardStateableHandler.setNewState(WelcomeScreenState.getInstance());        count = 0;    }    public void toggleButtonStatus(boolean isEnabled) {        mMainView.mButtonCancel.setEnabled(isEnabled);    }    public void toggleOkStatus(boolean isEnabled) {        mMainView.mButtonOk.setEnabled(isEnabled);    }    @Override    public void OnStateEntered(int state) {        Logger.d(TAG, "OnStateEntered  " + state);       /* if(presentersList.containsKey(state)){            presentersList.get(state).updateUI();        }        else{            //we will handle the error by trowing the new UI        }*/        manager.sendDataToPresner(state,null);        /*switch (state) {            case TransactionState.WELCOME_STATE:                WelcomeStatePresenter.getInstance(this).updateWelcomeScreen();                break;            case TransactionState.CARD_INSERTED:                CardInsertedStatePresenter.getInstance(this).showCardInserted();                break;            case TransactionState.CARD_READING:                CardReadingStatePresenter.getInstance(this).showCardReading();                break;            case TransactionState.TRANSACTION_IN_PROGRESS:                TransactionInProgressPresenter.getInstance(this).showCardTransactionProgress();                break;            case TransactionState.TRANSACTION_FINISHED:                TransactionFinishedPresenter.getInstance(this).showCardTransactionFinished();                break;            case TransactionState.TRANSACTION_CANCELED:                TransactionFailedPresenter.getInstance(this).showCardTransactionFailed();                break;        }*/    }    @Override    public void OnStateLeft(int state) {        Logger.d(TAG, "OnStateLeft  " + state);    }    @Override    public ModelP61Fragment getModelP61FragmentView() {        return mMainView;    }}