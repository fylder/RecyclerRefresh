package fylder.recycler.demo.tools.anim;

import android.content.Context;
import android.os.Build;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by 剑指锁妖塔 on 15-11-2.
 */
public class AnimUtils {

    private static Interpolator gusterpolator;

    public static Interpolator getMaterialInterpolator(Context ctx) {
        if (gusterpolator == null) {
            synchronized (AnimUtils.class) {
                if (gusterpolator == null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        gusterpolator = AnimationUtils.loadInterpolator(ctx, android.R.interpolator.fast_out_slow_in);
                    }else{
                        gusterpolator=new DecelerateInterpolator();
                    }
                }
            }
        }
        return gusterpolator;
    }
}
