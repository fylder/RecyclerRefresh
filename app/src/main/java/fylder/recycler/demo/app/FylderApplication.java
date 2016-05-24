package fylder.recycler.demo.app;

import android.app.Application;

import com.orhanobut.logger.Logger;

/**
 * Created by 剑指锁妖塔 on 2016/4/29.
 */
public class FylderApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("log_tag");
    }
}
