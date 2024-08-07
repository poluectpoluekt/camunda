package com.edu.camunda.dao;



import com.edu.camunda.entity.operation.Debit;
import com.edu.camunda.entity.operation.Topup;
import com.edu.camunda.entity.operation.Transfer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional("hibernateTransactionManager")
@Repository
public class TransactionDao {

    private final SessionFactory sessionFactory;

    public void createDebit(Debit debit) {
        sessionFactory.getCurrentSession().persist(debit);

    }

    public void createTopup(Topup topup) {

        sessionFactory.getCurrentSession().persist(topup);

    }

    public void createTransfer(Transfer transfer) {

        sessionFactory.getCurrentSession().persist(transfer);

    }


}
