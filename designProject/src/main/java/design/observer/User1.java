package design.observer;

import com.google.common.eventbus.Subscribe;

/**
 * @Author wangsl
 * @Date Create In 15:21 2019/4/1
 * @Description:
 */
public class User1 extends User {

    @Subscribe
    public void receiveMessage(String msg) {
        System.out.println(msg);
        if (msg == null || msg.indexOf("#") == -1)
            return;
        String[] messages = msg.split("#");
        if (comparedMessageMaster(this, messages[1]))
        System.out.println("用户1接收消息通知：" + messages[0]);
    }


}
