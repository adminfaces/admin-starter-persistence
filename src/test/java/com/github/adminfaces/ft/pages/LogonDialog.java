package com.github.adminfaces.ft.pages;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.fragment.Root;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

public class LogonDialog {

    @Root
    private GrapheneElement dialog;

    @FindByJQuery("input[id$=user]")
    private GrapheneElement user;

    @FindByJQuery("button[id$=btLogin]")
    private GrapheneElement btLogin;


    public GrapheneElement getUser(){
        return user;
    } 

    public void doLogon(String user){
        waitModel();
        this.user.clear();
        this.user.sendKeys(user);
        guardAjax(btLogin).click();
    }

}
