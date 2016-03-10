package com.joy.ep.myhttptext;

import android.app.Application;

import com.bugtags.library.Bugtags;
import com.facebook.stetho.Stetho;

/**
 * author   Joy
 * Date:  2016/3/4.
 * version:  V1.0
 * Description:
 */
public class app extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bugtags.start("ea43ad8098f47ba92ed6b2f010efc63b", this, Bugtags.BTGInvocationEventBubble);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
