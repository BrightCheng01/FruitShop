package com.javapandeng.po;


import java.io.Serializable;
import java.util.Date;

/*商品详情*/
public class OrderDetail implements Serializable {
    /*id主键*/
    private Integer id;
    /*商品id*/
    private Integer itemId;
    /*订单id*/
    private Integer orderId;

    /*订单状态
     * 0待发货，1已取消，2已发货，3待收货，已评价*/
    private Integer status;
    private Item item;

    /*购买数量*/
    private Integer num;

    /*小计*/
    private String  total;


    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", orderId=" + orderId +
                ", status=" + status +
                ", item=" + item +
                ", num=" + num +
                ", total=" + total +
                '}';
    }

    public OrderDetail() {
    }

    public OrderDetail(Integer id, Integer itemId, Integer orderId, Integer status, Item item, Integer num, String  total) {
        this.id = id;
        this.itemId = itemId;
        this.orderId = orderId;
        this.status = status;
        this.item = item;
        this.num = num;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderid) {
        this.orderId = orderid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String  getTotal() {
        return total;
    }

    public void setTotal(String  total) {
        this.total = total;
    }
}
