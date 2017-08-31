package com.github.adminfaces.starter.service;

import com.github.adminfaces.starter.model.Car;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryParam;
import org.apache.deltaspike.data.api.Repository;

import java.math.BigDecimal;

@Repository
public interface CarRepository extends EntityRepository<Car,Integer> {


    @Query("SELECT SUM(c.price) FROM Car c WHERE upper(c.model) like :model")
    Double getTotalPriceByModel(@QueryParam("model") String model);


}
