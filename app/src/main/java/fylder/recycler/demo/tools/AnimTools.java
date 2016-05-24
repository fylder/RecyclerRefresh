package fylder.recycler.demo.tools;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import fylder.recycler.demo.tools.anim.AnimUtils;


/**
 * Created by 剑指锁妖塔 on 16-1-11.
 */
public class AnimTools {

    /**
     * 显示
     *
     * @param view
     */
    public static void show(View view) {
        show(view, 700);
    }

    /**
     * @param view
     * @param duration
     */
    public static void show(final View view, long duration) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(view, "alpha", 0f, 1f, 1));
        set.setDuration(duration);
        set.start();
    }

    /**
     * 隐藏
     *
     * @param view
     */
    public static void hide(final View view) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 0));
        set.setDuration(700);
        set.start();
    }

    /**
     * 上移动
     *
     * @param view  logo
     * @param view2 input
     */

    public static void moveTop(View view, View view2, View view3) {

        AnimatorSet set = new AnimatorSet();

        set.playTogether(ObjectAnimator.ofFloat(view, "alpha", 1f, 0.7f, 0.7f),
                ObjectAnimator.ofFloat(view, "scaleX", 1, 0.7f),
                ObjectAnimator.ofFloat(view, "scaleY", 1, 0.7f),
                ObjectAnimator.ofFloat(view, "translationY", 0, -view.getHeight() / 3));
        set.setDuration(800);
        set.setInterpolator(new DecelerateInterpolator());
        set.start();


        AnimatorSet set2 = new AnimatorSet();
        set2.playTogether(ObjectAnimator.ofFloat(view2, "translationY", 0, -view.getHeight()));
        set2.setDuration(700);
        set2.setStartDelay(100);
        set2.setInterpolator(new DecelerateInterpolator());
        set2.start();

        AnimatorSet set3 = new AnimatorSet();
        set3.playTogether(ObjectAnimator.ofFloat(view3, "translationY", 0, -view.getHeight()));
        set3.setDuration(700);
        set3.setStartDelay(400);
        set3.setInterpolator(new DecelerateInterpolator());
        set3.start();

    }

    /**
     * 向下移动
     *
     * @param view
     */
    public static void moveBack(View view, View view2,View view3) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(ObjectAnimator.ofFloat(view, "alpha", 0.7f, 1f, 1),
                    ObjectAnimator.ofFloat(view, "scaleX", 0.7f, 1f),
                    ObjectAnimator.ofFloat(view, "scaleY", 0.7f, 1f),
                    ObjectAnimator.ofFloat(view, "translationY", -view.getHeight() / 3, 0));
            set.setDuration(700);//设置动画时间
            set.setInterpolator(new DecelerateInterpolator());
            set.setStartDelay(100);
            set.start();

            AnimatorSet set2 = new AnimatorSet();
            set2.playTogether(ObjectAnimator.ofFloat(view2, "translationY", -view.getHeight(), 0));
            set2.setDuration(700);
            set2.setInterpolator(new DecelerateInterpolator());
            set2.start();

            AnimatorSet set3 = new AnimatorSet();
            set3.playTogether(ObjectAnimator.ofFloat(view3, "translationY", -view.getHeight(), 0));
            set3.setDuration(700);
            set3.setStartDelay(200);
            set3.setInterpolator(new DecelerateInterpolator());
            set3.start();
        }
    }

    /**
     * 上移渐显
     *
     * @param view
     * @param y
     */
    public static void showTop(Context context, View view, int y) {
        showTop(context, view, 0, y);
    }


    public static void showTop(Context context, final View view, int d, int y) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "Y", view.getY() + DisplayTools.dp2px(context, y), view.getY());
        animator.setDuration(400);
        animator.setInterpolator(AnimUtils.getMaterialInterpolator(context));
        animator.setStartDelay(d);
        animator.start();

//        animator.addListener(new AnimatorListenerAdapter() {
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//                view.setVisibility(View.VISIBLE);//显示
//            }
//        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        animator2.setDuration(400);
        animator2.setInterpolator(AnimUtils.getMaterialInterpolator(context));
        animator2.setStartDelay(d);
        animator2.start();
    }


}
