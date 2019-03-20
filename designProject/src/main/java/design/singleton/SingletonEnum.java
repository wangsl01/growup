package design.singleton;

/**
 * @Author:wangsl
 * @Description: 该类为线程安全的单例模式，线程的安全性由枚举的特性保证，
 *              不可以用反射调用私有构造器创建新对象 ，不可以序列化后再反序列化创建新对象，不可以实现克隆接口
 * @Date:22:282019/3/18
 */
public enum SingletonEnum {

    INSTANCE;
    public void whateverMethod() {

    }
}
