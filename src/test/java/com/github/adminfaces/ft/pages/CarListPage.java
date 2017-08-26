package com.github.adminfaces.ft.pages;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.page.Location;

@Location("login.xhtml")
public class CarListPage {


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


    @FindByJQuery("section.content-header h1")
    private GrapheneElement header;



    public boolean isPresent() {
        return header.isPresent() && header.getText().contains("Car listing Find cars by name, price and model");
    }
}
