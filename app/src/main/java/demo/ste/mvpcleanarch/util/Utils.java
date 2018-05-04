package demo.ste.mvpcleanarch.util;

import android.app.Activity;
import android.content.Intent;

import java.util.Comparator;

import demo.ste.mvpcleanarch.model.entities.RepoResponseDbEntity;
import demo.ste.mvpcleanarch.view.DetailActivity;


public class Utils {

    public static class CustomComparator implements Comparator<Object> {

        @Override
        public int compare(Object o1, Object o2) {
            if ((o1 instanceof RepoResponseDbEntity) && (o2 instanceof RepoResponseDbEntity)) {
                return ((RepoResponseDbEntity) o1).getName().compareTo(((RepoResponseDbEntity) o2).getName());
            }
            return 0;
        }
    }

    public static void openDetail(Activity fromActivity, long detailId) {
        Intent intent = new Intent(fromActivity, DetailActivity.class);
        intent.putExtra(Config.REPO_ID, detailId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        fromActivity.startActivity(intent);
    }

    public static long getRepoId(Intent intent) {
        return getLongExtra(intent, Config.REPO_ID,0);
    }


    private static long getLongExtra(Intent intent, String extraName, long defaultValue) {
        long value = defaultValue;
        if (intent != null) {
            value = intent.getLongExtra(extraName, value);
        }
        return value;
    }

}