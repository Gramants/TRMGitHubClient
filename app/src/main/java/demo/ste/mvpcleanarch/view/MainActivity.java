package demo.ste.mvpcleanarch.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import demo.ste.mvpcleanarch.R;
import demo.ste.mvpcleanarch.interfaces.MainActivityView;
import demo.ste.mvpcleanarch.model.LoginResponse;
import demo.ste.mvpcleanarch.model.entities.RepoResponseDbEntity;
import demo.ste.mvpcleanarch.presenter.MainActivityPresenterImpl;
import demo.ste.mvpcleanarch.util.CheckInternet;
import demo.ste.mvpcleanarch.util.Utils;
import demo.ste.mvpcleanarch.view.adapters.RepoListAdapter;


public class MainActivity extends AppCompatActivity implements MainActivityView, RepoListAdapter.OnItemClickListener {

    @Inject
    MainActivityPresenterImpl presenter;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.edit_username)
    EditText editUserName;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.button_login)
    Button buttonSimpleLogin;
    @BindView(R.id.button_getrepos)
    Button buttonGetRepos;
    @BindView(R.id.repo_list)
    RecyclerView rvRepoList;
    @BindView(R.id.text_status)
    TextView statusText;

    private RepoListAdapter repoAdapter;

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // NOOP
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validateFields();
        }

        @Override
        public void afterTextChanged(Editable s) {
            // NOOP
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }


    private void init() {

        repoAdapter = new RepoListAdapter(this);
        rvRepoList.setAdapter(repoAdapter);
        rvRepoList.setLayoutManager(new LinearLayoutManager(this));
        buttonSimpleLogin.setEnabled(false);
        editUserName.addTextChangedListener(mTextWatcher);
        editPassword.addTextChangedListener(mTextWatcher);
        showGetReposUI(false);

        buttonSimpleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckInternet.isOnline(getApplicationContext())) {
                    showGetReposUI(false);
                    showProgress();
                    statusMessage(getString(R.string.logging));
                    presenter.doSimpleLogin(editUserName.getText().toString(), editPassword.getText().toString());
                } else {
                    statusMessage(getString(R.string.check_internet));
                }


            }
        });


    }

    private void showGetReposUI(boolean show) {

        if (show) {
            buttonGetRepos.setVisibility(View.VISIBLE);

        } else {
            buttonGetRepos.setVisibility(View.INVISIBLE);

        }

    }

    private void validateFields() {
        buttonSimpleLogin.setEnabled(!TextUtils.isEmpty(editUserName.getText()) && !TextUtils.isEmpty(editPassword.getText()));
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String errorMessage) {
        hideProgress();
        statusMessage(errorMessage);
        showGetReposUI(false);


    }


    @Override
    public void onLoginResponseReceived(LoginResponse model) {
        hideProgress();
        editPassword.setText("");
        editUserName.setText("");
        statusMessage(getString(R.string.login_ok));
        showGetReposUI(true);

        buttonGetRepos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckInternet.isOnline(getApplicationContext())) {
                    presenter.doFetchAllRepos(model.getLogin());
                } else {
                    statusMessage(getString(R.string.no_connection));
                }

                statusMessage("");
                showProgress();
            }
        });

    }

    private void statusMessage(String msg) {
        statusText.setText(msg);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void writeToStatus(String status) {
        statusMessage(status);
    }

    @Override
    public void clearResults() {

    }

    @Override
    public void onGetRepoListFromDb(List<RepoResponseDbEntity> list) {
        statusMessage(getString(R.string.reposfound, String.valueOf(list.size())));
        Collections.sort(list, new Utils.CustomComparator());
        showGetReposUI(false);
        repoAdapter.updateRepoList(list);
    }

    @Override
    public void setNoResult() {
        statusMessage(getString(R.string.noreposfound));
    }


    @Override
    public void onItemClick(RepoResponseDbEntity item) {
        Utils.openDetail(this, item.getId());
    }
}
