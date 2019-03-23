package design.proxy.jdkdemo;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @Author wangsl
 * @Date Create In 10:39 2019/3/22
 * @Description:
 */
public class Test {

    public static void main(String[] args) throws IOException {

        InvocationHandler invocationHandler = new ToyInvocationHandler();
        ((ToyInvocationHandler) invocationHandler).setObject(new ToyImpl());

        Toy toy = (Toy) Proxy.newProxyInstance(Toy.class.getClassLoader(), new Class[]{Toy.class}, invocationHandler);
//        toy.lamp();
//        getProxyClassCode(0,Toy.class);


        List toy2 = (List) Proxy.newProxyInstance(List.class.getClassLoader(), new Class[]{List.class}, invocationHandler);
//        toy2.size();
        getProxyClassCode(1,List.class);
        System.out.println(toy == toy2);
    }

    private static void getProxyClassCode(int i,Class<?> clazz) throws IOException {
        byte[] bytes = ProxyGenerator
                .generateProxyClass("$Proxy0", new Class<?>[]{List.class});

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
