package com.github.adminfaces.ft.pages;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.page.Location;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

@Location("index.xhtml")
public class IndexPage {

    @FindByJQuery("H2")
    private GrapheneElement title;


    public GrapheneElement getTitle() {
        return title;
    }
}
