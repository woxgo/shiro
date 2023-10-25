package com.py.dao;

import com.py.entity.Perms;
import com.py.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface  UserDao {
    void save(User user);

    //根据身份信息认证的方法
    User findByUserName(String username);

    //根据用户名查询所有角色
    User findRolesByUserName(String username);
    //根据角色id查询权限集合
    List<Perms> findPermsByRoleId(String id);
}
