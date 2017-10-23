/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adminfaces.starter.bean;

import com.github.adminfaces.persistence.bean.BeanService;
import com.github.adminfaces.persistence.bean.CrudMB;
import com.github.adminfaces.starter.model.Car;
import com.github.adminfaces.starter.model.CarPK;
import com.github.adminfaces.starter.service.CarService;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author rmpestano
 */
@Named
@ViewScoped
@BeanService(CarService.class)//use annotation instead of setter
public class CarFormMB extends CrudMB<Car> implements Serializable {


    public void afterRemove() {
        try {
            Faces.redirect("car-list.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void initBean() {
        id = new CarPK();
    }

    @Override
    public void init() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        CarPK carPK = (CarPK) id;

        if (carPK.getId() != null && carPK.getId2() != null) {
            entity =  crudService.findById(id);
        }
    }

    @Override
    public String getRemoveMessage() {
        return "Car " + entity.getModel()
                + " removed successfully";
    }

    @Override
    public String getCreateMessage() {
        return "Car " + entity.getModel() + " created successfully";
    }

    @Override
    public String getUpdateMessage() {
        return "Car " + entity.getModel() + " updated successfully";
    }


}
