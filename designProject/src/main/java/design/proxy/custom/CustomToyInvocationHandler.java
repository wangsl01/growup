package design.proxy.custom;

import java.lang.reflect.Method;

/**
 * @Author wangsl
 * @Date Create In 9:38 2019/3/23
 * @Description:
 */
public class CustomToyInvocationHandler implements CustomInvocationHandler {

    private Object obj;

    public CustomToyInvocationHandler(){}

    public CustomToyInvocationHandler(Object obj){
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
