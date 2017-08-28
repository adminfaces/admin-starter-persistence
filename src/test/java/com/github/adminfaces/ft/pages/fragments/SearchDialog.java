package com.github.adminfaces.ft.pages.fragments;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.fragment.Root;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import static org.jboss.arquillian.graphene.Graphene.guardHttp;

public class SearchDialog {

    @Root
    private GrapheneElement dialog;

    @FindByJQuery("span.ui-dialog-title")
    private GrapheneElement title;

    @FindByJQuery("input[id$=name]")
    private GrapheneElement name;

    @FindByJQuery("input[id$=min_input]")
    private GrapheneElement minPrice;

    @FindByJQuery("input[id$=max_input]")
    private GrapheneElement maxPrice;

    @FindByJQuery("span.fa-close")
    private GrapheneElement btClose;

    @FindByJQuery("div.ui-dialog-content button.btn-primary")
    private GrapheneElement btOk;



    public void close() {
        guardAjax(btClose).click();
    }

    public GrapheneElement getName() {
        return name;
    }

    public GrapheneElement getMinPrice() {
        return minPrice;
    }

    public GrapheneElement getMaxPrice() {
        return maxPrice;
    }

    public void search() {
        guardAjax(btOk).click();
    }
}
