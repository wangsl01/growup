package design.proxy.custom;

import java.lang.reflect.Method;

/**
 * @Author wangsl
 * @Date Create In 10:54 2019/3/22
 * @Description:
 */
public interface CustomInvocationHandler {

   Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
