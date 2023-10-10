package com.example.project02.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long category_id;

    private String name;

    @OneToMany(mappedBy = "category") // 관계 설정
    private List<Product> productList;
}
