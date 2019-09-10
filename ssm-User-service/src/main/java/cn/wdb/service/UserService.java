package cn.wdb.service;

import cn.wdb.domain.Admin;
import cn.wdb.domain.User;

import java.util.List;

/**
 * 业务层接口
 */
public interface UserService {

    public Admin login(Admin admin) throws Exception;
    /**
     * 查询所有
     * @return
     */
    public List<User> findAll(Integer page,Integer pageSize,User user) throws Exception;

    /**
     * 添加用户
     * @param user
     */
    void addUser(User user) throws Exception;

    /**
     * 删除用户
     * @param id
     */
    void delUser(Integer id) throws Exception;

    /**
     * 根据id查询后回显信息
     * @param id
     * @return
     */
    User findById(Integer id) throws Exception;

    /**
     * 更新用户信息
     * @param user
     */
    void update(User user) throws Exception;


}
