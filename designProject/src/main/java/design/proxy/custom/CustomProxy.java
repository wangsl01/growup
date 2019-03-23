package design.proxy.custom;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author wangsl
 * @Date Create In 10:53 2019/3/22
 * @Description:
 */
public class CustomProxy {

    private static Map<String,String> wrapperMap = new HashMap<>();

    static{
        wrapperMap.put("int","Integer");
        wrapperMap.put("short","Short");
        wrapperMap.put("long","Long");
        wrapperMap.put("Char","Char");
        wrapperMap.put("float","Float");
        wrapperMap.put("double","Double");
        wrapperMap.put("boolean","Boolean");
        wrapperMap.put("byte","Byte");
    }



    private final static String linenext = "\r\n";

    protected CustomInvocationHandler h;

    private static AtomicInteger classNum = new AtomicInteger(0);

    private static Map<String, Method> methodMap = new HashMap<>();

    private static Map<String, String> methodStringMap = new HashMap<>();

    private static Map<String, String> methodSimpleStringMap = new HashMap<>();


    public CustomProxy() {
    }

    public CustomProxy(CustomInvocationHandler customInvocationHandler) {
        this.h = customInvocationHandler;
    }

    public static Object newProxyInstance(ClassLoader classLoader, Class<?>[] interfaceArr, CustomInvocationHandler customInvocationHandler) throws Exception {
        String src = createProxyClassSource(classLoader, interfaceArr);
        System.out.println(src);
        Class<?> clazz = compile("design.proxy.custom.$Proxy" + classNum.get(), src);
        Constructor<?> cons = clazz.getDeclaredConstructor(new Class[]{CustomInvocationHandler.class});
        return cons.newInstance(new Object[]{customInvocationHandler});
    }


    private static String createProxyClassSource(ClassLoader classLoader, Class<?>[] interfaceArr) throws Exception {

//        if (classLoader == null) {
//            throw new Exception("CustomProxy.createProxyClassSource ---> classLoader must be not null");
//        }
        if (interfaceArr == null) {
            throw new Exception("CustomProxy.createProxyClassSource ---> interfaceArr must be not null");
        }

        StringBuilder stringBuilder = new StringBuilder(4096);

        stringBuilder.append("package design.proxy.custom;" + linenext);
        stringBuilder.append("import java.lang.reflect.InvocationHandler;" + linenext);
        stringBuilder.append("import java.lang.reflect.Method;" + linenext);
        stringBuilder.append("import java.lang.reflect.Proxy;" + linenext);
        stringBuilder.append("import java.lang.reflect.UndeclaredThrowableException;" + linenext);

        //构建类默认继承CustomProxy，描述类头
        stringBuilder.append("public class $Proxy" + classNum.get() + " extends CustomProxy implements ");
        for (int i = 0; i < interfaceArr.length; i++) {//处理类需要实现的接口开始
            Class clazz = interfaceArr[i];
            System.out.println(clazz.getSimpleName());
            stringBuilder.append(clazz.getSimpleName());
            if (i < interfaceArr.length - 1) {
                stringBuilder.append(", ");// 实现的接口
            }
        }
        stringBuilder.append("{" + linenext + linenext);//处理类需要实现的接口结束

        int increate = 0;
        for (Class clazz : interfaceArr) {//循环需要实现的接口

            Method[] proxyedObjMethods = clazz.getMethods();//接口的方法数组
            for (Method proxyMethod : proxyedObjMethods) { //生成需要执行方法的Method持有对象(Method对象)

                String signature = methodSignature(proxyMethod);
                if (methodMap.containsKey(signature)) {
                    continue;
                }
                String tempMethodObjectName = "m"+increate++;
                String methodString = "private static Method " + tempMethodObjectName;
                methodMap.put(signature, proxyMethod);
                methodStringMap.put(signature, methodString);
                methodSimpleStringMap.put(signature, tempMethodObjectName);
                //生成方法
                stringBuilder.append("public final " + methodSignature(proxyMethod));


//                if (proxyMethod.getExceptionTypes() != null && proxyMethod.getExceptionTypes().length > 0) {
//                    stringBuilder.append(" throws Throwable ");//如果接口抛出异常，则生成异常代码
//                }
                stringBuilder.append("{").append(linenext);
                //生成方法内部代码
                if(!"void".equals(proxyMethod.getReturnType().getSimpleName())) {
                    stringBuilder.append("try{").append(linenext);
                    stringBuilder.append("return this.h.invoke(this,").append(tempMethodObjectName).append(",");

                    String paramString = createMethodParameters(proxyMethod);
                    if (paramString.length() > 0) {
                        stringBuilder.append(createMethodParametersClass(proxyMethod));
                    } else {
                        stringBuilder.append(" null");
                    }
                    stringBuilder.append(");").append(linenext).append("}catch(Throwable e){").append(linenext);
                    stringBuilder.append("e.printStackTrace();").append(linenext);
                    stringBuilder.append("}").append(linenext);
                    stringBuilder.append("return null;").append(linenext);
                }else{
                    stringBuilder.append("try{").append(linenext);
                    stringBuilder.append(" this.h.invoke(this,").append(tempMethodObjectName).append(",");
                    String paramString = createMethodParameters(proxyMethod);
                    if (paramString.length() > 0) {
                        stringBuilder.append(" new Class[]{" + paramString + "}");
                    } else {
                        stringBuilder.append(" null");
                    }
                    stringBuilder.append(");").append(linenext).append("}catch(Throwable e){").append(linenext);
                    stringBuilder.append("e.printStackTrace();").append(linenext);
                    stringBuilder.append("}").append(linenext);
                }

                //生成方法内部代码结束
                stringBuilder.append(linenext);

                //方法结束
                stringBuilder.append("}").append(linenext);
            }
        }
        stringBuilder.append(linenext).append(linenext);

        stringBuilder.append("public $Proxy" + classNum.get()+"(CustomInvocationHandler customInvocationHandler){").append(linenext);

        stringBuilder.append("super(customInvocationHandler);").append(linenext);
        stringBuilder.append("}").append(linenext);

        stringBuilder.append(linenext).append(linenext).append(linenext);

        Iterator<String> it = methodStringMap.keySet().iterator();
        while (it.hasNext()) {
            String signature = it.next();
            String methodString = methodStringMap.get(signature);

            stringBuilder.append(methodString).append(";").append(linenext);
        }


        stringBuilder.append("static {").append(linenext);
        stringBuilder.append("try {").append(linenext);
        Iterator<String> itobj = methodMap.keySet().iterator();
        while (itobj.hasNext()) {
            String signature = itobj.next();
            Method method = methodMap.get(signature);
            String methodvar = methodSimpleStringMap.get(signature);
            stringBuilder.append(methodvar).append("=").append("Class.forName(\"")
                    .append(method.getDeclaringClass().getName()).append("\").getMethod(\"")
                    .append(method.getName()).append("\",");

            stringBuilder.append(createMethodParametersClass(method));

            stringBuilder.append(");").append(linenext);
        }
        stringBuilder.append("}catch(Throwable e){").append(linenext);
        stringBuilder.append("e.printStackTrace();").append(linenext);
        stringBuilder.append("}").append(linenext);
        stringBuilder.append("}").append(linenext);
        stringBuilder.append("}").append(linenext);


        methodMap.clear();
        methodStringMap.clear();
        methodSimpleStringMap.clear();
        return stringBuilder.toString();
    }


    private static String createMethodParameters(Method proxyMethod) {
        StringBuilder stringBuilder = new StringBuilder(2048);
        Class<?>[] paramTypeClazzes = proxyMethod.getParameterTypes();
        if (paramTypeClazzes != null) {//生成方法参数
            for (int k = 0; k < paramTypeClazzes.length; k++) {
                Class<?> paraClazz = paramTypeClazzes[k];
                String paramName = paraClazz.getName();
                String wrap = wrapperMap.get(paramName);
                if(wrap!=null){
                    stringBuilder.append(" " + paramName+" "+ paramName.substring(0,1));
                }else {
                    String paramNameLower = firstCharToLower(paraClazz.getSimpleName());
                    stringBuilder.append(" " + paramName+" "+ paramNameLower);
                }

                if (k < paramTypeClazzes.length - 1) {
                    stringBuilder.append(", ");
                }
            }
        }
        return stringBuilder.toString();
    }

    //得到方法签名
    private static String methodSignature(Method proxyMethod) {
        String param = createMethodParameters(proxyMethod);
        String methodParam = proxyMethod.getName() + "(" + param + ")";
        String signature = proxyMethod.getReturnType().getSimpleName() + " " + methodParam;
        return signature;
    }

    private static String firstCharToLower(String source) {
        char[] strChar = source.toCharArray();
        strChar[0] = (char) (Integer.valueOf(strChar[0]) + 32);
        String newStr = new String(strChar);
        return newStr;
    }

    private static String createMethodParametersClass(Method proxyMethod) {
        StringBuilder stringBuilder = new StringBuilder(2048);
        Class<?>[] paramTypeClazzes = proxyMethod.getParameterTypes();
        if (paramTypeClazzes != null && paramTypeClazzes.length > 0) {
            stringBuilder.append("new Class[]{");
            for (int k = 0; k < paramTypeClazzes.length; k++) {
                Class<?> paraClazz = paramTypeClazzes[k];
                String paramName = paraClazz.getName();

//                String wrap = wrapperMap.get(paramName);
//                if(wrap!=null){
                    stringBuilder.append(paramName+".class");
//                }else {
//                    stringBuilder.append(paramName+".class");
//                }


                if (k < paramTypeClazzes.length - 1) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append("}");
        } else {
            stringBuilder.append(" new Class[0]");
        }
        return stringBuilder.toString();
    }

    private static Class<?> compile(String className, String javaCodes) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        StrSrcJavaObject srcObject = new StrSrcJavaObject(className, javaCodes);
        Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(srcObject);
        String flag = "-d";
        String outDir = "";
        try {
            File classPath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());
            outDir = classPath.getAbsolutePath() + File.separator;
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        Iterable<String> options = Arrays.asList(flag, outDir);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, options, null, fileObjects);
        boolean result = task.call();
        if (result == true) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static class StrSrcJavaObject extends SimpleJavaFileObject {
        private String content;

        StrSrcJavaObject(String name, String content) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.content = content;
        }

        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return content;
        }
    }
}
