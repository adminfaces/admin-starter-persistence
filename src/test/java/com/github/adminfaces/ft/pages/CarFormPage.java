package com.github.adminfaces.ft.pages;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

@Location("login.xhtml")
public class CarFormPage {

    @FindByJQuery("section.content-header h1")
    private GrapheneElement header;

    @FindByJQuery("input[id$=inptModel]")
    private GrapheneElement inputModel;

    @FindByJQuery("input[id$=inptName]")
    private GrapheneElement inputName;

    @FindByJQuery("input[id$=inptPrice_input]")
    private GrapheneElement inputPrice;

    @FindByJQuery("button[id$=brFind]")
    private GrapheneElement btFind;

    @FindByJQuery("button.btn-danger")
    private GrapheneElement btRemove;

    @FindByJQuery("button.btn-primary")
    private GrapheneElement btSave;

    @FindByJQuery("span.ui-button-text:contains('Yes')")
    private GrapheneElement btYes;

    @FindByJQuery("span.ui-dialog-title:contains('Confirmation')")
    private GrapheneElement confirmHeader;

    @Drone
    private WebDriver browser;

    public boolean isPresent() {
        return header.isPresent() && header.getText().contains("Car form");
    }


    public GrapheneElement getInputModel() {
        return inputModel;
    }

    public GrapheneElement getInputName() {
        return inputName;
    }

    public GrapheneElement getInputPrice() {
        return inputPrice;
    }

    public void save() {
        guardAjax(btSave).click();
    }

    public void remove() {
        btRemove.click();
        waitModel().until().element(confirmHeader).is().present();
        guardAjax(btYes).click();
    }
}
