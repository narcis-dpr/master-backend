package com.howtodojava.rest.dao;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class killSwitch implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      //  System.out.println("Fixed delay scheduler is running at ");
      //  System.out.println("the time nos is : " +new Date());
        String queri = "delete FROM food WHERE expireTime <= CURRENT_TIMESTAMP";

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Session session = sessionFactory.openSession();

//        try{
//            session.createSQLQuery(queri).addEntity(Food.class);

            Query query = session.createSQLQuery(queri);
             query.executeUpdate();
           // System.out.println("Rows affected: " + rowCount);

//        } finally {
//            session.close();
//        }
        session.close();

    }
}
