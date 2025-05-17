package com.POJOS;

import com.POJOS.Producto;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-17T14:03:22")
@StaticMetamodel(Proveedor.class)
public class Proveedor_ { 

    public static volatile SingularAttribute<Proveedor, String> paginaWeb;
    public static volatile SingularAttribute<Proveedor, Integer> idProveedor;
    public static volatile SingularAttribute<Proveedor, String> direccion;
    public static volatile SingularAttribute<Proveedor, String> telefono;
    public static volatile SingularAttribute<Proveedor, String> nombre;
    public static volatile ListAttribute<Proveedor, Producto> productos;
    public static volatile SingularAttribute<Proveedor, String> rfc;

}