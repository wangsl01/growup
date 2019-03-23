package design.proxy.custom;

import java.io.File;
import java.io.InputStream;


/**
 * @Author wangsl
 * @Date Create In 10:38 2019/3/22
 * @Description:
 */
public class ToyImpl implements Toy {
    @Override
    public void lamp() {
        System.out.println("lamp is lighting!!!!");
    }


    public Object played(int i,File f) {
        System.out.println("this toy is playing!!!!"+i);
        return new Integer(i);
    }


    public void voice() {
        System.out.println("voice voice voice!!!!");
    }
}
