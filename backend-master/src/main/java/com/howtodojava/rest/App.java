package com.howtodojava.rest;


import com.howtodojava.rest.controller.EmployeeProController;
import com.howtodojava.rest.controller.EmployeeRestController;
import com.howtodojava.rest.controller.FoodRestController;
import com.howtodojava.rest.dao.EmployeeDaoImpl;
import com.howtodojava.rest.dao.EmployeeProImpl;
import com.howtodojava.rest.dao.FoodDaoImpl;
import com.howtodojava.rest.dao.killSwitch;
import com.howtodojava.rest.models.Employee;
import com.howtodojava.rest.models.EmployeePro;
import com.howtodojava.rest.models.Food;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App extends Application<Config> {
    private final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private final HibernateBundle<Config> hibernate = new HibernateBundle<Config>(Employee.class,  EmployeePro.class, Food.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(Config configuration) {
            return configuration.getDataSourceFactory();
        }
    };


    public static void main(String[] args) throws Exception {
         new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<Config> bootstrap) {

       bootstrap.addBundle(hibernate);
//       bootstrap.addBundle(hibernateBundle);
//     bootstrap.addBundle(new MultiPartBundle());
    }


    @Override
    public void run(Config config, Environment environment) throws SchedulerException {
        LOGGER.info("Registering REST resources");

        environment.jersey().register(new EmployeeRestController(new EmployeeDaoImpl(hibernate.getSessionFactory())));
        environment.jersey().register(new FoodRestController(new FoodDaoImpl(hibernate.getSessionFactory())));
       // environment.jersey().register(new EmployeeProController(new EmployeeProImpl(hibernate.getSessionFactory())));
        environment.jersey().register(new EmployeeProController(new EmployeeProImpl(hibernate.getSessionFactory())));
        environment.jersey().register(MultiPartFeature.class);
     //   ClassPathXmlApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
        JobDetail j = JobBuilder.newJob(killSwitch.class).build();
        Trigger t = TriggerBuilder.newTrigger().withIdentity("CroneTriger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
        Scheduler s= StdSchedulerFactory.getDefaultScheduler();
        s.start();
        s.scheduleJob(j,t);

    }

}