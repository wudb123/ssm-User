package cn.wdb.service.impl;

import cn.wdb.dao.UserDao;
import cn.wdb.domain.Admin;
import cn.wdb.domain.User;
import cn.wdb.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务层实现类
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;

    public Admin login(Admin admin) throws Exception {
        return userDao.login(admin);
    }

    public List<User> findAll(Integer page, Integer pageSize, User user) throws Exception {
        PageHelper.startPage(page,pageSize);
        return userDao.findAll(page,pageSize,user);
    }

    public void addUser(User user) throws Exception {
        userDao.addUser(user);
    }

    public void delUser(Integer id) throws Exception {
        userDao.delUser(id);
    }

    public User findById(Integer id) throws Exception {
        return userDao.findById(id);
    }

    public void update(User user) throws Exception {
        userDao.update(user);
    }
}
