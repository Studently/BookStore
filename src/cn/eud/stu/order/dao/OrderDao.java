package cn.eud.stu.order.dao;


import cn.edu.stu.tools.commons.CommonUtils;
import cn.edu.stu.tools.jdbc.TrQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import cn.eud.stu.book.domain.Book;
import cn.eud.stu.order.domain.Order;
import cn.eud.stu.order.domain.OrderItem;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据库层
 */
public class OrderDao {

    //依赖
    private QueryRunner qr=new TrQueryRunner();


    /**
     * 添加订单
     * @param order
     */
    public void addOrder(Order order){

        try {

            String sql="insert into orders values(?,?,?,?,?,?)";

            /**
             * 将util中date转化成sql中的Timestamp
             */
            Timestamp timestamp=new Timestamp(order.getOrdertime().getTime());
            Object[]params={order.getOid(),timestamp,order.getTotal(),
                    order.getState(),order.getOwnUser().getUid(),
                    order.getAddress()};

            qr.update(sql,params);

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    /**
     * 添加订单条目
     * @param orderItemList
     */
    public void addOrderItem(List<OrderItem> orderItemList){
        try {

            String sql="insert into orderitem values(?,?,?,?,?)";

            //建立二维数组参数，
            Object[][]parmas=new Object[orderItemList.size()][];

            //遍历记录条数组，给二维参数赋值
            for (int i = 0; i <orderItemList.size() ; i++) {

                OrderItem item=orderItemList.get(i);
                parmas[i]=new Object[]{item.getIid(),item.getCount(),
                item.getSubtotal(),item.getOrder().getOid(),
                item.getBook().getBid()};
            }
            qr.batch(sql,parmas);//执行批处理
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    /**
     * 通过用户uid，查询订单列表
     * @param uid
     * @return
     */
    public List<Order> findByUid(String uid) throws SQLException {

        //sql模板
        String sql="select * from orders where uid=?";
        //得到用户订单
        List<Order> orderList=qr.query(sql,new BeanListHandler<>(Order.class),uid);
        for (int i = 0; i <orderList.size() ; i++) {
            List<OrderItem> orderItemList;
            //通过订单得到订单项列表
            orderItemList=loadOrderItemList(orderList.get(i));
            //将订单项列表
            orderList.get(i).setOrderItemList(orderItemList);
        }
        return orderList;

    }

    /**
     * 通过订单加载其对应的订单列表
     * @param order
     * @return
     */
    private List<OrderItem> loadOrderItemList(Order order){
        try {
            //通过级联查询得到定点列表项和图书信息
            String sql="select * from orderitem as i,book as b where b.bid=i.bid and i.oid=?";
            //得到订单项map
            List<Map<String,Object>> mapList=qr.query(sql,new MapListHandler(),order.getOid());

            List<OrderItem> orderItemList=new ArrayList<>();
            //遍历订单项map，得到订单项list
            for (int i = 0; i <mapList.size() ; i++) {
                OrderItem orderItem=null;
                //将一条订单列表项map，转化成一个订单列表项
                orderItem=toOrderItem(mapList.get(i));
                orderItemList.add(orderItem);
            }

            return orderItemList;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 将一条订单列表项map，转化成一个订单列表项
     * @param stringObjectMap
     * @return
     */
    private OrderItem toOrderItem(Map<String, Object> stringObjectMap) {
        //使用自定义小工具将map转化成实体类
        OrderItem item= CommonUtils.toBean(stringObjectMap,OrderItem.class);
        Book book=CommonUtils.toBean(stringObjectMap,Book.class);
        //将图书添加到订单列表项中
        item.setBook(book);
        return item;
    }


    /**
     * 通过订单id查询订单
     * @param oid
     * @return
     */
    public Order findByOid(String oid){
        try {
            //sql模板
            String sql="select * from orders where oid=?";
            //得到用户订单
            Order order=qr.query(sql,new BeanHandler<>(Order.class),oid);
            //通过订单得到订单项列表
            List<OrderItem> orderItemList=loadOrderItemList(order);
            //将订单项列表添加到订单中
            order.setOrderItemList(orderItemList);
            return order;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    /**
     * 通过点单id查询订单状态
     * @param oid
     * @return
     */
    public int findStateByOid(String oid){

        try {
            String sql="select state from orders where oid=?";
            return (Integer) qr.query(sql,new ScalarHandler(),oid);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    /**
     * 修改订单状态
     * @param oid
     * @param state
     */
    public void updateState(String oid,int state){
        try{

            String sql="update orders set state=? where oid=?";
            qr.update(sql,state,oid);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
