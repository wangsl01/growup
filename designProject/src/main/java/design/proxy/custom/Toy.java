package design.proxy.custom;

import java.io.File;

/**
 * @Author wangsl
 * @Date Create In 10:37 2019/3/22
 * @Description:
 */
public interface Toy {

    void lamp() throws Throwable;

    Object played(int i,File file);

    void voice();
}
