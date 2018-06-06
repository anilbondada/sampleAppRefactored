package com.poynt.payment.app.presenter;

import android.view.View;

import com.poynt.payment.app.listener.ModelP61View;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by H211060 on 6/5/2018.
 */

public class PresenterManager {

    HashSet<IPresenter> presenters = new HashSet<>();
    public void init(ModelP61View view){
        IPresenter presenter = WelcomeStatePresenter.getInstance(view);
        add(presenter);
        IPresenter presenter1 = TransactionFailedPresenter.getInstance(view);
        add(presenter1);
        IPresenter transactionFinishedState = TransactionFinishedPresenter.getInstance(view);
        add(transactionFinishedState);

        IPresenter cardInsertedStatePresenter = CardInsertedStatePresenter.getInstance(view);
        add(cardInsertedStatePresenter);

        IPresenter cardReadingStatePresenter = CardReadingStatePresenter.getInstance(view);
        add(cardReadingStatePresenter);

        IPresenter inProgressStatePresenter = TransactionInProgressPresenter.getInstance(view);
        add(inProgressStatePresenter);


    }
    public void add(IPresenter presenter){
        presenters.add(presenter);
    }
    public void remove(IPresenter presenter){
        presenters.add(presenter);
    }

    public void sendDataToPresner(int state,String data){
        for (IPresenter presenter:presenters
             ) {
            if(presenter.isYourState(state)){
                presenter.updateUI();
                break;
            }
        }
    }
}
