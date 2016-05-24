package fylder.recycler.demo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import fylder.recycler.demo.adapter.FishAdapter;
import fylder.recycler.demo.view.FylderRecyclerView;

/**
 * Created by 剑指锁妖塔 on 2016/4/29.
 */
public class FishActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, FylderRecyclerView.LoadingListener {

    @BindView(R.id.demo_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.demo_recycler)
    FylderRecyclerView recyclerView;

    LinearLayoutManager manager;
    FishAdapter mAdapter;

    Context mContext;
    Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
        mContext = this;
        init();
    }


    void init() {
        manager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(manager);
        mAdapter = new FishAdapter(mContext);
        recyclerView.setAdapter(mAdapter);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLoadingListener(this);

        refreshLayout.setColorSchemeResources(R.color.color1, R.color.color2,
                R.color.color3, R.color.color4);

//        refreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                refreshLayout.setRefreshing(true);
//                refresh();
//            }
//        });
        mHandler.postDelayed(new Runnable() {
        @Override
        public void run() {
            refreshLayout.setRefreshing(true);
            refresh();
        }
    }, 100);
}


    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoading() {
        mAdapter.loading();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
                recyclerView.setLoading(false);
                if (mAdapter.getItemCount() > 21) {
                    mAdapter.ending();
                } else {
                    mAdapter.addListData(7);
                }
            }
        }, 3000);
    }

    void refresh() {
        if (!recyclerView.isLoading()) {
            recyclerView.setLoading(true);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(false);
                    recyclerView.setLoading(false);
                    mAdapter.refresh(7);
                }
            }, 1000);
        }
    }

}
