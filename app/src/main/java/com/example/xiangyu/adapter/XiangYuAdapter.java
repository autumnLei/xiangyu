package com.example.xiangyu.adapter;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xiangyu.R;
import com.example.xiangyu.entity.Message;
import com.example.xiangyu.global.MyApplication;
import com.example.xiangyu.ui.MessageActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/27.
 */

public class XiangYuAdapter extends RecyclerView.Adapter<XiangYuAdapter.ViewHolder> {

    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    //从XiangYuActivity过来的数据
    private List<Message> mList;

    private static View mHeaderView;
    private static View mFooterView;

    //只有初始化item会用到
    private ViewGroup mparent;

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text;


        public ViewHolder(View view) {
            super(view);
            if (view == mHeaderView || view == mFooterView){
                return;
            }
            image = (ImageView) view.findViewById(R.id.message_image);
            text = (TextView) view.findViewById(R.id.message_text);
        }

    }

    public XiangYuAdapter(List<Message> messageList) {
        mList = messageList;
    }

    //HearderView和FooterView的get和set函数
    public View getmHeaderView() {
        return mHeaderView;
    }

    public void setmHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
        notifyItemInserted(0);
    }

    public View getmFooterView() {
        return mFooterView;
    }

    public void setmFooterView(View mFooterView) {
        this.mFooterView = mFooterView;
        notifyItemInserted(getItemCount()-1);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        mparent = parent;
        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        }
        else if(mFooterView != null && viewType == TYPE_FOOTER){
            return new ViewHolder(mFooterView);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.xiangyu_item, parent, false);
        final ViewHolder holder = new ViewHolder(layout);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = mList.get(holder.getAdapterPosition()-1);
                Intent intent = new Intent(MyApplication.getContext(), MessageActivity.class);
                intent.putExtra(MessageActivity.MESSAGE_NAME, message.getText());
                intent.putExtra(MessageActivity.MESSAGE_IMAGE_ID, message.getIamgeId());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER)
            return;
        if (holder instanceof ViewHolder) {
            //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
            Message message = mList.get(position - 1);
            Glide.with(MyApplication.getContext()).load(message.getIamgeId()).into(holder.image);
            holder.text.setText(message.getText());
        }
    }


    /** 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    * */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null){
            return TYPE_NORMAL;
        }
        if (position == 0){
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount()-1){
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if(mHeaderView == null && mFooterView == null){
            return mList.size();
        }else if(mHeaderView == null){
            return mList.size() + 1;
        }else if (mFooterView == null){
            return mList.size() + 1;
        }else {
            return mList.size() + 2;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position);
    }
}
