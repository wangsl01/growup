package design.observer;

import com.google.common.eventbus.EventBus;

/**
 * @Author wangsl
 * @Date Create In 15:20 2019/4/1
 * @Description:
 */
public class MessageCenter {

    private static EventBus messageEventBus = new EventBus();

    public static void postMessage(Object message){
        messageEventBus.post(message);
    }

    static public void registListen(Object obj){
        messageEventBus.register(obj);
    }

    static public void unregistListen(Object obj){
        messageEventBus.unregister(obj);
    }
}
