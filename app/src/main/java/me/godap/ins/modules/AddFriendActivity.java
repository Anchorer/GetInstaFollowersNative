package me.godap.ins.modules;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import dev.niekirk.com.instagram4android.requests.payload.InstagramSearchUsersResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramSearchUsersResultUser;
import dev.niekirk.com.instagram4android.requests.payload.StatusResult;
import me.godap.ins.Following;
import me.godap.ins.R;
import me.godap.ins.component.ApiCallback;
import me.godap.ins.component.InstagramManager;
import me.godap.ins.dao.UserDBManager;
import me.godap.ins.modules.adapter.SearchResultAdapter;

/**
 * 添加好友页面
 * Created by Anchorer on 2017/7/18.
 */
public class AddFriendActivity extends AppCompatActivity implements View.OnClickListener, SearchResultAdapter.FollowCallback {

    private EditText mKeyEt;
    private RecyclerView mUserRv;

    private InstagramManager mInstagramManager;
    private ProgressDialog mProgressDialog;
    private SearchResultAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        mInstagramManager = InstagramManager.getInstance();
        initViews();
    }

    private void initViews() {
        mKeyEt = (EditText) findViewById(R.id.key_et);
        mUserRv = (RecyclerView) findViewById(R.id.user_rv);
        findViewById(R.id.search_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn: {
                search();
                break;
            }
        }
    }

    /**
     * 执行搜索
     */
    private void search() {
        String key = mKeyEt.getText().toString();

        mProgressDialog = ProgressDialog.show(this, null, getString(R.string.hint_waiting), true);
        mInstagramManager.searchUsers(key, new ApiCallback<InstagramSearchUsersResult>() {
            @Override
            public void onSuccess(InstagramSearchUsersResult result) {
                dismissProgressDialog();
                List<InstagramSearchUsersResultUser> userList = result.getUsers();
                displayUserList(userList);
            }

            @Override
            public void onError(Throwable e, String message) {
                dismissProgressDialog();
                String reason = (e == null) ? message : e.toString();
                Toast.makeText(AddFriendActivity.this, "Search Failed: " + reason, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 显示搜索结果列表
     */
    private void displayUserList(List<InstagramSearchUsersResultUser> userList) {
        if (mAdapter == null) {
            mUserRv.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new SearchResultAdapter(this, userList, this);
            mUserRv.setAdapter(mAdapter);
        } else {
            mAdapter.resetListData(userList);
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onFollowBtnClick(final InstagramSearchUsersResultUser user) {
        mProgressDialog = ProgressDialog.show(this, null, getString(R.string.hint_waiting), true);
        final long targetUserId = user.getPk();
        mInstagramManager.followUser(targetUserId, new ApiCallback<StatusResult>() {
            @Override
            public void onSuccess(StatusResult result) {
                dismissProgressDialog();
                Toast.makeText(AddFriendActivity.this, "Follow Success!", Toast.LENGTH_SHORT).show();
                // 向数据库中增加新的关注
                Following following = new Following(targetUserId);
                following.setUserName(user.getUsername());
                following.setFullName(user.getFull_name());
                following.setAvatarUrl(user.getProfile_pic_url());
                following.setIsPrivate(user.is_private());
                UserDBManager.getInstance().addFollowing(following);

                // 更新列表源
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable e, String message) {
                dismissProgressDialog();
                String reason = (e == null) ? message : e.toString();
                Toast.makeText(AddFriendActivity.this, "Follow Failed: " + reason, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
