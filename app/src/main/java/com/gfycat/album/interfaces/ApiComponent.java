package com.gfycat.album.interfaces;

import com.gfycat.album.activities.HandleShareActivity;
import com.gfycat.album.activities.LoginActivity;
import com.gfycat.album.activities.SplashActivity;
import com.gfycat.album.modules.ApplicationModule;
import com.gfycat.album.modules.RetrofitModule;
import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by Michael Yoon Huh on 1/28/2017.
 */

@Singleton
@Component(modules={ApplicationModule.class, RetrofitModule.class})
public interface ApiComponent {
    void inject(SplashActivity activity);
    void inject(LoginActivity activity);
    void inject(HandleShareActivity activity);
}