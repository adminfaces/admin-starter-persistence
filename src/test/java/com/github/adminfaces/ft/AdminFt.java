package com.github.adminfaces.ft;

import com.github.adminfaces.ft.util.Deployments;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.net.URL;

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

   /* @FindByJQuery("div.ui-growl-message")
    private GrapheneElement growl;

    @FindByJQuery("a[id$=openLogin]")
    private GrapheneElement anchorLogin;

    @FindByJQuery("div[id$=userPanel]")
    private GrapheneElement divLogin;

    @Page
    IndexPage index;

    @FindByJQuery("div[id$=logonPanel]")
    LogonDialog logonPanel;


    @Before
    public void initDataset() {
        DBUnitUtils.createRemoteDataset(url, "car.yml");
    }

    @After
    public void clear() {
        DBUnitUtils.deleteRemoteDataset(url, "car.yml");
    }

    @When("^search car by id (\\d+)$")
    public void searchCarById(int id) {
        Graphene.goTo(IndexPage.class);
        index.findById("" + id);
    }

    @Then("^must find car with model \"([^\"]*)\" and price (.+)$")
    public void returnCarsWithModel(String model, final double price) {
        assertEquals(model, index.getInputModel().getAttribute("value"));
        assertEquals(price, Double.parseDouble(index.getInputPrice().getAttribute("value")), 0);
    }

    @Given("^user is logged in as \"([^\"]*)\"$")
    public void user_is_logged_in_as(String user) throws Throwable {
        Graphene.goTo(IndexPage.class);
        anchorLogin.click();
        waitModel().until().element(logonPanel.getUser()).is().present();
        logonPanel.doLogon(user);
        assertThat(divLogin.getText()).isEqualTo(user);
    }

    @And("^click on remove button$")
    public void click_on_remove_button() throws Throwable {
        index.remove();
    }

    @Then("^message \"([^\"]*)\" should be displayed$")
    public void message_should_be_displayed(String msg) throws Throwable {
        assertThat(growl.getText()).isEqualTo(msg);
    }
*/
}
