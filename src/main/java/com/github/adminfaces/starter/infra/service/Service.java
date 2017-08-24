package com.github.adminfaces.starter.infra.service;

import javax.inject.Qualifier;
import java.lang.annotation.*;

/**
 * Marker interface to allow generic CrudServive injection: @Inject @Service CrudService<Entity,PK>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER,ElementType.TYPE})
@Qualifier
@Documented
public @interface Service {
}
