package com.wonders.health.venus.open.user.module.home.registration;


import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.entity.event.RegistrationEvent;

/**
 * Created by sunning on 16/1/9.
 * 继承此类发布FinishEvent的Activity都会Finish
 */
public abstract class UnifyFinishActivity extends BaseActivity {

    public void onEvent(RegistrationEvent finishEvent){
        if(!finishEvent.isCancel){
            this.finish();
        }
    }
}
