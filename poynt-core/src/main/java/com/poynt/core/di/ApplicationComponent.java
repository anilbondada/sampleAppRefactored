package com.poynt.core.di;

import android.app.Application;

import com.poynt.core.CardStateHandler;
import com.poynt.core.cardstates.CardCanceledState;
import com.poynt.core.cardstates.CardFinishedState;
import com.poynt.core.cardstates.CardInsertedState;
import com.poynt.core.cardstates.CardProcessingState;
import com.poynt.core.cardstates.CardReadingState;
import com.poynt.core.cardstates.WelcomeScreenState;

import javax.inject.Singleton;


import dagger.Component;

@Singleton
@Component(modules = {MainModule.class})
public interface ApplicationComponent {

    void inject(WelcomeScreenState welcomeScreenState);

    void inject(CardInsertedState cardInsertedState);

    void inject(CardReadingState cardReadingState);

    void inject(CardProcessingState cardProcessingState);

    void inject(CardFinishedState cardFinishedState);

    void inject(CardCanceledState cardCanceledState);

    void inject(CardStateHandler cardStateableHandler);

    final class Initializer {

        private Initializer() {
        }

        public static ApplicationComponent init(Application app) {
            DaggerApplicationComponent.Builder builder = DaggerApplicationComponent.builder();

            return builder
                    .mainModule(new MainModule(app))
                    .build();
        }
    }
}
