package com.github.adminfaces.util;

import com.github.adminfaces.template.exception.BusinessException;
import com.github.adminfaces.template.util.Assert;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;

import java.io.File;

/**
 * @author rafael-pestano
 *         Arquillian WebArchive factory
 */
public class Deployments {


    protected static final String WEB_INF= "src/main/webapp/WEB-INF";

    /**
     * @return base WebArchive for all arquillian tests
     */
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "admin-starter-test.war");
        war.addPackages(true, "com.github.adminfaces.starter");
        war.addClasses(BusinessException.class, Assert.class);
        //LIBS
        MavenResolverSystem resolver = Maven.resolver();
        war.addAsLibraries(resolver.loadPomFromFile("pom.xml").resolve("com.github.adminfaces:admin-persistence").withTransitivity().asFile());
        war.addAsLibraries(resolver.loadPomFromFile("pom.xml").resolve("org.primefaces.extensions:primefaces-extensions").withTransitivity().asFile());
        war.addAsLibraries(resolver.loadPomFromFile("pom.xml").resolve("org.omnifaces:omnifaces").withTransitivity().asFile());


        //WEB-INF

        war.addAsWebInfResource(new File(WEB_INF,"beans.xml"), "beans.xml");
        war.addAsWebInfResource(new File(WEB_INF,"web.xml"), "web.xml");
        war.addAsWebInfResource(new File(WEB_INF,"faces-config.xml"), "faces-config.xml");
        //resources
        war.addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml");

        war.addAsResource(new File("src/main/resources/admin-config.properties"), "admin-config.properties");
        war.addAsResource(new File("src/main/resources/messages.properties"), "messages.properties");

        return war;
    }

}
