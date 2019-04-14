package cn.eud.stu.order.domain;


import cn.eud.stu.book.domain.Book;

/**
 * 订单条目实体类
 */
public class OrderItem {

    private String iid;//条目id
    private int count;//图书数量
    private double subtotal;//条目金额小计
    private Order order;//关联订单
    private Book book;//包含图书

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
