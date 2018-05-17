package com.lulusuit.backend.data;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

@Repository
public class GenericDao<T extends Serializable> extends AbstractDao<T> implements IGenericDao<T> {

}
