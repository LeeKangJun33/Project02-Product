package com.example.project02.entity;

import com.example.project02.constant.ProductSerllStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long productId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "product_name",length = 20)
    private String product_name;

    private double price;

    private int stock_quantity;

    private LocalDateTime register_date;

    @Temporal(TemporalType.DATE)
    @Column(name = "field_predicted_sale_enddate",nullable = false)
    private Date field_predicted_sale_enddate;

    @Enumerated(EnumType.STRING)
    private ProductSerllStatus productSerllStatus; //상품 판매상태

    private String img1;

    private String img2;

    private String img3;

    @Column(name = "seller_id")
    private Long SellerId;

}
