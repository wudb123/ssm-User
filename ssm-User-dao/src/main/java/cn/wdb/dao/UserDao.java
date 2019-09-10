package cn.wdb.dao;

import cn.wdb.domain.Admin;
import cn.wdb.domain.User;
import org.apache.ibatis.annotations.*;
import cn.wdb.utils.SqlProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户持久层接口
 */
@Repository("userDao")
public interface UserDao {

    @Select("select * from admin where username = #{username} and password = #{password}")
    public Admin login(Admin admin)throws Exception;
    /**
     * 查询所有
     * @return
     */
    @SelectProvider(type = SqlProvider.class,method = "select")
    public List<User> findAll(Integer page,Integer pageSize,User user) throws Exception;

    /**
     * 添加用户
     * @param user
     */
    @Insert("insert into user(name,gender,age,address,qq,email) values(#{name},#{gender},#{age},#{address},#{qq},#{email})")
    void addUser(User user) throws Exception;

    /**
     * 删除用户
     * @param id
     */
    @Delete("delete from user where id =#{id}")
    void delUser(Integer id) throws Exception;

    /**
     * 根据id查询后回显信息
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User findById(Integer id) throws Exception;

    /**
     * 更新用户信息
     * @param user
     */
    @Update("update user set name = #{name},gender = #{gender},age = #{age},address =#{address},qq = #{qq},email = #{email} where id = #{id}")
    void update(User user) throws Exception;

}
