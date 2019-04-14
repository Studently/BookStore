package cn.eud.stu.cart.domain;

import cn.eud.stu.book.domain.Book;

import java.math.BigDecimal;

/**
 * 购物车实体类
 */
public class CartItem {

    private Book book;//图书
    private int count;//数量

    /**
     * 小计价格
     * 使用bigDecimal处理二进制浮点运算误差问题
     */
    public double getSubtotal(){
        BigDecimal d1=new BigDecimal(book.getPrice()+"");
        BigDecimal d2=new BigDecimal(count+"");
        return d1.multiply(d2).doubleValue();
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "book=" + book +
                ", count=" + count +
                '}';
    }
}
