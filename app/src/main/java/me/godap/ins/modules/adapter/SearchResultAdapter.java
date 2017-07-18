package me.godap.ins.modules.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import dev.niekirk.com.instagram4android.requests.payload.InstagramSearchUsersResultUser;
import me.godap.ins.R;
import me.godap.ins.dao.UserDBManager;

/**
 * 搜索结果列表适配器
 * Created by Anchorer on 2017/7/18.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private UserDBManager mDBManager;
    private List<InstagramSearchUsersResultUser> mUserList;

    public interface FollowCallback {
        void onFollowBtnClick(InstagramSearchUsersResultUser user);
    }
    private FollowCallback mCallback;

    public SearchResultAdapter(Context context, List<InstagramSearchUsersResultUser> userList, FollowCallback callback) {
        this.mContext = context;
        this.mDBManager = UserDBManager.getInstance();
        this.mUserList = userList;
        this.mCallback = callback;
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        ImageView avatarIv;
        TextView userNameTv, descTv, followeredTv;
        Button followBtn;

        public ItemHolder(View itemView) {
            super(itemView);
            avatarIv = (ImageView) itemView.findViewById(R.id.avatar_iv);
            userNameTv = (TextView) itemView.findViewById(R.id.name_tv);
            descTv = (TextView) itemView.findViewById(R.id.desc_tv);
            followBtn = (Button) itemView.findViewById(R.id.follow_btn);
            followeredTv = (TextView) itemView.findViewById(R.id.followed_tv);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(View.inflate(mContext, R.layout.item_search_result, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        final InstagramSearchUsersResultUser user = getItem(position);

        Glide.with(mContext)
                .load(user.getProfile_pic_url())
                .apply(RequestOptions.circleCropTransform())
                .into(itemHolder.avatarIv);

        String userName = user.getUsername();
        if (TextUtils.isEmpty(userName)) {
            itemHolder.userNameTv.setVisibility(View.GONE);
        } else {
            itemHolder.userNameTv.setVisibility(View.VISIBLE);
            itemHolder.userNameTv.setText(user.getUsername());
        }
        String desc = user.getFull_name();
        if (TextUtils.isEmpty(desc)) {
            itemHolder.descTv.setVisibility(View.GONE);
        } else {
            itemHolder.descTv.setVisibility(View.VISIBLE);
            itemHolder.descTv.setText(desc);
        }

        boolean hasFollowed = mDBManager.hasFollowedUser(user.getPk());
        if (hasFollowed) {
            itemHolder.followBtn.setVisibility(View.GONE);
            itemHolder.followeredTv.setVisibility(View.VISIBLE);
        } else {
            itemHolder.followeredTv.setVisibility(View.GONE);
            itemHolder.followBtn.setVisibility(View.VISIBLE);
            itemHolder.followBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.onFollowBtnClick(user);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mUserList == null) {
            return 0;
        }
        return mUserList.size();
    }

    private InstagramSearchUsersResultUser getItem(int position) {
        if (position >= 0 && position < getItemCount()) {
            return mUserList.get(position);
        }
        return null;
    }

    /**
     * 重置列表数据源
     */
    public void resetListData(List<InstagramSearchUsersResultUser> userList) {
        mUserList.clear();
        mUserList.addAll(userList);
        notifyDataSetChanged();
    }

}
