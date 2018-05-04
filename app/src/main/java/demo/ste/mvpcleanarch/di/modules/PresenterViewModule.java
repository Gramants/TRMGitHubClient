package demo.ste.mvpcleanarch.di.modules;

import dagger.Binds;
import dagger.Module;
import demo.ste.mvpcleanarch.interfaces.MainActivityView;
import demo.ste.mvpcleanarch.view.MainActivity;

@Module
public abstract class PresenterViewModule {

    @Binds
    abstract MainActivityView provideView(MainActivity activity);

}