package demo.ste.mvpcleanarch.di.modules;

import dagger.Module;
import dagger.Provides;
import demo.ste.mvpcleanarch.interfaces.Interactor;
import demo.ste.mvpcleanarch.interfaces.MainActivityView;
import demo.ste.mvpcleanarch.presenter.MainActivityPresenterImpl;

@Module
public class PresenterModule {

    @Provides
    MainActivityPresenterImpl providePresenter(MainActivityView view,
                                               Interactor interactor) {
        return new MainActivityPresenterImpl(view, interactor);
    }


}