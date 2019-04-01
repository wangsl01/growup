package design.observer;

import com.google.common.eventbus.Subscribe;

/**
 * @Author wangsl
 * @Date Create In 17:13 2019/4/1
 * @Description:
 */
public abstract class User {

    protected boolean comparedMessageMaster(User sourceObj, String messageFlag) {

        return sourceObj.getClass().getSimpleName().equals(messageFlag);

    }
}
