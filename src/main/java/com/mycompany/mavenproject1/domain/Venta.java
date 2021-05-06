package domain;

import java.time.LocalDate;

public class Venta {

    private int ventaID;
    private LocalDate fechaVenta;
    private double totalAPagar;
    private Articulo articulo;

    public Venta(LocalDate fechaVenta, double totalAPagar, Articulo articulo) {
        this.fechaVenta = fechaVenta;
        this.totalAPagar = totalAPagar;
        this.articulo = articulo;
    }

    public Venta(int ventaID, LocalDate fechaVenta, double totalAPagar, Articulo articulo) {
        this.ventaID = ventaID;
        this.fechaVenta = fechaVenta;
        this.totalAPagar = totalAPagar;
        this.articulo = articulo;
    }

    public int getVentaID() {
        return ventaID;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public double getTotalAPagar() {
        return totalAPagar;
    }

    public Articulo getArticulo() {
        return articulo;
    }
}
