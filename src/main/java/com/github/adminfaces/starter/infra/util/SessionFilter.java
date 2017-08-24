package com.github.adminfaces.starter.infra.util;

import com.github.adminfaces.starter.infra.bean.CrudMB;
import com.github.adminfaces.starter.infra.model.BaseEntity;
import com.github.adminfaces.starter.infra.model.Filter;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rmpestano
 * <p>
 * A map of {@link com.github.adminfaces.starter.infra.model.Filter} to Keep in session for each CrudMB
 */
@SessionScoped
@SuppressWarnings({"rawtypes", "unchecked"})
public class SessionFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<Class<? extends CrudMB>, Filter<? extends BaseEntity>> sessionMap = new HashMap<>();

    public void add(Class<? extends CrudMB> key, Filter<? extends BaseEntity> value) {
        sessionMap.put(key, value);
    }

    public void clear(Class<? extends CrudMB> key) {
        if (sessionMap.containsKey(key)) {
            sessionMap.put(key, null);
        }
    }

    public Filter<? extends BaseEntity> get(Class<? extends CrudMB> key) {
        return sessionMap.get(key);
    }

}
