package com.POJOS;

import com.POJOS.Cliente;
import com.POJOS.ProductosVenta;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-17T14:03:22")
@StaticMetamodel(Venta.class)
public class Venta_ { 

    public static volatile SingularAttribute<Venta, Calendar> fecha;
    public static volatile SingularAttribute<Venta, Cliente> cliente;
    public static volatile ListAttribute<Venta, ProductosVenta> listaProductos;
    public static volatile SingularAttribute<Venta, Float> montoFinal;
    public static volatile SingularAttribute<Venta, Float> descuento;
    public static volatile SingularAttribute<Venta, Integer> idVenta;

}