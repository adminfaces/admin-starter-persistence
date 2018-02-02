package com.github.adminfaces.it;

import com.github.adminfaces.persistence.model.AdminSort;
import com.github.adminfaces.persistence.model.Filter;
import com.github.adminfaces.persistence.service.CrudService;
import com.github.adminfaces.persistence.service.Service;
import com.github.adminfaces.starter.model.Car;
import com.github.adminfaces.starter.model.Car_;
import com.github.adminfaces.starter.service.CarService;
import com.github.adminfaces.template.exception.BusinessException;
import com.github.adminfaces.util.Deployments;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class AdminIt {


    @Deployment(name = "admin-starter-it.war",testable = true)
    public static WebArchive createDeployment() {
        WebArchive war = Deployments.createDeployment();
        MavenResolverSystem resolver = Maven.resolver();
        war.addAsLibraries(resolver.loadPomFromFile("pom.xml").resolve("org.assertj:assertj-core").withTransitivity().asFile());
        System.out.println(war.toString(true));
        return war;
    }

    @Inject
    CarService carService;

    @Inject
    @Service
    CrudService<Car, Integer> crudService;


    @Test
    @UsingDataSet("cars.yml")
    public void shouldCountCars() {
        assertEquals(carService.count().intValue(), 4);
    }

    @Test
    @UsingDataSet("cars.yml")
    public void shouldFindCarById() {
        Car car = carService.findById(1);
        assertThat(car).isNotNull()
                .extracting("id")
                .contains(new Integer(1));
    }

    @Test
    @UsingDataSet("cars.yml")
    public void shouldFindCarByExample() {
        Car carExample = new Car().model("Ferrari");
        List<Car> cars = carService.example(carExample,Car_.model).getResultList();
        assertThat(cars).isNotNull()
                .hasSize(1)
                .extracting("id")
                .contains(new Integer(1));
    }

    @Test
    @Cleanup(phase = TestExecutionPhase.BEFORE)
    public void shouldNotInsertCarWithoutName() {
        long countBefore = carService.count();
        assertEquals(countBefore, 0);
        Car newCar = new Car().model("My Car").price(1d);
        try {
            carService.insert(newCar);
        } catch (BusinessException e) {
            assertEquals("Car name cannot be empty", e.getExceptionList().get(0).getMessage());
        }
    }

    @Test
    public void shouldNotInsertCarWithoutModel() {
        Car newCar = new Car().name("My Car")
                .price(1d);
        try {
            carService.insert(newCar);
        } catch (BusinessException e) {
            assertEquals("Car model cannot be empty", e.getExceptionList().get(0).getMessage());
        }
    }

    @Test
    @UsingDataSet("cars.yml")
    @Cleanup(phase = TestExecutionPhase.BEFORE)
    public void shouldNotInsertCarWithDuplicateName() {
        Car newCar = new Car().model("My Car")
                .name("ferrari spider")
                .price(1d);
        try {
            carService.insert(newCar);
        } catch (BusinessException e) {
            assertEquals("Car name must be unique", e.getExceptionList().get(0).getMessage());
        }
    }

    @Test
    public void shouldInsertCar() {
        long countBefore = carService.count();
        assertEquals(countBefore, 0);
        Car newCar = new Car().model("My Car")
                .name("car name").price(1d);
        carService.insert(newCar);
        assertEquals(countBefore + 1, carService.count().intValue());
    }

    @Test
    @UsingDataSet("cars.yml")
    @Transactional(TransactionMode.DISABLED)
    public void shouldRemoveCar() {
        assertEquals(carService.count(carService.criteria().eq(Car_.id,1)).intValue(),1);
        Car car = carService.findById(1);
        assertNotNull(car);
        carService.remove(car);
        assertEquals(carService.count(carService.criteria().eq(Car_.id,1)).intValue(),0);
    }

    @Test
    @UsingDataSet("cars.yml")
    public void shouldListCarsModel() {
        List<Car> cars = carService.listByModel("%porche%");
        assertThat(cars).isNotNull().hasSize(2);
    }

    @Test
    @UsingDataSet("cars.yml")
    public void shouldPaginateCars() {
        Filter<Car> carFilter = new Filter<Car>().setFirst(0).setPageSize(1);
        List<Car> cars = carService.paginate(carFilter);
        assertNotNull(cars);
        assertEquals(cars.size(), 1);
        assertEquals(cars.get(0).getId(), new Integer(1));
        carFilter.setFirst(1);//get second database page
        cars = carService.paginate(carFilter);
        assertNotNull(cars);
        assertEquals(cars.size(), 1);
        assertEquals(cars.get(0).getId(), new Integer(2));
        carFilter.setFirst(0);
        carFilter.setPageSize(4);
        cars = carService.paginate(carFilter);
        assertEquals(cars.size(), 4);
    }

    @Test
    @UsingDataSet("cars.yml")
    public void shouldPaginateAndSortCars() {
        Filter<Car> carFilter = new Filter<Car>()
                .setFirst(0)
                .setPageSize(4)
                .setSortField("model")
                .setAdminSort(AdminSort.DESCENDING);
        List<Car> cars = carService.paginate(carFilter);
        assertThat(cars).isNotNull().hasSize(4);
        assertTrue(cars.get(0).getModel().equals("Porche274"));
        assertTrue(cars.get(3).getModel().equals("Ferrari"));
    }

    @Test
    @UsingDataSet("cars.yml")
    public void shouldPaginateCarsByModel() {
        Car carExample = new Car().model("Ferrari");
        Filter<Car> carFilter = new Filter<Car>().
                setFirst(0).setPageSize(4)
                .setEntity(carExample);
        List<Car> cars = carService.paginate(carFilter);
        assertThat(cars).isNotNull().hasSize(1)
                .extracting("model").contains("Ferrari");
    }

    @Test
    @UsingDataSet("cars.yml")
    public void shouldPaginateCarsByPrice() {
        Car carExample = new Car().price(12999.0);
        Filter<Car> carFilter = new Filter<Car>().setFirst(0).setPageSize(2).setEntity(carExample);
        List<Car> cars = carService.paginate(carFilter);
        assertThat(cars).isNotNull().hasSize(1)
                .extracting("model").contains("Mustang");
    }

    @Test
    @UsingDataSet("cars.yml")
    public void shouldPaginateCarsByIdInParam() {
        Filter<Car> carFilter = new Filter<Car>().setFirst(0).setPageSize(2).addParam("id", 1);
        List<Car> cars = carService.paginate(carFilter);
        assertThat(cars).isNotNull().hasSize(1)
                .extracting("id").contains(1);
    }

    @Test
    @UsingDataSet("cars.yml")
    @Transactional(value = TransactionMode.DISABLED)
    public void shouldListCarsByPrice() {
        List<Car> cars = carService.criteria()
                .between(Car_.price, 1000D, 2450.9)
                .orderAsc(Car_.price).getResultList();
        //ferrari and porche
        assertThat(cars).isNotNull()
                .hasSize(2).extracting("model")
                .contains("Porche","Ferrari");
    }

    @Test
    @UsingDataSet("cars.yml")
    public void shouldGetCarModels() {
        List<String> models = carService.getModels("po");
        //porche and Porche274
        assertThat(models).isNotNull().hasSize(2)
                .contains("Porche","Porche274");
    }

    @Test
    @UsingDataSet("cars.yml")
    public void shoulListCarsUsingCrudUtility() {
        assertEquals(4, crudService.count().intValue());
        long count = crudService.count(crudService.criteria()
                .likeIgnoreCase(Car_.model, "%porche%")
                .gtOrEq(Car_.price, 10000D));
        assertEquals(1, count);

    }

    @Test
    @UsingDataSet("cars.yml")
    public void shouldFindCarsByExample() {
        Car carExample = new Car().model("Ferrari");
        List<Car> cars = crudService.example(carExample,Car_.model).getResultList();
        assertThat(cars).isNotNull().hasSize(1)
                .extracting("model")
                .contains("Ferrari");

        carExample = new Car().model("porche").name("%avenger");
        cars = crudService.exampleLike(carExample, Car_.name, Car_.model).getResultList();

        assertThat(cars).isNotNull().hasSize(1)
                .extracting("name")
                .contains("porche avenger");

    }

    @Test
    @UsingDataSet("cars.yml")
    public void shoulGetTotalPriceByModel() {
        assertEquals((Double) 20380.53, carService.getTotalPriceByModel(new Car().model("%porche%")));
    }
}
