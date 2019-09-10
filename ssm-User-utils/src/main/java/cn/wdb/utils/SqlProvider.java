package cn.wdb.utils;

import cn.wdb.domain.User;
import org.apache.ibatis.jdbc.SQL;

/**
 * 用于提供注解开发动态sql的类
 */
public class SqlProvider{

    public String select(Integer page,Integer pageSize,final User user) {
        return new SQL() {
            {
                SELECT("*");
                FROM("user");
                if (user.getName() != null && !"".equals(user.getName())) {
                    WHERE("name like '%"+user.getName()+"%'");
                }
                if (user.getAddress() != null && !"".equals(user.getAddress())) {
                    WHERE("address like '%"+user.getAddress()+"%'");
                }
                if (user.getEmail() != null && !"".equals(user.getEmail())) {
                    WHERE("email like '%"+user.getEmail()+"%'");
                }

            }
        }.toString();
    }
}
