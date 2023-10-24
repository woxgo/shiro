package com.py.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * 使用自定义realm实现   加入md5 + salt + hash
 */
public class CustomerMD5Realm extends AuthorizingRealm {
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String primaryPrincipal = (String)principals.getPrimaryPrincipal();
        System.out.println("身份信息: " + primaryPrincipal);

        //根据身份信息 用户名 获取当前用户的角色信息，以及权限信息 zhangsan pyadmin commonuser
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        //将数据库中查询角色信息赋值给权限对象
        simpleAuthorizationInfo.addRole("pyadmin");
        simpleAuthorizationInfo.addRole("commonuser");

        //将数据库中查询权限信息赋值给权限对象
        simpleAuthorizationInfo.addStringPermission("user:*:01");
        simpleAuthorizationInfo.addStringPermission("product:create");

        return simpleAuthorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //在token中获取用户名
        String principal = (String)token.getPrincipal();
        System.out.println(principal);
        //根据身份信息使用jdbc mybatis查询相关数据库
        if ("zhangsan".equals(principal)){
            //参数1：数据库用户名
            //参数2：数据库md5+salt之后的密码
            //参数3：注册时的随机盐
            //参数4：提供当前realm的名字  this.getName()
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("zhangsan",
                    "e4f9bf3e0c58f045e62c23c533fcf633",
                    ByteSource.Util.bytes("X0*7ps"),
                    this.getName());
            return simpleAuthenticationInfo;

        }
        return null;
    }
}
