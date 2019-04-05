package com.github.adminfaces.starter.bean;

import static com.github.adminfaces.persistence.util.Messages.addDetailMessage;
import static com.github.adminfaces.persistence.util.Messages.getMessage;
import static com.github.adminfaces.template.util.Assert.has;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.github.adminfaces.persistence.bean.CrudMB;
import com.github.adminfaces.persistence.service.CrudService;
import com.github.adminfaces.persistence.service.Service;
import com.github.adminfaces.starter.model.Car;
import com.github.adminfaces.starter.service.CarService;
import com.github.adminfaces.template.exception.BusinessException;

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
    CrudService<Car, Integer> carCrudService; //generic injection example

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
        Car carFound = carCrudService.findById(id);
        if (carFound == null) {
            throw new BusinessException(String.format("No car found with id %s", id));
        }
        selectionList.add(carFound);
        getFilter().addParam("id", id);
    }

    public void delete() {
        int numCars = 0;
        for (Car selectedCar : selectionList) {
            numCars++;
            carService.remove(selectedCar);
        }
        selectionList.clear();
        addDetailMessage(numCars + " cars deleted successfully!");
        clear();
    }

    public String getSearchCriteria() {
        StringBuilder sb = new StringBuilder(21);

        String nameParam = null;
        Car carFilter = filter.getEntity();

        Integer idParam = null;
        if (filter.hasParam("id")) {
            idParam = filter.getIntParam("id");
        }

        if (has(idParam)) {
            sb.append("<b>id</b>: ").append(idParam).append(", ");
        }

        if (filter.hasParam("name")) {
            nameParam = filter.getStringParam("name");
        } else if (has(carFilter) && carFilter.getName() != null) {
            nameParam = carFilter.getName();
        }

        if (has(nameParam)) {
            sb.append("<b>name</b>: ").append(nameParam).append(", ");
        }

        String modelParam = null;
        if (filter.hasParam("model")) {
            modelParam = filter.getStringParam("model");
        } else if (has(carFilter) && carFilter.getModel() != null) {
            modelParam = carFilter.getModel();
        }

        if (has(modelParam)) {
            sb.append("<b>model</b>: ").append(modelParam).append(", ");
        }

        Double priceParam = null;
        if (filter.hasParam("price")) {
            priceParam = filter.getDoubleParam("price");
        } else if (has(carFilter) && carFilter.getModel() != null) {
            priceParam = carFilter.getPrice();
        }

        if (has(priceParam)) {
            sb.append("<b>price</b>: ").append(priceParam).append(", ");
        }

        if (filter.hasParam("minPrice")) {
            sb.append("<b>").append(getMessage("label.minPrice")).append("</b>: ").append(filter.getParam("minPrice")).append(", ");
        }

        if (filter.hasParam("maxPrice")) {
            sb.append("<b>").append(getMessage("label.maxPrice")).append("</b>: ").append(filter.getParam("maxPrice")).append(", ");
        }

        int commaIndex = sb.lastIndexOf(",");

        if (commaIndex != -1) {
            sb.deleteCharAt(commaIndex);
        }

        if (sb.toString().trim().isEmpty()) {
            return "No search criteria";
        }

        return sb.toString();
    }

}
