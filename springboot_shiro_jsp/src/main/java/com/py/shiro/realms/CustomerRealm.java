package com.py.shiro.realms;

import com.py.entity.Perms;
import com.py.entity.User;
import com.py.service.UserService;
import com.py.shiro.salt.MyByteSource;
import com.py.utils.ApplicationContextUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

//自定义realm
public class CustomerRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取身份信息
        String primaryPrincipal = ((String) principals.getPrimaryPrincipal());
        System.out.println("权限认证：" + primaryPrincipal);
        //根据主身份信息获取 角色 权限 信息
        UserService userService = (UserService) ApplicationContextUtils.getBean("userService");
        User user = userService.findRolesByUserName(primaryPrincipal);

        System.out.println("user:"+user);
        //授权角色信息
        if(!CollectionUtils.isEmpty(user.getRoles())){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

            user.getRoles().forEach(role->{
                simpleAuthorizationInfo.addRole(role.getName()); //添加角色信息

                //权限信息
                List<Perms> perms = userService.findPermsByRoleId(role.getId());
                System.out.println("perms:"+perms);

                if(!CollectionUtils.isEmpty(perms) && perms.get(0)!=null ){
                    perms.forEach(perm->{
                        simpleAuthorizationInfo.addStringPermission(perm.getName());
                    });
                }
            });
            return simpleAuthorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("=============");

        //从传过来的token获取到的用户名
        String principal = (String) token.getPrincipal();
        System.out.println("用户名"+principal);

        //在工厂中获取service对象
        UserService userService = (UserService) ApplicationContextUtils.getBean("userService");

        //根据身份信息查询
        User user = userService.findByUserName(principal);
        System.out.println("User:"+user);

        //用户不为空
        if(!ObjectUtils.isEmpty(user)){
            //返回数据库信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),
                    new MyByteSource(user.getSalt()), this.getName());
            return simpleAuthenticationInfo;
        }

        return null;
    }
}
