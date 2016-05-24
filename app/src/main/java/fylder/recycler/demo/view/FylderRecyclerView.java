package fylder.recycler.demo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 添加loading监听
 * <p/>
 * 添加手势滑动只能在向上滑触发
 * <p/>
 * Created by 剑指锁妖塔 on 15-11-24.
 */
public class FylderRecyclerView extends RecyclerView {

    boolean isDown = false; //手势是否下滑
    private LinearLayoutManager mLayoutManager;
    private GestureDetector mGestureDetector;

    boolean isLoading = false; //判断是否正在刷新或加载

    public FylderRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if (layout instanceof LinearLayoutManager)
            this.mLayoutManager = (LinearLayoutManager) layout;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    /**
     * 设置监听事件
     */
    public void setLoadingListener(final LoadingListener listener) {

        this.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int visibleItemCount = mLayoutManager.getChildCount();
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                if (visibleItemCount > 1 && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem >= totalItemCount - 1) {
                    if (!isDown && !isLoading()) {
                        setLoading(true);
                        listener.onLoading();
                    } else {
                        Log.w("123", "onLoading");
                    }
                }
            }
        });


        mGestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                //distanceY为负数则是向下
                if (distanceY < 0) {
                    isDown = true;
                } else {
                    isDown = false;
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                return false;
            }
        });

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }

    public interface LoadingListener {

        /**
         * 触发加载更多
         */
        void onLoading();
    }
}
