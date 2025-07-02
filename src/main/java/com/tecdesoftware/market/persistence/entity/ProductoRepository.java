package com.tecdesoftware.market.persistence.entity;

import com.tecdesoftware.market.persistence.entity.crud.ProductoCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Esta anotación le dice a Spring que este archivo se enlaza con la BD
@Repository
public class ProductoRepository {

    private ProductoCrudRepository productoCrudRepository;

    //Equivalente a poner SELECT * FROM productos
    public List<Producto> getAll() {
        //Se castea de iterable a lista

        return (List<Producto>) productoCrudRepository.findAll();
    }

    //Consultas especificas
    public List<Producto> getByCategoria(int idCategoria) {
        return productoCrudRepository.findByIdCategoriaOrderByNombreAsc(idCategoria);
    }
    public Optional<List<Producto>> getEscasos(int cantidad) {
        return productoCrudRepository.findByCantidadStockLessThanAndEstado(cantidad, true);
    }

    //obtener un producto dado id
    public Optional<Producto> getProducto(int idProducto) {
        return productoCrudRepository.findById(idProducto);
    }

    //Guardar un producto
    public Producto save(Producto producto) {
        return productoCrudRepository.save(producto);
    }

    //Eliminar producto por id
    public void delete(int idProducto) {
        productoCrudRepository.deleteById(idProducto);
    }
}
