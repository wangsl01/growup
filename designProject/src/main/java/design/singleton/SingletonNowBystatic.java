package design.singleton;

/**
 * @Author:wangsl
 * @Description: 该类为线程安全的单例模式，线程的安全性由静态化块保证，但可以用反射调用私有构造器创建新对象 ，也可以序列化后再反序列化创建新对象
 * @Date:22:102019/3/18
 */
public class SingletonNowBystatic {

    private static SingletonNowBystatic SingletonNowByStatic;

    static{
        SingletonNowByStatic = new SingletonNowBystatic();
    }

    private SingletonNowBystatic(){}

    public static SingletonNowBystatic getInstance(){
        return SingletonNowByStatic;
    }

}
