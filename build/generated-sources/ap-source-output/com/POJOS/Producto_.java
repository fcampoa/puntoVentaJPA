package com.POJOS;

import com.POJOS.Categoria;
import com.POJOS.Proveedor;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-17T14:03:22")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile SingularAttribute<Producto, Float> precioActual;
    public static volatile SingularAttribute<Producto, Categoria> categoria;
    public static volatile SingularAttribute<Producto, Proveedor> proveedor;
    public static volatile SingularAttribute<Producto, Integer> idProducto;
    public static volatile SingularAttribute<Producto, Integer> stock;
    public static volatile SingularAttribute<Producto, String> nombre;

}