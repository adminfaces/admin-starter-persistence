package com.github.adminfaces.ft.pages;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.fragment.Root;
import org.jboss.arquillian.graphene.page.Location;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import static org.jboss.arquillian.graphene.Graphene.guardHttp;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

@Location("login.xhtml")
public class LogonPage {


    @FindByJQuery("input[type=email]")
    private GrapheneElement email;

    @FindByJQuery("input[type=password]")
    private GrapheneElement password;

    @FindByJQuery("button.btn-success")
    private GrapheneElement btLogon;

    @FindByJQuery("div.login-box")
    private GrapheneElement loginBox;


    public void doLogon(String email, String password) {
        waitModel();
        this.email.clear();
        this.email.sendKeys(email);
        this.password.clear();
        this.password.sendKeys(password);
        guardAjax(btLogon).click();
    }

    public boolean isPresent() {
        return loginBox.isPresent();
    }

    public GrapheneElement getLoginBox() {
        return loginBox;
    }
}
