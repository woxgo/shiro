package com.py;

import com.py.realm.CustomerMD5Realm;
import com.py.realm.CustomerRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * 使用自定义realm
 */
public class TestCustomerMD5RealmAuthenticator {
    public static void main(String[] args) {
        //创建securityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //注入realm
        CustomerMD5Realm realm = new CustomerMD5Realm();
        //设置realm使用hash凭证匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //使用算法
        credentialsMatcher.setHashAlgorithmName("md5");
        //散列次数
        credentialsMatcher.setHashIterations(1024);
        realm.setCredentialsMatcher(credentialsMatcher);
        securityManager.setRealm(realm);

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
