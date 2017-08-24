/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adminfaces.starter.bean;

import com.github.adminfaces.starter.infra.bean.CrudMB;
import com.github.adminfaces.starter.model.Car;
import com.github.adminfaces.starter.service.CarService;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;

/**
 * @author rmpestano
 */
@Named
@ViewScoped
public class CarFormMB extends CrudMB<Car> implements Serializable {

    @Inject
    CarService carService;

    @Inject
    public void setCrudService() {
        super.setCrudService(carService);
    }

    public void remove() throws IOException {
        if (has(entity) && has(entity.getId())) {
            carService.remove(entity);
            addDetailMessage("Car " + entity.getModel()
                    + " removed successfully");
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("car-list.xhtml");
        }
    }

    public void save() {
        String msg;
        if (entity.getId() == null) {
            carService.insert(entity);
            msg = "Car " + entity.getModel() + " created successfully";
        } else {
            carService.update(entity);
            msg = "Car " + entity.getModel() + " updated successfully";
        }
        addDetailMessage(msg);
    }

    public void clear() {
        entity = new Car();
        id = null;
    }

}
