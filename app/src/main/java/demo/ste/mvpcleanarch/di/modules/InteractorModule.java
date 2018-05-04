package demo.ste.mvpcleanarch.di.modules;

import dagger.Module;
import dagger.Provides;
import demo.ste.mvpcleanarch.data.database.ProjectDb;
import demo.ste.mvpcleanarch.data.database.RepoDao;
import demo.ste.mvpcleanarch.domain.InteractorImpl;
import demo.ste.mvpcleanarch.interfaces.Api;
import demo.ste.mvpcleanarch.interfaces.Interactor;

@Module
public class InteractorModule {

    @Provides
    Interactor provideInteractor(
            Api api,
            ProjectDb db,
            RepoDao dao) {

        return new InteractorImpl(api, db, dao);
    }

}