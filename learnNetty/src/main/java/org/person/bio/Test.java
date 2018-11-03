package org.person.bio;

import org.person.bio.custom.CustomerTest;
import org.person.bio.custom.MutilCustomer;
import org.person.bio.server.MutilServer;
import org.person.bio.server.ServerTest;

import java.lang.management.ManagementFactory;

public class Test {
    public static void main(String[] args) {
//        Thread t = new Thread(() ->{
//            CustomerTest ct = new CustomerTest();
//            ct.startCustom();
//        });
//        t.start();
//        Thread t2 = new Thread(()->{
//            ServerTest st = new ServerTest();
//            st.startServer();
//        });
//        t2.start();


        Thread t = new Thread(() ->{
            MutilCustomer mct = new MutilCustomer();
            mct.sendMessage();
        });
        t.start();
        Thread t2 = new Thread(()->{
            MutilServer ms = new MutilServer();
            ms.recieveMessage();
        });
        t2.start();

        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(name);
        String pid = name.split("@")[0];
        System.out.println("Pid is:" + pid);
    }
}
