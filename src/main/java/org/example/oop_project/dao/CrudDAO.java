package org.example.oop_project.dao;

import org.example.oop_project.entity.SuperEntity;

public interface CrudDAO<T extends SuperEntity, ID> extends SuperDAO{
    public boolean save(T t);

    public boolean update(T t);

}
