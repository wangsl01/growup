package design.singleton;

/**
 * @Author:wangsl
 * @Description: 该类为线程不安全的单例模式，不安全点在18行。线程并发执行18行时，可能会创建多个对象
 *              除线程不安全外还可以用反射调用私有构造器创建新对象 ，也可以序列化后再反序列化创建新对象
 * @Date:22:122019/3/18
 */
public class SingletonLazyUnsafe {


    private static SingletonLazyUnsafe singletonLazyUnsafe;

    private SingletonLazyUnsafe() {
    }

    public static SingletonLazyUnsafe getInstance() {
        if (singletonLazyUnsafe == null) {
            singletonLazyUnsafe = new SingletonLazyUnsafe();
        }
        return singletonLazyUnsafe;
    }

}
