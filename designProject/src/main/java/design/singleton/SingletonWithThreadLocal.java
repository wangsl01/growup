package design.singleton;

/**
 * @Author:wangsl
 * @Description: 该类单个线程可以保证单例，通过threadlocal的特性把单例对象保存在线程的threadlocalMap中。
 * @Date:22:362019/3/18
 */
public class SingletonWithThreadLocal {

    private SingletonWithThreadLocal(){}

    private static ThreadLocal<SingletonWithThreadLocal> threadLocalThreadLocal = new ThreadLocal<SingletonWithThreadLocal>(){
        @Override
        protected SingletonWithThreadLocal initialValue() {
            return new SingletonWithThreadLocal();
        }
    };

    public static SingletonWithThreadLocal getInstance(){
        return threadLocalThreadLocal.get();
    }
}
