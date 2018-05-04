package demo.ste.mvpcleanarch.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import demo.ste.mvpcleanarch.data.database.ProjectDb;
import demo.ste.mvpcleanarch.data.database.RepoDao;
import demo.ste.mvpcleanarch.util.Config;

@Module
public class DatabaseModule {


    @Provides
    @Singleton
    ProjectDb provideProjectDb(Context context) {
        return Room.databaseBuilder(context, ProjectDb.class, Config.DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    RepoDao provideRepoDao(ProjectDb projectDb) {
        return projectDb.repoDao();
    }


}