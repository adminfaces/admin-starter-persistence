package com.github.adminfaces.starter.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CarPK implements Serializable {

    private Integer id;
    private Long id2;

    public CarPK() {
    }

    public CarPK(Integer id, Long id2) {
        this.id = id;
        this.id2 = id2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getId2() {
        return id2;
    }

    public void setId2(Long id2) {
        this.id2 = id2;
    }

    @Override
    public String toString() {
        return "id:"+id + " id2:"+id2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarPK carPK = (CarPK) o;

        if (id != null ? !id.equals(carPK.id) : carPK.id != null) return false;
        return id2 != null ? id2.equals(carPK.id2) : carPK.id2 == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (id2 != null ? id2.hashCode() : 0);
        return result;
    }
}
