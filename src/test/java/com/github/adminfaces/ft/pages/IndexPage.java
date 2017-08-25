package com.github.adminfaces.ft.pages;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.page.Location;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

@Location("index.xhtml")
public class IndexPage {

    @FindByJQuery("input[id$=inptId]")
    private GrapheneElement inputId;

    @FindByJQuery("input[id$=inptModel]")
    private GrapheneElement inputModel;

    @FindByJQuery("input[id$=inptName]")
    private GrapheneElement inputName;

    @FindByJQuery("input[id$=inptPrice]")
    private GrapheneElement inputPrice;

    @FindByJQuery("button[id$=brFind]")
    private GrapheneElement btFind;

    @FindByJQuery("button[id$=btRemove]")
    private GrapheneElement btRemove;



    public void findById(String carId){
        inputId.sendKeys(carId);
        guardAjax(btFind).click();
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

    public void remove() {
        guardAjax(btRemove).click();
    }
}
