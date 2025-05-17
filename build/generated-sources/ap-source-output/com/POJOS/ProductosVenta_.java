package com.POJOS;

import com.POJOS.Producto;
import com.POJOS.Venta;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-17T14:03:22")
@StaticMetamodel(ProductosVenta.class)
public class ProductosVenta_ { 

    public static volatile SingularAttribute<ProductosVenta, Venta> venta;
    public static volatile SingularAttribute<ProductosVenta, Float> precio;
    public static volatile SingularAttribute<ProductosVenta, Producto> producto;
    public static volatile SingularAttribute<ProductosVenta, Integer> cantidad;
    public static volatile SingularAttribute<ProductosVenta, Integer> idProductosVenta;
    public static volatile SingularAttribute<ProductosVenta, Float> montoTotal;

}