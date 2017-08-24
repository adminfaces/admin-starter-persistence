package com.github.adminfaces.starter.bean;

import com.github.adminfaces.starter.infra.bean.CrudMB;
import com.github.adminfaces.starter.infra.model.Filter;
import com.github.adminfaces.starter.infra.service.CrudService;
import com.github.adminfaces.starter.infra.service.Service;
import com.github.adminfaces.starter.model.Car;
import com.github.adminfaces.starter.service.CarService;
import com.github.adminfaces.template.exception.BusinessException;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;

/**
 * Created by rmpestano on 12/02/17.
 */
@Named
@ViewScoped
public class CarListMB extends CrudMB<Car> implements Serializable {

    @Inject
    CarService carService;

    @Inject
    @Service
    CrudService<Car, Integer> crudService; //generic injection

    Integer id;


    @Inject
    public void initService() {
       setCrudService(carService);
    }

    public void clear() {
        filter = new Filter<Car>(new Car());
    }

    public List<String> completeModel(String query) {
        List<String> result = carService.getModels(query);
        return result;
    }

    public void findCarById(Integer id) {
        if (id == null) {
            throw new BusinessException("Provide Car ID to load");
        }
        selectionList.add(crudService.findById(id));
    }

    public void delete() {
        int numCars = 0;
        for (Car selectedCar : selectionList) {
            numCars++;
            carService.remove(selectedCar);
        }
        selectionList.clear();
        addDetailMessage(numCars + " cars deleted successfully!");
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
