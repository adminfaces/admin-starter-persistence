package com.github.adminfaces.starter.infra.bean;

import com.github.adminfaces.starter.infra.model.BaseEntity;
import com.github.adminfaces.starter.infra.model.Filter;
import com.github.adminfaces.starter.infra.service.CrudService;
import com.github.adminfaces.starter.infra.util.SessionFilter;
import com.github.adminfaces.starter.model.Car;
import org.omnifaces.util.Faces;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import static com.github.adminfaces.template.util.Assert.has;

public abstract class CrudMB<T extends BaseEntity, PK extends Serializable> {

    protected final Logger LOG = LoggerFactory.getLogger(getClass().getName());

    private CrudService<T, ?> crudService;

    protected T entity;

    protected PK id;

    protected Filter<T> filter;

    protected LazyDataModel<T> list; //datatable pagination

    protected List<T> selectionList; //holds selected rows in datatable with multiple selection (checkbox column)

    protected T selection; //holds single selection

    protected List<T> filteredValue;// datatable filteredValue attribute (column filters)

    @Inject
    protected SessionFilter sessionFilter; //save filters in session

    @PostConstruct
    public void initCrudMB() {

        if (getCrudService() == null) {
            LOG.error("You need to initialize CrudService on your Managed Bean and call setCrudService(yourService) or override getCrudService()");
            throw new RuntimeException("You need to initialize CrudService on your Managed Bean and call setCrudService(yourService) or override getCrudService()");
        }

        entity = initEntity();

        filter = initFilter();

        list = initList();
    }

    protected LazyDataModel<T> initList() {
        return new LazyDataModel<T>() {

            @Override
            public List<T> load(int first, int pageSize,
                                String sortField, SortOrder sortOrder,
                                Map<String, Object> filters) {
                com.github.adminfaces.starter.infra.model.SortOrder order = null;
                if (sortOrder != null) {
                    order = sortOrder.equals(SortOrder.ASCENDING) ? com.github.adminfaces.starter.infra.model.SortOrder.ASCENDING
                            : sortOrder.equals(SortOrder.DESCENDING) ? com.github.adminfaces.starter.infra.model.SortOrder.DESCENDING
                            : com.github.adminfaces.starter.infra.model.SortOrder.UNSORTED;
                }

                if (filters == null || filters.isEmpty() && keepFiltersInSession()) {
                    filters = filter.getParams();
                }

                filter.setFirst(first).setPageSize(pageSize)
                        .setSortField(sortField).setSortOrder(order)
                        .setParams(filters);
                List<T> list = crudService.paginate(filter);
                setRowCount((int) crudService.count(filter));
                return list;
            }

            @Override
            public int getRowCount() {
                return super.getRowCount();
            }

            @Override
            public T getRowData(String key) {
                return crudService.findById((PK) key);
            }
        };
    }


    //called view preRenderView or viewAction
    public void init() {
        if (Faces.isAjaxRequest()) {
            return;
        }
        if (has(id)) {
            entity = crudService.findById(id);
        }
    }

    protected Filter<T> initFilter() {
        Filter<T> filter;
        if (keepFiltersInSession()) {
            filter = (Filter<T>) sessionFilter.get(getClass());
            if (filter == null) {
                filter = createDefaultFilters();
                sessionFilter.add(getClass(), filter);
            }
        } else {
            filter = createDefaultFilters();
        }

        return filter;
    }

    public boolean isNew() {
        return entity == null || entity.getId() == null;
    }

    public void clear() {
        if(keepFiltersInSession()) {
            sessionFilter.clear(getClass());
        }
        filter = initFilter();
        entity = initEntity();
    }

    protected T initEntity() {
        return createDefaultEntity();
    }

    public T createDefaultEntity() {
        try {
            return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.error(String.format("Could not create entity class for bean %s", getClass().getName()), e);
            throw new RuntimeException(e);
        }
    }

    public Filter<T> createDefaultFilters() {
        try {
            return new Filter<>(((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.error(String.format("Could not create filters for bean %s", getClass().getName()), e);
            throw new RuntimeException(e);
        }
    }

    public boolean keepFiltersInSession() {
        return true;
    }

    public void setCrudService(CrudService<T, ?> crudService) {
        this.crudService = crudService;
    }

    public LazyDataModel<T> getList() {
        return list;
    }

    public void setList(LazyDataModel<T> list) {
        this.list = list;
    }

    public List<T> getSelectionList() {
        return selectionList;
    }

    public void setSelectionList(List<T> selectionList) {
        this.selectionList = selectionList;
    }

    public T getSelection() {
        return selection;
    }

    public void setSelection(T selection) {
        this.selection = selection;
    }

    public List<T> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<T> filteredValue) {
        this.filteredValue = filteredValue;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Filter<T> getFilter() {
        return filter;
    }

    public void setFilter(Filter<T> filter) {
        this.filter = filter;
    }

    public CrudService<T, ?> getCrudService() {
        return crudService;
    }

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }
}
