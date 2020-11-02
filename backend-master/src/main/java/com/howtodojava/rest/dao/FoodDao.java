package com.howtodojava.rest.dao;


import com.howtodojava.rest.models.Food;
import com.howtodojava.rest.models.Point;

import java.util.List;

public interface FoodDao {
    List<Food> findAll();

    Food findById(String id);

    void save(Food food);

    void delete(Food food);

    List findNeighbours(Point point);

}
