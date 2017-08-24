package com.github.adminfaces.starter.bean;

import com.github.adminfaces.persistence.bean.CrudMB;
import com.github.adminfaces.persistence.service.CrudService;
import com.github.adminfaces.persistence.service.Service;
import com.github.adminfaces.starter.model.Car;
import com.github.adminfaces.starter.service.CarService;
import com.github.adminfaces.template.exception.BusinessException;
import org.omnifaces.cdi.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

import static com.github.adminfaces.persistence.util.Messages.addDetailMessage;


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

    @Inject
    public void initService() {
       setCrudService(carService);
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

}
