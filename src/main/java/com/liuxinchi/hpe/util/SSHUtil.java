package com.liuxinchi.hpe.util;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.liuxinchi.hpe.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;

public class SSHUtil {
    static String hostname = Constant.SSH_HOSTNAME;
    static int port =Constant.SSH_PORT;
    static String user = Constant.SSH_USER;
    static String password= Constant.SSH_PASSWORD;
    static Connection conn;

    private final static Logger log = LoggerFactory.getLogger(SSHUtil.class);

    public static boolean login(){
        conn = new Connection(hostname,port);
        boolean flag = false;
        try {
            conn.connect();
            flag = conn.authenticateWithPassword(user,password);
            if(flag){
                log.info("SHELL : SSH认证成功" );
            }else{
                log.info("SHELL : SSH认证失败，请检查用户名和密码" );
            }
        } catch (IOException e) {
            log.info("SHELL : SSH连接错误" );
            conn.close();
            e.printStackTrace();
        }
        return flag;
    }

    public static String exec(String command) throws InterruptedException, IOException {
        String result = "";
        try {
            Session session = conn.openSession();
            session.requestPTY("vt100", 80, 24, 640, 480, null);
            session.execCommand(command);
            result = processStdout(session.getStdout(), "UTF-8");
            if (StringUtils.isEmpty(result)) {
                result = processStdout(session.getStderr(), "UTF-8");
            }
            session.close();
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String processStdout(InputStream in, String charset){
        InputStream stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
