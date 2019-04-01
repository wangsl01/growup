package design.observer;

import com.google.common.eventbus.Subscribe;

/**
 * @Author wangsl
 * @Date Create In 15:29 2019/4/1
 * @Description:
 */
public class User2 extends User {
    @Subscribe
    public void receiveMessage(String msg) {
        System.out.println(msg);
        if (msg == null || msg.indexOf("#") == -1)
            return;
        String[] messages = msg.split("#");
        if (comparedMessageMaster(this, messages[1]))
            System.out.println("用户2接收通知消息：" + messages[0]);
    }


}