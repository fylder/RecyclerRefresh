package fylder.recycler.demo.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.orhanobut.logger.Logger;

import fylder.recycler.demo.R;
import fylder.recycler.demo.tools.DisplayTools;
import fylder.recycler.demo.tools.anim.AnimatorPath;
import fylder.recycler.demo.tools.anim.PathEvaluator;
import fylder.recycler.demo.tools.anim.PathPoint;


/**
 * 大鱼吃小鱼的故事
 * *
 * eatStart()   开始游走gank
 * eatEnd()     333
 * <p/>
 * Created by 剑指锁妖塔 on 16-1-14.
 */
public class FishView extends RelativeLayout {

    RelativeLayout fishLay;
    ImageView shark;
    ImageView fish1, fish2;

    int fish1X = 0;
    int fish1Y = 0;
    int fish2X = 0;
    int fish2Y = 0;

    private int sharkX = 0;
    // private int sharkY = 0;

    public FishView(Context context) {
        this(context, null);
    }

    public FishView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FishView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FishView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.fish_lay, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        fishLay = (RelativeLayout) findViewById(R.id.eating_fish_lay);
        shark = (ImageView) findViewById(R.id.fish_shark);
        fish1 = (ImageView) findViewById(R.id.by_eat_fish);
        fish2 = (ImageView) findViewById(R.id.by_eat_fish2);
    }

    AnimatorSet mAnimatorSet;
    ObjectAnimator eatFishAnim;
    boolean isStart = true;// -->-->--

    boolean isGo = true;

    /**
     * 停止游走gank
     */
    public void eatEnd() {
        isGo = false;
        shark.setX(sharkX);
        if (eatFishAnim != null) {
            if (eatFishAnim.isStarted()) {
                eatFishAnim.cancel();
            }
        }
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
        }
    }

    int repeatFlag = 1;

    /**
     * 开始游走
     */
    public void eatStart() {
        if (fish1X == 0 && fish1Y == 0) {
            fish1X = (int) fish1.getX();
            fish1Y = (int) fish1.getY();
        }
        if (fish2X == 0 && fish2Y == 0) {
            fish2X = (int) fish2.getX();
            fish2Y = (int) fish2.getY();
        }
        // Log.i("123", "fish1:(" + fish1X + "," + fish1Y + ")" + " ~ fish2:(" + fish2X + "," + fish2Y + ")");
        isGo = true;
        if (eatFishAnim == null) {
            eatFishAnim = getSharkAnimator();
        }
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(eatFishAnim);
        if (mAnimatorSet.isStarted()) {
            Log.i("1234", "动画正在运行");
            mAnimatorSet.cancel();
            mAnimatorSet.start();
        } else {
            mAnimatorSet.start();
        }

    }

    /**
     * //@param isFirst true:从左边跳起    false：从右边跳起
     */
    private void fishMove(final View view, final int x, final int y) {
        int h = fishLay.getHeight();
        int w = DisplayTools.dp2px(getContext(), 20);
        AnimatorPath path = new AnimatorPath();
        path.moveTo(x, y);//初始化坐标

        path.curveTo((float) (x + 0.5 * w), y - h, x + 2 * w, y - h, x + 3 * w, y);//向右跳跃

        ObjectAnimator a = getFishAnimator(view, path, x, y);
        a.start();

    }

    /**
     * //@param isFirst true:从左边跳起    false：从右边跳起
     */
    private void secondMove(final View view, final int x, final int y) {
        int h = fishLay.getHeight();
        int w = DisplayTools.dp2px(getContext(), 20);
        AnimatorPath path = new AnimatorPath();
        path.moveTo(x, y);//初始化坐标
        path.curveTo((float) (x - 0.5 * w), y - h, x - 2 * w, y - h, x - 3 * w, y);//向左跳跃
        ObjectAnimator a = getFishAnimator(view, path, x, y);
        a.start();

    }

    /**
     * 配置鲨鱼的gank路线
     * <p/>
     * 反复循环
     */
    private ObjectAnimator getSharkAnimator() {

        // DisplayMetrics dm = new DisplayMetrics();
        // getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = DisplayTools.getWidth(getContext());
        if (sharkX == 0) {
            sharkX = (int) shark.getX();
        }

        final int xStart = sharkX;
        final int xEnd = screenWidth - shark.getWidth() - DisplayTools.dp2px(getContext(), 20);

        ObjectAnimator eatFishAnim = ObjectAnimator.ofFloat(shark, "x", xStart, xEnd);
        eatFishAnim.setRepeatCount(ValueAnimator.INFINITE);
        eatFishAnim.setRepeatMode(ValueAnimator.REVERSE);
        eatFishAnim.setDuration(2000);
        eatFishAnim.setInterpolator(new AccelerateDecelerateInterpolator());

        eatFishAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!isGo) {
                    Logger.i("终止动画");
                    animation.cancel();
                }
                float value = (float) animation.getAnimatedValue();
                int xt = (int) value;
                if (xt > xEnd - 2 && repeatFlag == 1 && isStart) {
                    //结束端逆流
                    shark.setImageResource(R.mipmap.loading_shark2);
                    isStart = false;
                    secondMove(fish2, fish2X, fish2Y);
                } else if (xt < xStart + 2 && repeatFlag == 2 && !isStart) {
                    //开始端顺流
                    shark.setImageResource(R.mipmap.loading_shark);
                    isStart = true;
                    fishMove(fish1, fish1X, fish1Y);
                }
            }
        });

        eatFishAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                repeatFlag = 1;
                isStart = true;
                shark.setImageResource(R.mipmap.loading_shark);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animation.cancel();
                repeatFlag = 1;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (repeatFlag == 1) {
                    repeatFlag = 2;
                } else {
                    repeatFlag = 1;
                }
            }
        });
        return eatFishAnim;
    }

    /**
     * 小鱼快跳
     * 配置小鱼的浪路线
     *
     * @param path 贝赛尔曲线
     * @param x    原始X坐标
     * @param y    原始Y坐标
     */
    private ObjectAnimator getFishAnimator(final View view, AnimatorPath path, final int x, final int y) {

        ObjectAnimator anim = ObjectAnimator.ofObject(this, "fabLoc", new PathEvaluator(), path.getPoints().toArray());
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(800);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PathPoint point = (PathPoint) animation.getAnimatedValue();
                view.setX(point.mX);
                view.setY(point.mY);
            }
        });

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setX(x);
                view.setY(y);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return anim;
    }
}
