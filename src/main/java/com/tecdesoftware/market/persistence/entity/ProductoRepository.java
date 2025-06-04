package com.tecdesoftware.market.persistence.entity;

import com.tecdesoftware.market.persistence.entity.crud.ProductoCrudRepository;

import java.util.List;

public class ProductoRepository {

    private ProductoCrudRepository productoCrudRepository;

    //Equivalente a poner SELECT * FROM productos
    public List<Producto> getAll() {
        //Se castea de iterable a lista
        return (List<Producto>) productoCrudRepository.findAll();
    }
}
