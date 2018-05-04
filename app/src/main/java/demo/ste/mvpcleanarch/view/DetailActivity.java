package demo.ste.mvpcleanarch.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import demo.ste.mvpcleanarch.R;
import demo.ste.mvpcleanarch.interfaces.DetailActivityView;
import demo.ste.mvpcleanarch.model.entities.RepoResponseDbEntity;
import demo.ste.mvpcleanarch.presenter.DetailActivityPresenterImpl;
import demo.ste.mvpcleanarch.util.Utils;


public class DetailActivity extends AppCompatActivity implements DetailActivityView {

    @Inject
    DetailActivityPresenterImpl presenter;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.text_repourl)
    TextView repoURL;
    @BindView(R.id.text_repotype)
    TextView repoType;
    @BindView(R.id.text_reponame)
    TextView repoName;
    @BindView(R.id.text_repodescription)
    TextView repoDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        presenter.getRepoById(Utils.getRepoId(getIntent()));

    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String errMsg) {
        updateStatus(errMsg);

    }

    private void updateStatus(String msg) {
        repoName.setText(msg);
        repoDescription.setText("");
        repoType.setText("");
        repoURL.setText("");
    }


    @Override
    public void onGetRepoDetailFromDb(RepoResponseDbEntity repoItem) {
        repoName.setText(repoItem.getName());
        repoDescription.setText(repoItem.getDescription());
        repoType.setText(repoItem.getIsPrivate() ? "Type: private" : "Type: public");
        repoURL.setText(repoItem.getHtmlUrl());

    }

    @Override
    public void writeToStatus(String msg) {
        updateStatus(msg);

    }


}
