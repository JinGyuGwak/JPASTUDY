package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){ //엔티티반환은 원래 하면 안 됨
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
//        List<SimpleOrderDto> result = orders.stream()
//                .map(o -> new SimpleOrderDto(o))
//                .collect(Collectors.toList());
        List<SimpleOrderDto> result = new ArrayList<>();
        for(Order order : orders){
            SimpleOrderDto a = new SimpleOrderDto(order);
            result.add(a);
        }
        return result;
    }


    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
//        List<SimpleOrderDto> result = orders.stream()
//                .map(o -> new SimpleOrderDto(o))
//                .collect(Collectors.toList());
        List<SimpleOrderDto> result = new ArrayList<>();
        for(Order order : orders){
            SimpleOrderDto a = new SimpleOrderDto(order);
            result.add(a);
        }
        return result;
    }

    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order){
            orderId=order.getId();
            name = order.getMember().getName(); //Member쿼리 한 개 나감 ->LAZY 초기화 -> 다시 조회해야함
            orderDate=order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // Delivery쿼리 한 개 나감 ->LAZY 초기화 -> 다시 조회해야함
            //order는 한 번 조회 + 멤버,딜리버리를 order의 수 만큼 더 조회(2*(1+1))
            //근데 주문이나 딜리버리나 같은 객체면(PK가 같으면) 1번씩 조회함 (영속성 컨텍스트에 있음)
            //-> join fetch 사용하자 (Order를 조회할 때 join fetch로 조회 할 엔티티 전부 선언) 한 번에 같이 조회하니깐 다시 조회할 필요가 없다
            // 페치 조인으로 order -> member, order -> delivery 는 이미 조회 된 상태 이므로 지연로딩X
            // (근데 select 절을 보면 진짜 너무 많다 -> 간단한 주문 조회 V4를 보자

        }
    }

}
