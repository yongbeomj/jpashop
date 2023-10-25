package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    // EnumType은 반드시 STRING으로 사용! ORDINAL은 숫자로 부여되므로 중간에 타입이 추가될 경우 하나씩 밀려 처음 부여된 숫자와 달라져 DB에서 조회가 안됨(XXX로 나옴)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP

}
