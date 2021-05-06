package dataAccess;

import domain.Articulo;
import domain.Empeno;
import domain.Prestamo;
import domain.Venta;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentaDA {
    private Articulo articulo;
    private Date fechaSQL;
    private ResultSet rs;
    private Venta ve;
    private PreparedStatement stat;
    private int ventaId;
    private int ventaArticuloId;

    public VentaDA() {
    }

    public int registrarVenta(Venta ve) {

        this.ve=ve;
        this.articulo = ve.getArticulo();

        String sentenciaSQL = "insert into Ventas (fechaDeVenta, totalAPagar) values (?,?)";
        this.fechaSQL = Date.valueOf(this.ve.getFechaVenta());
        Object[] parametrosVenta = new Object[]{this.fechaSQL,this.ve.getTotalAPagar()};

        try {
            Db.ejecutarQueryPrepared(sentenciaSQL, parametrosVenta);
        } catch (SQLException var12) {
            var12.printStackTrace();
        } catch (Exception var13) {
            var13.printStackTrace();
        }

        this.rs = Db.getGeneratedKeys();

        try {
            while(this.rs.next()) {
                this.ventaId = this.rs.getInt(1);
                System.out.println("Key de la Venta" + this.ventaId);
            }
        } catch (Exception var15) {
            var15.printStackTrace();
        }

        sentenciaSQL = "insert into VentaArticulos (ventaId, articuloId) values (?,?)";
        Object[] parametrosVentaArticulo = new Object[]{this.ve.getVentaID(),this.articulo.getArticuloId()};

        try {
            Db.ejecutarQueryPrepared(sentenciaSQL, parametrosVentaArticulo);
        } catch (SQLException var10) {
            var10.printStackTrace();
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        this.rs = Db.getGeneratedKeys();

        try {
            while(this.rs.next()) {
                this.ventaArticuloId = this.rs.getInt(1);
                System.out.println("Imprime VentaArticulo Key : " + this.ventaArticuloId);
            }
        } catch (Exception var14) {
            var14.printStackTrace();
        }

        return 0;
    }
}
