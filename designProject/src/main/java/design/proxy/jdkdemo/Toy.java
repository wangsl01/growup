package design.proxy.jdkdemo;

import java.io.File;

/**
 * @Author wangsl
 * @Date Create In 10:37 2019/3/22
 * @Description:
 */
public interface Toy {

    void lamp();

    void played();

    Object voice(int i,File file);
}
