package com.edu.camunda.dao;

import com.edu.camunda.entity.User;

import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserDao {

    private SessionFactory sessionFactory;

    public void createUser(User user) {
        sessionFactory.getCurrentSession().persist(user);
    }

    public Optional<User> find(String username){

        Session session = sessionFactory.getCurrentSession();

        TypedQuery<User> query = session.createQuery("select u from User u where u.username=:username", User.class);

        query.setParameter("username", username);

        return Optional.ofNullable(query.getSingleResult());
    }

    public Optional<User> findUserByAccountNumber(String accountNumber){

        Session session = sessionFactory.getCurrentSession();

        TypedQuery<User> query = session.createQuery("select u from User u where u.accountNumber=:accountNumber",User.class);
        query.setParameter("accountNumber", accountNumber);

        return Optional.ofNullable(query.getSingleResult());

    }


    public void updateUser(User user){

        Session session = sessionFactory.getCurrentSession();

        session.update(user);
    }


}
