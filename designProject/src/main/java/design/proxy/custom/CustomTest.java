package design.proxy.custom;


import design.proxy.jdkdemo.ToyInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Author wangsl
 * @Date Create In 10:39 2019/3/22
 * @Description:
 */
public class CustomTest {

    public static void main(String[] args) throws Throwable {

        CustomInvocationHandler invocationHandler = new CustomToyInvocationHandler(new ToyImpl());

        Toy toy = (Toy) CustomProxy.newProxyInstance(Toy.class.getClassLoader(),new Class[]{Toy.class},invocationHandler);
        toy.lamp();
    }
}
