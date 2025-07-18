package com.tecdesoftware.market.persistence.mapper;

import com.tecdesoftware.market.domain.PurchaseItem;
import com.tecdesoftware.market.persistence.entity.CompraProducto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface PurchaseItemMapper {

    @Mappings({
            @Mapping( source = "id.productoId", target = "productId" ),
            @Mapping( source = "cantidad", target = "quantity" ),
            // @Mapping( source = "total", target = "total" ),  No es necesario por que se llaman igual
            @Mapping( source = "estado", target = "active" ),
    })
    PurchaseItem toPurchaseItem(CompraProducto producto);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping( target = "id.compraId", ignore = true),
            @Mapping( target = "compra", ignore = true),
            @Mapping( target = "productos", ignore = true),
    })
    CompraProducto toCompraProducto(PurchaseItem item);
}
