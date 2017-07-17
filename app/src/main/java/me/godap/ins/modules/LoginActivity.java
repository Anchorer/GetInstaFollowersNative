package me.godap.ins.modules;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import dev.niekirk.com.instagram4android.requests.payload.InstagramLoginResult;
import me.godap.ins.R;
import me.godap.ins.component.ApiCallback;
import me.godap.ins.component.InstagramManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUsernameEt, mPwEt;
    private InstagramManager mInstagramManager;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mInstagramManager = InstagramManager.getInstance();
        initViews();
    }

    private void initViews() {
        mUsernameEt = (EditText) findViewById(R.id.username_et);
        mPwEt = (EditText) findViewById(R.id.pw_et);
        findViewById(R.id.login_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn: {
                login();
                break;
            }
        }
    }

    private void login() {
        String userName = mUsernameEt.getText().toString();
        String pw = mPwEt.getText().toString();

        mProgressDialog = ProgressDialog.show(this, "", "Please wait...", true);
        mInstagramManager.login(userName, pw, new ApiCallback<InstagramLoginResult>() {
            @Override
            public void onSuccess(InstagramLoginResult instagramLoginResult) {
                dismissDialog();
                Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                // 跳转到用户信息页面
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onError(Throwable e, String message) {
                dismissDialog();
                String reason = (e == null) ? message : e.toString();
                Toast.makeText(LoginActivity.this, "Login Failed: " + reason, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dismissDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

}
