package design.singleton;

/**
 * @Author:wangsl
 * @Description:   该类为线程安全的单例模式，线程的安全性由synchronized保证，
 *             但可以用反射调用私有构造器创建新对象 ，也可以序列化后再反序列化创建新对象
 * @Date:22:192019/3/18
 */
public class SingletonLazyBySyn {


    private static SingletonLazyBySyn singletonLazyByDCL;

    private SingletonLazyBySyn() {}

    public static synchronized SingletonLazyBySyn getInstance() {
        if (singletonLazyByDCL == null) {
            singletonLazyByDCL = new SingletonLazyBySyn();
        }
        return singletonLazyByDCL;
    }
}
