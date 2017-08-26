package com.github.adminfaces.ft.util;

import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
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
        WebArchive war = ShrinkWrap.create(WebArchive.class);
        war.addPackages(true, "com.github.adminfaces.starter");
        //LIBS
        MavenResolverSystem resolver = Maven.resolver();
        war.addAsLibraries(resolver.loadPomFromFile("pom.xml").resolve("com.github.adminfaces:admin-template").withTransitivity().asFile());
        war.addAsLibraries(resolver.loadPomFromFile("pom.xml").resolve("com.github.adminfaces:admin-persistence").withTransitivity().asFile());

        war.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory("src/main/webapp").as(GenericArchive.class), "/", Filters.include(".*\\.(xml|xhtml|html|css|js|png|gif)$"));

        //resources
        war.addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml");

        war.addAsResource(new File("src/main/resources/admin-config.properties"), "admin-config.properties");
        war.addAsResource(new File("src/main/resources/messages.properties"), "messages.properties");

        return war;
    }

}
