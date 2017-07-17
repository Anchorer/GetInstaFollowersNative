package me.godap.ins.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import dev.niekirk.com.instagram4android.requests.payload.InstagramSearchUsernameResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUser;
import me.godap.ins.R;
import me.godap.ins.component.ApiCallback;
import me.godap.ins.component.InstagramManager;

/**
 * Created by Anchorer on 2017/7/17.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mAvatarIv;
    private TextView mNameTv, mFollowerCountTv, mFollowingCountTv;

    private InstagramManager mInstagramManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInstagramManager = InstagramManager.getInstance();
        initViews();
    }

    private void initViews() {
        setTitle(getString(R.string.title_main));
        mAvatarIv = (ImageView) findViewById(R.id.avatar_iv);
        mNameTv = (TextView) findViewById(R.id.name_tv);
        mFollowerCountTv = (TextView) findViewById(R.id.followers_count_tv);
        mFollowingCountTv = (TextView) findViewById(R.id.following_count_tv);
        findViewById(R.id.followers_layout).setOnClickListener(this);
        findViewById(R.id.following_layout).setOnClickListener(this);

        mNameTv.setText(mInstagramManager.getUserFullName());
        Glide.with(this)
                .load(mInstagramManager.getUserAvatar())
                .apply(RequestOptions.circleCropTransform())
                .into(mAvatarIv);
        loadUserData();
    }

    private void loadUserData() {
        mInstagramManager.getUserInfo(mInstagramManager.getUserName(), new ApiCallback<InstagramSearchUsernameResult>() {
            @Override
            public void onSuccess(InstagramSearchUsernameResult result) {
                InstagramUser user = result.getUser();
                mFollowerCountTv.setText(String.valueOf(user.getFollower_count()));
                mFollowingCountTv.setText(String.valueOf(user.getFollowing_count()));
            }

            @Override
            public void onError(Throwable e, String message) {
                String reason = (e == null) ? message : e.toString();
                Toast.makeText(MainActivity.this, "Load user data failed: " + reason, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.followers_layout: {
                // 跳转到粉丝列表页面
                startActivity(new Intent(MainActivity.this, FollowersActivity.class));
                break;
            }
            case R.id.following_layout: {
                // 跳转到关注列表页面
                startActivity(new Intent(MainActivity.this, FollowingActivity.class));
                break;
            }
        }
    }
}
