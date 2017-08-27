package com.github.adminfaces.ft;

import com.github.adminfaces.ft.pages.CarListPage;
import com.github.adminfaces.ft.pages.IndexPage;
import com.github.adminfaces.ft.pages.LeftMenu;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.net.URL;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.jboss.arquillian.graphene.Graphene.waitModel;
import static org.junit.Assert.assertTrue;

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

    @FindByJQuery("div[id='info-messages'] .ui-messages-info-detail")
    private GrapheneElement infoMessages;


    @Page
    private IndexPage index;

    @Page
    private CarListPage carList;

    @FindByJQuery("section.sidebar > ul.sidebar-menu")
    private LeftMenu menu;


    @Test
    @InSequence(1)
    public void shouldLogonSuccessfully(@InitialPage LogonPage logon) {
        assertThat(logon.isPresent()).isTrue();
        logon.doLogon("abc@gmail.com", "abcde");
        assertThat(infoMessages.isPresent()).isTrue();
        assertThat(infoMessages.getText()).contains("Logged in successfully as abc@gmail.com");
    }

    @Test
    @InSequence(2)
    public void shouldListCars() {
       menu.listCars();
       assertThat(carList.isPresent()).isTrue();
    }

    @Test
    @InSequence(3)
    public void shouldPaginateCars() {
        carList.paginate();
        carList.getDatatable().findGrapheneElements(By.cssSelector("a.ui-link"))
                .forEach(e -> assertTrue(e.getText().equals("model 46") ||
                        e.getText().equals("model 47") ||  e.getText().equals("model 48")
                        ||  e.getText().equals("model 49") ||  e.getText().equals("model 50")));
    }


    @Test
    @InSequence(4)
    public void shouldFilterByModel() {
        carList.filterByModel("model 8");
        assertThat(carList.getDatatable().findElement(By.xpath("//a[contains(@class,'ui-link') and contains(text(),'model 8')]")).isPresent());
        assertThat(carList.getTableRows()).isNotNull().hasSize(1);
        assertThat(carList.getTableRows().get(0).getText()).contains("model 8");
    }

    @Test
    @InSequence(5)
    public void shouldRemoveMultipleCars() {
        carList.clear();
        webDriver.findElements(By.cssSelector("td .ui-chkbox-box")).forEach(e -> e.click());
        waitModel();
        carList.remove();
        assertThat(infoMessages.getText()).isEqualTo("5 cars deleted successfully!");
    }

    @Test
    @InSequence(6)
    public void shouldEditViaDatatable() {

    }

    @Test
    @InSequence(7)
    public void shouldEditViaUrl() {

    }

    @Test
    @InSequence(7)
    public void shouldInsertCar() {

    }


}
