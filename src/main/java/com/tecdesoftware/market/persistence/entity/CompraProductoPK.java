package com.tecdesoftware.market.persistence.entity;

// Para unir las 2 llaves y crear una llave compuesta

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

import java.io.Serializable;

@Embeddable
//Seriezable es para que sea autoincrementable
public class CompraProductoPK implements Serializable {

    @Column (name = "id_compra")
    private int compraId;

    @Column (name = "id_producto")
    private int productoId;

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }
}
