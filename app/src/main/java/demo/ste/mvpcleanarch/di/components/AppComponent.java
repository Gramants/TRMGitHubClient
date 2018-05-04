package demo.ste.mvpcleanarch.di.components;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import demo.ste.mvpcleanarch.App;
import demo.ste.mvpcleanarch.di.modules.AppModule;
import demo.ste.mvpcleanarch.di.modules.DatabaseModule;
import demo.ste.mvpcleanarch.di.modules.InteractorModule;
import demo.ste.mvpcleanarch.di.modules.NetModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        DatabaseModule.class,
        NetModule.class,
        InteractorModule.class,
        BuildersModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App application);

        AppComponent build();
    }

    void inject(App app);
}