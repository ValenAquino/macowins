package ar.edu.utn.frba.dds.regventas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ar.edu.utn.regventas.prenda.*;
import ar.edu.utn.regventas.venta.LineaDeVenta;
import ar.edu.utn.regventas.venta.Venta;
import ar.edu.utn.regventas.venta.MetodosDePago;
import java.time.LocalDate;

public class VentaTest {
  int cuotas = 1;
  int coeficiente = 1;
  int cantidadUnaPrenda = 2;
  int cantidadOtraPrenda = 1;
  LocalDate unaFecha = LocalDate.now();
  Estado prendaNueva = new PrendaNueva();
  Prenda unaPrenda = new Prenda("Remera", 500, prendaNueva);
  Prenda otraPrenda = new Prenda("Pantalon", 300, prendaNueva);
  LineaDeVenta unaLinea = new LineaDeVenta(unaPrenda, cantidadUnaPrenda);
  LineaDeVenta otraLinea = new LineaDeVenta(otraPrenda, cantidadOtraPrenda);
  Venta unaVenta = new Venta(coeficiente, cuotas, MetodosDePago.EFECTIVO, unaFecha);

  @Test
  public void elPrecioDeUnaVentaEnEfectivoEsLaSumaDelPrecioDeLasLineasDeVenta() {
    float precioDeLasLineas = unaLinea.precioTotalLinea() + otraLinea.precioTotalLinea();

    unaVenta.agregarLinea(unaLinea);
    unaVenta.agregarLinea(otraLinea);

    Assertions.assertEquals(precioDeLasLineas, unaVenta.precioFinal());
  }

  @Test
  public void elPrecioDeUnaVentaPagandoConTarjetaTieneRecargo() {
    float precioDeLasLineas = unaLinea.precioTotalLinea() + otraLinea.precioTotalLinea();
    float recargoPorTarjeta = coeficiente * cuotas + precioDeLasLineas * 0.01f;

    unaVenta.setMetodoDePago(MetodosDePago.TARJETA);
    unaVenta.agregarLinea(unaLinea);
    unaVenta.agregarLinea(otraLinea);

    Assertions.assertEquals(recargoPorTarjeta + precioDeLasLineas, unaVenta.precioFinal());
  }

}