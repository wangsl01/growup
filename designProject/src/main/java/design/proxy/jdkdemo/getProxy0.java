package design.proxy.jdkdemo;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author wangsl
 * @Date Create In 11:52 2019/3/22
 * @Description:
 */
public class getProxy0 {

    public static void main(String[] args) throws IOException {

        byte[] bytes = ProxyGenerator
                .generateProxyClass("$Proxy0", new Class<?>[]{Toy.class});

        String pathDir = "E:";
        String path = "\\$Proxy0.class";
        File f = new File(pathDir);
        if (!f.exists()) {
            f.mkdir();
        }
        path = f.getAbsolutePath() + path;
        f = new File(path);
        if (f.exists()) {
            f.delete();
        }
        f.createNewFile();

        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}