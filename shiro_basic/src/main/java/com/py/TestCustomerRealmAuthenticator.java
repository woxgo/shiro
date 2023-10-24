package com.py;

import com.py.realm.CustomerRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * 使用自定义realm
 */
public class TestCustomerRealmAuthenticator {
    public static void main(String[] args) {
        //创建securityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //设置自定义realm
        securityManager.setRealm(new CustomerRealm());
        //给全局安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //通过安全工具类获取subject
        Subject subject = SecurityUtils.getSubject();
        //5.创建token
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123");
        try {
            System.out.println("认证状态：" + subject.isAuthenticated());
            subject.login(token);//用户认证
            System.out.println("登录成功");
            System.out.println("认证状态：" + subject.isAuthenticated());
        }catch (UnknownAccountException e){
            e.printStackTrace();
            System.out.println("用户名错误");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误");
        }
    }
}
