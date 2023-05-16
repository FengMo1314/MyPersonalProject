package com.example.springbootweb.Component;

import com.example.springbootweb.util.SSHConnection;
import com.jcraft.jsch.JSchException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

//@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    public static void ssh() throws JSchException {
        SSHConnection conexionssh = new SSHConnection();
        conexionssh.createSSH();
    }

    /*
     * 本方法一定要返回bean对象
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("dataSource".equals(beanName)) {
            System.out.println(beanName + "对象初始化前...");
            try {
                ssh();
            } catch (JSchException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("1");
            }
        }
        return bean;
    }

    /*
     * 本方法一定要返回bean对象
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
