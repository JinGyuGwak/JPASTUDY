package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ITEM_ID")
    private Long Id;

    private String name;
    private int price;
    private int stockQuantity;


}
