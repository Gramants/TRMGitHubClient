package demo.ste.mvpcleanarch.di.modules;

import dagger.Binds;
import dagger.Module;
import demo.ste.mvpcleanarch.interfaces.DetailActivityView;
import demo.ste.mvpcleanarch.view.DetailActivity;

@Module
public abstract class PresenterDetailViewModule {

    @Binds
    abstract DetailActivityView provideViewDetail(DetailActivity activity);

}