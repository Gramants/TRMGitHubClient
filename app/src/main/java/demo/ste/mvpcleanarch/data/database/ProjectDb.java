package demo.ste.mvpcleanarch.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import demo.ste.mvpcleanarch.model.entities.RepoResponseDbEntity;


@Database(entities = {RepoResponseDbEntity.class}, version = 1)
public abstract class ProjectDb extends RoomDatabase {
    public abstract RepoDao repoDao();
}
