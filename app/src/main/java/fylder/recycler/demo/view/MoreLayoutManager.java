package fylder.recycler.demo.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * 动态分配span size
 * Created by 剑指锁妖塔 on 2016/4/29.
 */
public class MoreLayoutManager extends GridLayoutManager {

    public MoreLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
        init(spanCount);
    }

    void init(final int spanCount) {

        //分配最后一个享有span size=2
        setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if (position == getItemCount() - 1)
                    return spanCount;
                else {
                    return 1;
                }
            }
        });
    }

}
