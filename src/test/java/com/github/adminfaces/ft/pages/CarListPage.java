package com.github.adminfaces.ft.pages;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

@Location("login.xhtml")
public class CarListPage {


    @FindByJQuery("input[id$=inptModel]")
    private GrapheneElement inputModel;

    @FindByJQuery("input[id$=inptName]")
    private GrapheneElement inputName;

    @FindByJQuery("input[id$=inptPrice]")
    private GrapheneElement inputPrice;

    @FindByJQuery("button[id$=brFind]")
    private GrapheneElement btFind;

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

    @FindByJQuery(".ui-column-customfilter span.ui-autocomplete > input")
    private GrapheneElement datatableModelFilter;

    @FindByJQuery("ul.ui-autocomplete-items")
    private GrapheneElement modelFilterCompleteItens;

    @FindByJQuery("span.ui-dialog-title:contains('Confirmation')")
    private GrapheneElement confirmHeader;

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
        datatableModelFilter.sendKeys(filterCriteria);
        waitModel().until().element(modelFilterCompleteItens)
                .is().present();
        guardAjax(modelFilterCompleteItens.findElement(By.cssSelector("li.ui-autocomplete-item[data-item-value='" +
                "" + filterCriteria +"']"))).click();
    }

    public List<GrapheneElement> getTableRows(){
        return datatableRows;
    }

    public void clear() {
        guardAjax(btClear).click();
    }

    public void remove() {
        guardAjax(btRemove).click();
        waitModel().until().element(confirmHeader).is().present();
        guardAjax(btYes).click();
    }
}
