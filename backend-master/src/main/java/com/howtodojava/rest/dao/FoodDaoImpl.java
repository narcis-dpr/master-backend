package com.howtodojava.rest.dao;

import com.howtodojava.rest.models.Food;
import com.howtodojava.rest.models.Point;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import java.util.Date;
import java.util.List;
public class FoodDaoImpl extends AbstractDAO<Food> implements FoodDao {



    public FoodDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Food> findAll() {
        return super.list(this.criteria());

    }

    @Override
    public Food findById(String id) {
        return super.get(id);
    }

    @Override
    public void save(Food food) {
        food.setCreated(new Date());
        Date expireDate = food.getCreated();
        System.out.println("55555555555555555555555         "+ expireDate);
//        Integer expire = food.getExpire();
            food.setExpireOne(expireDate);
        super.persist(food);
    }

    @Override
    public void delete(Food food) { super.currentSession().delete(food); }


    private static String makePointGeom(String lat, String lng) {
        return String.format("ST_GeomFromText(" +
            "'POINT('" +
                "|| %s || ' ' || %s ||" +
            "')',4326" +
        ")", lat, lng);
    }

    private static String castPointToGeography(String pointSQL) {
        return pointSQL + " \\:\\: geography";
    }

    private static String distanceQueryString(Point point) {
        String refPointSql = castPointToGeography(makePointGeom("" + point.lat, "" + point.lng));
        String colPointSql = castPointToGeography(makePointGeom("lat", "lng"));
        return String.format("SELECT food.* FROM food WHERE (ST_Distance(%s, %s) < 20000)" +
                " ", refPointSql, colPointSql);
    }

    public List<Food> findNeighbours(Point point) {
        String query = distanceQueryString(point);
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println("querrryy:  "+query);
      //  return currentSession().createSQLQuery(query).addEntity(Food.class).list();

        return currentSession().createSQLQuery(query).addEntity(Food.class).list();
    }




}
