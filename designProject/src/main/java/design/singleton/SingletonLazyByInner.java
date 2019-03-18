package design.singleton;

/**
 * @Author:wangsl
 * @Description: 该类为线程安全的单例模式，线程的安全性由静态内部类加载机制保证，
 *  *             可以序列化后再反序列化创建新对象
 * @Date:22:242019/3/18
 */
public class SingletonLazyByInner {

    private SingletonLazyByInner() {}

    private static class SingletonInstance {
        private static final SingletonLazyByInner INSTANCE = new SingletonLazyByInner();
    }

    public static SingletonLazyByInner getInstance() {
        return SingletonInstance.INSTANCE;
    }
}
