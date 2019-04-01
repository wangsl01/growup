package design.observer;

import com.google.common.eventbus.EventBus;

/**
 * @Author wangsl
 * @Date Create In 15:29 2019/4/1
 * @Description:
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {


        User1 user1 = new User1();
        User2 user2 = new User2();


        MessageCenter.registListen(user1);
        MessageCenter.registListen(user2);


        String message = "Your anwser was watched #User1";
        MessageCenter.postMessage(message);

        message = "Your question has bean one anwser #User2";
        // 只有注册的参数类型为String的方法会被调用
        MessageCenter.postMessage(message);

//        MessageCenter.unregistListen(user2);

    }
}