package fylder.recycler.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import fylder.recycler.demo.R;

/**
 * Created by 剑指锁妖塔 on 2016/4/29.
 */
public class DemoAdapter extends BaseRecyclerAdapter<Integer> {

    LayoutInflater inflater;

    int c;

    public DemoAdapter(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        c = 0;
    }

    @Override
    public void refresh(Integer integer) {
        this.c = integer;
        super.refresh(integer);
    }

    @Override
    public void addListData(Integer integer) {
        this.c += integer;
        super.addListData(integer);
    }


    @Override
    public RecyclerView.ViewHolder createExcludeViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_demo, parent, false);
        return new DemoViewHolder(view);
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getExcludeItemCount() {
        return c;
    }

    @Override
    public int getExcludeItemViewType(int realItemPosition) {
        return 0;
    }

    class DemoViewHolder extends RecyclerView.ViewHolder {

        public DemoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
