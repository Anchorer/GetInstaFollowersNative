package me.godap.ins.modules.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import dev.niekirk.com.instagram4android.requests.payload.InstagramUserSummary;
import me.godap.ins.R;
import me.godap.ins.component.Consts;

/**
 * 粉丝列表/关注列表适配器
 * Created by Anchorer on 2017/7/17.
 */
public class FollowListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<InstagramUserSummary> mUserList;

    public FollowListAdapter(Context context, List<InstagramUserSummary> userList) {
        this.mContext = context;
        this.mUserList = userList;
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        ImageView avatarView;
        TextView nameView, descView;

        public ItemHolder(View itemView) {
            super(itemView);
            avatarView = (ImageView) itemView.findViewById(R.id.avatar_iv);
            nameView = (TextView) itemView.findViewById(R.id.name_tv);
            descView = (TextView) itemView.findViewById(R.id.desc_tv);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(View.inflate(mContext, R.layout.item_follow, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        InstagramUserSummary user = getItem(position);
        ItemHolder itemHolder = (ItemHolder) holder;
        Log.i(Consts.TAG, "Display: " + user.getFull_name() + ", isFavorite: " + user.is_favorite());
        Glide.with(mContext)
                .load(user.getProfile_pic_url())
                .apply(RequestOptions.circleCropTransform())
                .into(itemHolder.avatarView);

        String name = user.getUsername();
        if (TextUtils.isEmpty(name)) {
            itemHolder.nameView.setVisibility(View.GONE);
        } else {
            itemHolder.nameView.setVisibility(View.VISIBLE);
            itemHolder.nameView.setText(name);
        }

        String des = user.getFull_name();
        if (TextUtils.isEmpty(des)) {
            itemHolder.descView.setVisibility(View.GONE);
        } else {
            itemHolder.descView.setVisibility(View.VISIBLE);
            itemHolder.descView.setText(des);
        }
    }

    @Override
    public int getItemCount() {
        if (mUserList == null) {
            return 0;
        }
        return mUserList.size();
    }

    private InstagramUserSummary getItem(int position) {
        if (position >= 0 && position < getItemCount()) {
            return mUserList.get(position);
        }
        return null;
    }

}
