package design.singleton;

/**
 * @Author:wangsl
 * @Description:  该类为线程安全的单例模式，线程的安全性由类载机制和构造器来保证，但可以用反射调用私有构造器创建新对象 ，也可以序列化后再反序列化创建新对象
 * @Date:22:092019/3/18
 */
public class SingletonNow {

    private static SingletonNow singletonNow = new SingletonNow();

    private SingletonNow(){}


    public static SingletonNow getInstance(){
        return singletonNow;
    }
}
