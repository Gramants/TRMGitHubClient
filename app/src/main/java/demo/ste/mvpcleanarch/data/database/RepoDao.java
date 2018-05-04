package demo.ste.mvpcleanarch.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import demo.ste.mvpcleanarch.model.entities.RepoResponseDbEntity;
import demo.ste.mvpcleanarch.util.Config;
import io.reactivex.Flowable;


@Dao
public abstract class RepoDao {
    @Query("SELECT * FROM " + Config.TABLE_REPOS)
    public abstract Flowable<List<RepoResponseDbEntity>> getAllRepos();

    @Query("SELECT * FROM " + Config.TABLE_REPOS + " where id = :id")
    public abstract Flowable<RepoResponseDbEntity> getRepoById(long id);

    @Query("DELETE FROM " + Config.TABLE_REPOS)
    public abstract void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<RepoResponseDbEntity> repos);

    @Transaction
    public void updateData(List<RepoResponseDbEntity> repos) {
        deleteAll();
        insert(repos);
    }


}
