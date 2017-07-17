package me.godap.ins.modules;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import me.godap.ins.R;
import me.godap.ins.component.InstagramManager;

/**
 * Created by Anchorer on 2017/7/17.
 */

public class MainActivity extends Activity implements View.OnClickListener {

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.followers_layout: {
                // TODO 跳转到粉丝列表页面
                break;
            }
            case R.id.following_layout: {
                // TODO 跳转到关注列表页面
                break;
            }
        }
    }
}
