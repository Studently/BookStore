package cn.eud.stu.user.dao;

import cn.edu.stu.tools.jdbc.TrQueryRunner;
import cn.eud.stu.user.domian.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * 用户数据层
 */
public class UserDao {
    //添加依赖
    private QueryRunner qr=new TrQueryRunner();

    /**
     * 通过用户名查询用户信息
     * @param username
     * @return
     * @throws SQLException
     */
    public User findByUsername(String username) throws SQLException {
        String sql="select * from tb_user where username=?";

        return qr.query(sql,new BeanHandler<>(User.class),username);
    }


    /**
     * 通过email查询用户
     * @param email
     * @return
     * @throws SQLException
     */
    public User findByEmail(String email) throws SQLException {
        String sql="select * from tb_user where email=?";
        return qr.query(sql,new BeanHandler<>(User.class),email);
    }

    /**
     * 插入用户
     * @param user
     * @throws SQLException
     */
    public void add(User user) throws SQLException {
        String sql="insert into tb_user values(?,?,?,?,?,?)";
        //设置参数
        Object[] params={user.getUid(),user.getUsername(),user.getPassword(),
                        user.getEmail(),user.getCode(),user.isState()};

        qr.update(sql,params);
    }


    /**
     * 通过激活码查询用户信息
     * @param code
     * @return
     * @throws SQLException
     */
    public User findByCode(String code) throws SQLException {
        String sql="select * from tb_user where code=?";
        return qr.query(sql,new BeanHandler<>(User.class),code);
    }


    /**
     * 根据用户uid和状态激活
     * @param uid
     * @param state
     * @throws SQLException
     */
    public void updateState(String uid,boolean state) throws SQLException {
        String sql="update  tb_user set state=? where uid=?";
        qr.update(sql,state,uid);
    }

}
