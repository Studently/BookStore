package cn.eud.stu.order.domain;

import cn.eud.stu.user.domian.User;

import java.util.Date;
import java.util.List;

/**
 * 订单实体类
 */
public class Order {

    private String oid;//订单id
    private Date ordertime;//订单时间
    private double total;//订单金额
    private int state;//订单状态：1未付款；2付款，未法获；3发货，未确认；4已确认，交易成功
    private User ownUser;//订单拥有者
    private String address;//收获地址

    private List<OrderItem> orderItemList;//订单包含的订单条目

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public User getOwnUser() {
        return ownUser;
    }

    public void setOwnUser(User ownUser) {
        this.ownUser = ownUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
    //添加条目
    public void add(OrderItem item){
        orderItemList.add(item);
    }
}
