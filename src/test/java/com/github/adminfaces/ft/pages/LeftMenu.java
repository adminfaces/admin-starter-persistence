package com.github.adminfaces.ft.pages;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.fragment.Root;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;

public class LeftMenu {

    @Root
    private GrapheneElement menu;

    @FindByJQuery("li > a > i.fa-car")
    private GrapheneElement listCarMenu;

    public void listCars() {
        guardHttp(listCarMenu).click();
    }
}
