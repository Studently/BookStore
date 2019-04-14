package cn.eud.stu.order.service;

import cn.edu.stu.tools.jdbc.JDBCUtils;
import cn.eud.stu.order.dao.OrderDao;
import cn.eud.stu.order.domain.Order;
import cn.eud.stu.order.exception.OrderExcetion;

import java.sql.SQLException;
import java.util.List;

/**
 * 业务层
 */
public class OrderService {

    //依赖
    private OrderDao dao=new OrderDao();

    /**
     * 添加订单
     */
    public void add(Order order){

        try {
            //开启事务
            JDBCUtils.beginTransaction();
            //添加订单
            dao.addOrder(order);
            //添加订单条
            dao.addOrderItem(order.getOrderItemList());

            //提交事务
            JDBCUtils.commitTransaction();
        }catch (SQLException e){
            try {
                //回滚事务
                JDBCUtils.rollbackTransaction();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 通过用户id查询订单列表
     * @param uid
     * @return
     */
    public List<Order> findByUid(String uid) throws SQLException {
        return dao.findByUid(uid);
    }

    /**
     * 通过订单id查找订单
     * @param oid
     * @return
     */
    public Order findByOid(String oid){
        return dao.findByOid(oid);
    }

    public void confirm(String oid) throws OrderExcetion {

        int _state = dao.findStateByOid(oid);
        if(_state!=3)
            throw new OrderExcetion("订单确认失败！");

        dao.updateState(oid,4);
    }
}
