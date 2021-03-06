package com.js.taxi.dao;

import com.js.taxi.domain.Order;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Jura on 05.04.2015.
 */
@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    @Override
    public Long create(Order order) {
        sessionFactory.getCurrentSession().save(order);
        return order.getOrderId();
    }

    @Override
    public Order read(Long id) {
        return (Order) sessionFactory.getCurrentSession().get(Order.class, id);
    }

    @Override
    public void update(Order order) {
        sessionFactory.getCurrentSession().update(order);
    }

    @Override
    public void delete(Order order) {
        sessionFactory.getCurrentSession().delete(order);
    }

    @Override
    public List<Order> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(Order.class).list();
    }

    @Override
    public List<Order> findOrdersBySum(Integer from, Integer to) {
        return sessionFactory.getCurrentSession().createCriteria(Order.class).add(Restrictions.between("sum", from, to)).list();
    }

    @Override
    public List<Order> findPortion(int firstResult, int maxResults) {
        return sessionFactory.getCurrentSession().createCriteria(Order.class).addOrder(org.hibernate.criterion.Order.desc("orderDate"))
                .setFirstResult(firstResult).setMaxResults(maxResults).list();
    }

    @Override
    public Long rowCount() {
        return (Long) sessionFactory.getCurrentSession().createCriteria(Order.class)
                .setProjection(Projections.rowCount()).uniqueResult();
    }
}
