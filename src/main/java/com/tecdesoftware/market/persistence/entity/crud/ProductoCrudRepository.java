package com.tecdesoftware.market.persistence.entity.crud;

import com.tecdesoftware.market.persistence.entity.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoCrudRepository extends CrudRepository<Producto, Integer> {

    //Query method (por medio del nombre del metodo, genera un querry)
    /*
        SELECT *
        FROM Catergoria
        WHERE id_categoria  = ?
        ORDER BY Nombre ASC
     */

    //Obtener una lista de productos filtrados por id_categoria y ordenados ascendentemente por nombre
    List<Producto> findByIdCategoriaOrderByNombreAsc(int idCategoria);

    //Obtener los productos escasos
    Optional<List<Producto>> findByCantidadStockLessThanAndEstado(int cantidad, boolean estado);
}

