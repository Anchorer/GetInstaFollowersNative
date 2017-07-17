package me.godap.ins.modules;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import dev.niekirk.com.instagram4android.requests.payload.InstagramGetUserFollowersResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUserSummary;
import me.godap.ins.R;
import me.godap.ins.component.ApiCallback;
import me.godap.ins.component.InstagramManager;
import me.godap.ins.modules.adapter.FollowListAdapter;

/**
 * 粉丝列表页面
 * Created by Anchorer on 2017/7/17.
 */
public class FollowingActivity extends AppCompatActivity {

    private RecyclerView mFollowingsRv;
    private ProgressDialog mProgressDialog;
    private InstagramManager mInstagramManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_list);
        setTitle(getString(R.string.title_followings));
        mInstagramManager = InstagramManager.getInstance();
        mFollowingsRv = (RecyclerView) findViewById(R.id.follow_rv);
        loadFollowersList();
    }

    private void loadFollowersList() {
        mProgressDialog = ProgressDialog.show(this, null, getString(R.string.hint_waiting), true);
        mInstagramManager.getFollowingList(mInstagramManager.getUserId(), new ApiCallback<InstagramGetUserFollowersResult>() {
            @Override
            public void onSuccess(InstagramGetUserFollowersResult instagramGetUserFollowersResult) {
                dismissProgressDialog();
                List<InstagramUserSummary> userSummaryList = instagramGetUserFollowersResult.getUsers();
                displayFollowersList(userSummaryList);
            }

            @Override
            public void onError(Throwable e, String message) {
                dismissProgressDialog();
                String reason = (e == null) ? message : e.toString();
                Toast.makeText(FollowingActivity.this, "Get Following List Failed: " + reason, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayFollowersList(List<InstagramUserSummary> userSummaryList) {
        mFollowingsRv.setLayoutManager(new LinearLayoutManager(this));
        mFollowingsRv.setAdapter(new FollowListAdapter(this, userSummaryList));
    }

    private void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

}
