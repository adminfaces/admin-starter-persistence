package com.github.adminfaces.ft.pages;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

@Location("car-list.xhtml")
public class CarListPage {

    @FindByJQuery("button.btn-primary")
    private GrapheneElement btNew;

    @FindByJQuery("button.btn-info")
    private GrapheneElement btSearch;

    @FindByJQuery("span.ui-button-text:contains('Delete')")
    private GrapheneElement btRemove;

    @FindByJQuery("span.ui-button-text:contains('Clear')")
    private GrapheneElement btClear;

    @FindByJQuery("span.ui-button-text:contains('Yes')")
    private GrapheneElement btYes;

    @FindByJQuery("section.content-header h1")
    private GrapheneElement header;

    @FindByJQuery("hr.ui-separator + div.ui-datatable")
    private GrapheneElement datatable;

    @FindByJQuery("hr.ui-separator + div.ui-datatable tbody tr")
    private List<GrapheneElement> datatableRows;

    @FindByJQuery("a.ui-paginator-page[aria-label='Page 10']")
    private GrapheneElement page10;

    @FindByJQuery("button.ui-autocomplete-dropdown")
    private GrapheneElement modelAutocompleteButton;

    @FindByJQuery(".ui-column-customfilter span.ui-autocomplete > input")
    private GrapheneElement datatableModelFilter;

    @FindByJQuery("ul.ui-autocomplete-items")
    private GrapheneElement modelFilterCompleteItens;

    @FindByJQuery("span.ui-dialog-title:contains('Confirmation')")
    private GrapheneElement confirmHeader;

    @Drone
    private WebDriver browser;

    public boolean isPresent() {
        return header.isPresent() && header.getText().contains("Car listing Find cars by name, price and model");
    }

    public void paginate() {
       guardAjax(page10).click();
    }

    public GrapheneElement getDatatable() {
        return datatable;
    }

    public void filterByModel(String filterCriteria) {
        datatableModelFilter.click();
        datatableModelFilter.sendKeys(filterCriteria);
        waitModel().until().element(modelFilterCompleteItens)
                .is().present();
        guardAjax(modelFilterCompleteItens.findElement(By.cssSelector("li.ui-autocomplete-item[data-item-value='" +
                "" + filterCriteria +"']"))).click();
    }

    public List<WebElement> getTableRows(){
        return browser.findElements(By.cssSelector("hr.ui-separator + div.ui-datatable tbody tr"));
    }

    public GrapheneElement getConfirmHeader() {
        return confirmHeader;
    }

    public GrapheneElement getDatatableModelFilter() {
        return datatableModelFilter;
    }

    public void newCar(){
        btNew.click();
    }

    public void clear() {
        guardAjax(btClear).click();
    }

    public void search() {
        guardAjax(btSearch).click();
    }

    public void remove() {
        btRemove.click();
        waitModel().until().element(confirmHeader).is().present();
        guardAjax(btYes).click();
    }
}
