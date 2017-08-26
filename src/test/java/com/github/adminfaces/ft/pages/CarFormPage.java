package com.github.adminfaces.ft.pages;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.page.Location;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

@Location("login.xhtml")
public class CarFormPage {


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
}
