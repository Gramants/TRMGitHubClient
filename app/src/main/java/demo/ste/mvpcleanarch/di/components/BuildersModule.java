package demo.ste.mvpcleanarch.di.components;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import demo.ste.mvpcleanarch.di.modules.PresenterDetailModule;
import demo.ste.mvpcleanarch.di.modules.PresenterDetailViewModule;
import demo.ste.mvpcleanarch.di.modules.PresenterModule;
import demo.ste.mvpcleanarch.di.modules.PresenterViewModule;
import demo.ste.mvpcleanarch.view.DetailActivity;
import demo.ste.mvpcleanarch.view.MainActivity;


@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = {PresenterViewModule.class, PresenterModule.class})
    abstract MainActivity bindActivity();

    @ContributesAndroidInjector(modules = {PresenterDetailViewModule.class, PresenterDetailModule.class})
    abstract DetailActivity bindDetailActivity();
}