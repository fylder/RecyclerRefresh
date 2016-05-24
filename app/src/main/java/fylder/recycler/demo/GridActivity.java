package fylder.recycler.demo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import fylder.recycler.demo.adapter.DemoAdapter;
import fylder.recycler.demo.view.MoreLayoutManager;
import fylder.recycler.demo.view.FylderRecyclerView;

public class GridActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, FylderRecyclerView.LoadingListener {

    @BindView(R.id.grid_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.grid_recycler)
    FylderRecyclerView recyclerView;

    MoreLayoutManager manager;
    DemoAdapter mAdapter;

    Context mContext;
    Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        ButterKnife.bind(this);
        mContext = this;
        init();
    }


    void init() {
        manager = new MoreLayoutManager(mContext, 3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(manager);
        mAdapter = new DemoAdapter(mContext);
        recyclerView.setAdapter(mAdapter);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLoadingListener(this);

        refreshLayout.setColorSchemeResources(R.color.color1, R.color.color2,
                R.color.color3, R.color.color4);

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
        }, 2000);
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
