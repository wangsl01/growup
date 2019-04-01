package design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 */
public class App {
    static ExecutorService executorService = Executors.newFixedThreadPool(5);

    static volatile int position = -1;



    public static void main(String[] args){
        t3();
    }

    public static void t3(){
        Set<String> set1 = new HashSet<>();
        set1.add("1");
        Set<Integer> set2 = new HashSet<>();
        set2.add(1);
        set2.add(2);
        set1.retainAll(set2);
        System.out.println(set1);

    }

    public static void t2(){
//        String str ="1.0";
//        Integer t = Integer.parseInt(str);
//        System.out.println(t.intValue());

        List<String> list = new ArrayList(20);
        System.out.println(list.size());


    }

    public static void t1() throws InterruptedException {

//        Scanner sc = new Scanner(System.in);
//        String str = sc.nextLine();
//        System.out.println(str);

        final List<Integer> resultlist = new ArrayList(120);


        StringBuffer sb = new StringBuffer(2048);
        final int listsize = 200;
        final List<Integer> list = new LinkedList<>();
        Random r = new Random();
        for (int i = 0; i < listsize; i++) {
            while (true) {
                int num = r.nextInt(200);
                if(num > 200) {
                    System.out.println(num);
                }
                if (!list.contains(num)) {
                    list.add(num);
                    sb.append(num).append(",");
                    break;
                }
            }
        }
        System.out.println(sb.toString());

        final CountDownLatch start = new CountDownLatch(5);
    //    CountDownLatch end = new CountDownLatch(1);
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread() {
                public void run() {
                    for (int k = 0; k < listsize; k++) {
                        int num = list.get(k);
                        if (num % 2 != 0) {
                            continue;
                        }
                        String name = this.getName();
                        if(position < k) {
                            synchronized (resultlist) {
                                if(position < k){
                                    position = k;
                                    resultlist.add(num);
                                }
                            }
                        }
                    }
                    start.countDown();
                }
            };
            t.setName(i + "");
            executorService.submit(t);
        }
        start.await();
        System.out.println(resultlist.size());
        sb.delete(0, sb.length());
        for (int i = 0; i < resultlist.size(); i++) {
            sb.append(resultlist.get(i)).append(",");
        }
        System.out.println(sb.toString());

//        Map<Object,Object> map = new HashMap<>();
//        while(true){
//            map.put(new Object(),new Object());
//        }
        // System.out.println( "Hello World!" );
    }
}
