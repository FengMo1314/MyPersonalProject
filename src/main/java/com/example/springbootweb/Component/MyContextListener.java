package com.example.springbootweb.Component;


import com.example.springbootweb.util.SSHConnection;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
//@WebListener
//@Component
public class MyContextListener implements ServletContextListener {
    private SSHConnection conexionssh;

    public MyContextListener() {
        super();
    }


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Context initialized ... !");
        try {
            conexionssh = new SSHConnection();
            conexionssh.createSSH();
        } catch (Throwable e) {
            e.printStackTrace(); // 连接失败
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Context destroyed ... !");
        conexionssh.closeSSH();//断开ssh连接
    }
}
