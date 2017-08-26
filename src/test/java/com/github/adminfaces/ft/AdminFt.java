package com.github.adminfaces.ft;

import com.github.adminfaces.ft.pages.IndexPage;
import com.github.adminfaces.ft.pages.LogonPage;
import com.github.adminfaces.ft.util.Deployments;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.net.URL;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Car acceptance tests
 */

@RunWith(Arquillian.class)
public class AdminFt {

    @Deployment(name = "admin-starter.war", testable = false)
    public static Archive<?> createDeployment() {
        WebArchive war = Deployments.createDeployment();
        System.out.println(war.toString(true));
        return war;
    }

    @ArquillianResource
    URL url;

    @Drone
    WebDriver webDriver;

    @FindByJQuery("div[id='messages'] span.ui-messages-error-detail")
    private GrapheneElement errorMessages;

    @FindByJQuery("div[id='messages'] span.ui-messages-info-detail")
    private GrapheneElement inforMessages;



    @Page
    IndexPage index;

    @Test
    @InSequence(1)
    public void shouldLogonSuccessfully(@InitialPage LogonPage logon) {
        assertThat(logon.isPresent()).isTrue();
    }
}
