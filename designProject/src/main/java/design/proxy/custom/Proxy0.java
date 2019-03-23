package design.proxy.custom;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
public class Proxy0 extends CustomProxy implements Toy{

    public final void voice(){
        try{
            this.h.invoke(this,m0, null);
        }catch(Throwable e){
            e.printStackTrace();
        }

    }
    public final Object played( int i,  java.io.File file){
        try{
            return this.h.invoke(this,m1,new Class[]{int.class, java.io.File.class});
        }catch(Throwable e){
            e.printStackTrace();
        }
        return null;

    }
    public final void lamp(){
        try{
            this.h.invoke(this,m2, null);
        }catch(Throwable e){
            e.printStackTrace();
        }

    }





    private static Method m1;
    private static Method m2;
    private static Method m0;
    static {
        try {
            m1=Class.forName("design.proxy.custom.Toy").getMethod("played",new Class[]{Integer.class, java.io.File.class});
            m2=Class.forName("design.proxy.custom.Toy").getMethod("lamp", new Class[0]);
            m0=Class.forName("design.proxy.custom.Toy").getMethod("voice", new Class[0]);
        }catch(Throwable e){
            e.printStackTrace();
        }
    }
}