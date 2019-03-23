package design.proxy.jdkdemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author wangsl
 * @Date Create In 10:43 2019/3/22
 * @Description:
 */
public class ToyInvocationHandler implements InvocationHandler {

    private Object obj;

    public ToyInvocationHandler(){}

    public ToyInvocationHandler(Object obj){
        this.obj = obj;
    }

    public void setObject(Object obj){
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(obj,args);
    }
}
