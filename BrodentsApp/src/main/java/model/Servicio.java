package model;

import java.math.BigDecimal;

public class Servicio {
    private int id;
    private String nombre;
    private BigDecimal precio;

    public Servicio(int id, String nombre, BigDecimal precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
