package demo.ste.mvpcleanarch.di.modules;

import dagger.Module;
import dagger.Provides;
import demo.ste.mvpcleanarch.interfaces.Interactor;
import demo.ste.mvpcleanarch.interfaces.DetailActivityView;
import demo.ste.mvpcleanarch.presenter.DetailActivityPresenterImpl;

@Module
public class PresenterDetailModule {

    @Provides
    DetailActivityPresenterImpl providePresenterDetail(DetailActivityView view,
                                                       Interactor interactor) {
        return new DetailActivityPresenterImpl(view, interactor);
    }


}