package com.POJOS;

import com.POJOS.Cliente;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-03-27T00:14:25")
@StaticMetamodel(Venta.class)
public class Venta_ { 

    public static volatile SingularAttribute<Venta, Calendar> fecha;
    public static volatile SingularAttribute<Venta, Cliente> cliente;
    public static volatile SingularAttribute<Venta, Float> montoFinal;
    public static volatile SingularAttribute<Venta, Float> descuento;
    public static volatile SingularAttribute<Venta, Integer> idVenta;

}