package design.singleton;

/**
 * @Author:wangsl
 * @Description: 该类为线程安全的单例模式，线程的安全性由synchronized保证，
 *  *             但可以用反射调用私有构造器创建新对象 ，也可以序列化后再反序列化创建新对象!
 * @Date:22:212019/3/18
 */
public class SingletonLazyByDCL {

    private static volatile SingletonLazyByDCL singletonLazyByDCL;

    private SingletonLazyByDCL() {}

    public static SingletonLazyByDCL getInstance() {
        if (singletonLazyByDCL == null) {
            synchronized (SingletonLazyByDCL.class) {
                if (singletonLazyByDCL == null) {
                    singletonLazyByDCL = new SingletonLazyByDCL();
                }
            }
        }
        return singletonLazyByDCL;
    }
}
