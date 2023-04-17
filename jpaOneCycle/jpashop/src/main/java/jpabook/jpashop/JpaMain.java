package jpabook.jpashop;



import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();


        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Member m1 = new Member();
            m1.setName("곽진규");
            em.persist(m1);

            Member m2 = new Member();
            m2.setName("진규");
            em.persist(m2);

            Order order1 = new Order();
            order1.setMember(m1);
            em.persist(order1);

            Order order2 = new Order();
            order2.setMember(m2);
            em.persist(order2);

            em.flush();
            em.clear();


//            Order or = em.find(Order.class,order1.getId());
            List<Order> orders = em.createQuery("select o from Order o", Order.class)
                            .getResultList();

            tx.commit();
        } catch (Exception e){
            System.out.println("실행이 안되었어요");
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

        System.out.println("실행이 되었어요");

    }
}
