package demo.ste.mvpcleanarch;


import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import demo.ste.mvpcleanarch.data.database.ProjectDb;
import demo.ste.mvpcleanarch.data.database.RepoDao;
import demo.ste.mvpcleanarch.model.entities.RepoResponseDbEntity;

@RunWith(AndroidJUnit4.class)
public class RepoDaoTest {
    private RepoDao mRepoDao;
    private ProjectDb mDb;

    private static final String ANY_STRING = "anystring";
    private static final Boolean ANY_BOOLEAN = true;
    private static final Long ANY_LONG = 1L;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();


    @Before
    public void initDb() throws Exception {
        mDb = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                ProjectDb.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        mDb.close();
    }


    @Test
    public void insertReposAndGetAllRepos()  {
        // setup vars
        RepoResponseDbEntity item=new RepoResponseDbEntity(ANY_LONG,ANY_STRING,ANY_BOOLEAN,ANY_STRING,ANY_STRING);
        List<RepoResponseDbEntity> items= Arrays.asList(item);
        mDb.repoDao().insert(items);
        mDb.repoDao().getAllRepos().test()
                .assertValue(emitteditems -> {
                    return  ((RepoResponseDbEntity) emitteditems.get(0)).getName().equals(ANY_STRING);
                });
    }
}

