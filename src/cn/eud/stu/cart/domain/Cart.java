package cn.eud.stu.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {

    /**
     * 使用LinkHashMap，保证顺序
     * 键：图书id
     */
    private Map<String,CartItem>cartItems=new LinkedHashMap<>();


    /**
     * 获得合计
     * 使用BigDecimal来解决二进制浮点运算误差
     * @return
     */
    public double getTotal(){
        BigDecimal total=new BigDecimal("0");
        //遍历所有购物条
        for (CartItem item:cartItems.values()) {
            BigDecimal d=new BigDecimal(item.getSubtotal()+"");
            total=total.add(d);
        }
        return total.doubleValue();
    }

    //添加条目
    public void add(CartItem item){
        if(cartItems.containsKey(item.getBook().getBid())){//包含该键
            //得到旧条目
            CartItem _item=cartItems.get(item.getBook().getBid());
            //新条目数量等于=旧条目数量+添加条目数量
            _item.setCount(_item.getCount()+item.getCount());
            //将新条目放入map
            cartItems.put(_item.getBook().getBid(),_item);
        }else{//如果不存在，直接存入map中
            cartItems.put(item.getBook().getBid(),item);
        }
    }

    //删除指定条目
    public void delete(String bid){
        cartItems.remove(bid);
    }

    //清空条目
    public void clear(){
        cartItems.clear();
    }

    //获得所有条目
    public Collection<CartItem> getCartItems(){
        return cartItems.values();
    }
}
