package fylder.recycler.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fylder.recycler.demo.R;
import fylder.recycler.demo.tools.AnimTools;
import fylder.recycler.demo.view.FishView;

/**
 * 小鱼加载
 * Created by 剑指锁妖塔 on 2016/4/29.
 */
public abstract class BaseRecyclerFishAdapter<T> extends RecyclerView.Adapter {

    private static final int TYPE_FOOTER_VIEW = 100;//尾部布局类型
    private int extraCount = 1;//额外多出来的

    protected final int STATS_EMPTY = 1;      //  空白
    protected final int STATS_LOADING = 2;    //  加载
    protected final int STATS_LOADED = 3;     //  加载完了
    protected final int STATS_END = 4;        //  到最后一条

    Context context;

    protected int STATS = STATS_EMPTY;

    public BaseRecyclerFishAdapter(Context context) {
        this.context = context;
    }

//    public boolean hasFooterView() {
//        return footerView != null;
//    }

    public void refresh(T t) {
        STATS = STATS_EMPTY;
        notifyDataSetChanged();
    }

    public void addListData(T t) {
        STATS = STATS_LOADED;
        notifyDataSetChanged();
    }

    /**
     * 正在加载
     */
    public void loading() {
        STATS = STATS_LOADING;
        notifyDataSetChanged();
    }

    /**
     * 最后一条
     */
    public void ending() {
        STATS = STATS_END;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_FOOTER_VIEW:
                View footerView = LayoutInflater.from(context).inflate(R.layout.footer_fish_lay, parent, false);
                return new FooterViewHolder(footerView);
            default:
                return createExcludeViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_FOOTER_VIEW) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;

            if (STATS == STATS_LOADING) {
                //正在加载中
                footerViewHolder.lay.setVisibility(View.VISIBLE);
                footerViewHolder.lT.setVisibility(View.GONE);
                footerViewHolder.fishView.setVisibility(View.VISIBLE);
                footerViewHolder.lT.setText(R.string.recycler_loading);
                //start
                footerViewHolder.fishView.eatStart();

                AnimTools.show(footerViewHolder.lay);
            } else if (STATS == STATS_LOADED) {
                //end
                footerViewHolder.fishView.eatEnd();
                footerViewHolder.lay.setVisibility(View.GONE);
            } else if (STATS == STATS_END) {
                //加载结束后
                footerViewHolder.lay.setVisibility(View.VISIBLE);
                footerViewHolder.lT.setVisibility(View.VISIBLE);
                footerViewHolder.fishView.setVisibility(View.GONE);
                footerViewHolder.lT.setText(R.string.recycler_load_end);
                footerViewHolder.fishView.eatEnd();
                AnimTools.show(footerViewHolder.lay);
            } else {
                //其余情况隐藏
                footerViewHolder.lay.setVisibility(View.GONE);
                footerViewHolder.fishView.eatEnd();
            }
        } else {
            onBindView(holder, position);
        }
    }

    /**
     * 获取该type的ViewHolder
     */
    public abstract RecyclerView.ViewHolder createExcludeViewHolder(ViewGroup parent, int viewType);


    @Override
    public int getItemCount() {
        return getExcludeItemCount() + extraCount;
    }


    @Override
    public int getItemViewType(int innerPosition) {
        if (getItemCount() - 1 == innerPosition) { // footer
            return TYPE_FOOTER_VIEW;
        } else {
            return getExcludeItemViewType(innerPosition);
        }
    }

    /**
     * 绑定数据
     *
     * @param holder
     */
    public abstract void onBindView(RecyclerView.ViewHolder holder, int position);

    /**
     * （不包括headerView和footerView）
     *
     * @return 获取item的数量
     */
    public abstract int getExcludeItemCount();

    /**
     * 通过realItemPosition得到该item的类型（不包括headerView和footerView）
     *
     * @param realItemPosition 位置
     * @return 得到该item的类型
     */
    public abstract int getExcludeItemViewType(int realItemPosition);


    class FooterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.footer_fish_lay)
        RelativeLayout lay;

        @BindView(R.id.footer_fish)
        FishView fishView;
        @BindView(R.id.footer_fish_text)
        TextView lT;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}