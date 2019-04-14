package cn.eud.stu.user.service;

import cn.eud.stu.user.dao.UserDao;
import cn.eud.stu.user.domian.User;
import cn.eud.stu.user.exception.UserException;

import java.sql.SQLException;

/**
 * 用户业务层
 */
public class UserService {

    //添加依赖
    private UserDao userDao=new UserDao();


    /**
     * 注册
     * @param user
     */
    public void regist(User user) throws SQLException, UserException {


        //用户名验证
        User _user=userDao.findByUsername(user.getUsername());
        if(_user!=null){
            throw new UserException("该用户名已经被注册");
        }
        _user=userDao.findByEmail(user.getEmail());
        if(_user!=null){
            throw new UserException("该邮箱已经被注册");
        }
        //向数据库插入用户记录
        userDao.add(user);
    }

    /**
     * 根据用户的激活码激活
     * @param code
     */
    public void active(String code) throws SQLException, UserException {
        User user=userDao.findByCode(code);

        //激活码不存在
        if (user == null)
            throw new UserException("激活码无效！");
        //用户已激活
        if(user.isState())
            throw new UserException("您已激活，请不要重复激活！");
        //激活
        userDao.updateState(user.getUid(),true);

    }


    /**
     * 登陆
     * @param form
     * @return
     */
    public User login(User form) throws SQLException, UserException {

        /**
         * 登陆
         * 1、根据用户名查询用户信息
         *      （1）如果用户不存在，抛出异常
         *      （2）如果存在，比较密码是否相同
         * 2、匹配成功返回用户信息
         */

        User user=userDao.findByUsername(form.getUsername());

        if (user == null)
            throw new UserException("用户名错误！");

        if(!user.getPassword().equals(form.getPassword()))
            throw new UserException("密码错误！");

        if(!user.isState())
            throw new UserException("未激活！");

        return user;
    }

}
