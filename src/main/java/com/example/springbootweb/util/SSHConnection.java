package com.example.springbootweb.util;


import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class SSHConnection {
    //本地的ssh中的knownhost文件路径
    private final String SSH_PATH_FILE_KNOWN_HOSTS = "C:\\Users\\36082\\.ssh\\known_hosts";
    //本地的ssh密钥路径
    private final String SSH_PATH_FILE_PRIVATE_KEY = "C:\\Users\\36082\\.ssh\\aliyun-root.pem";
    //ssh连接的用户名
    private final String SSH_USER = "root";
    //ssh远程连接的ip地址
    private final String SSH_REMOTE_SERVER = "8.130.22.229";
    //ssh连接的端口号，一般默认为22
    private final Integer SSH_REMOTE_PORT = 22;
    //SSH使用密码
    //private String sshPassword;

    //本地mysql发起连接的IP地址
    private final String MYSQL_REMOTE_SERVER = "localhost";
    //本地数据库连接时用的端口号(不能填3306)
    private final Integer LOCAl_PORT = 3307;
    //远程数据库端口用的端口号
    private final Integer REMOTE_PORT = 3306;

    //com.jcraft.jsch.Session;
    private Session session;

    /**
     * 关闭ssh连接
     */
    public void closeSSH() {
        session.disconnect();
    }

    /**
     * 创建ssh连接
     */
    public void createSSH() throws JSchException {
        JSch jSch = new JSch();

        //下面这两个设置是在公钥模式需要设置的，非公钥模式不需要进行设置
        //设置known_hosts文件路径，如：~/.ssh/known_hosts（known_hosts中存储是已认证的远程主机host key）
        jSch.setKnownHosts(SSH_PATH_FILE_KNOWN_HOSTS);
        //设置私钥
        jSch.addIdentity(SSH_PATH_FILE_PRIVATE_KEY);

        session = jSch.getSession(SSH_USER, SSH_REMOTE_SERVER, SSH_REMOTE_PORT);

        //如果是密码模式需要设置密码
        //session.setPassword(sshPassword);

        //设置连接过程不校验known_hosts文件中的信息
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        //ssh 建立连接！
        session.connect();

        //根据安全策略，您必须通过转发端口进行连接
        session.setPortForwardingL(LOCAl_PORT, MYSQL_REMOTE_SERVER, REMOTE_PORT);
    }
}
