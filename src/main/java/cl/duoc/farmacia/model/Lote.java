package cl.duoc.farmacia.model;
import java.time.LocalDate;

public class Lote {
    private String id;
    private String medicamentoId;
    private String numeroLote;
    private int cantidad;
    private LocalDate fechaRecepcion;
    private LocalDate fechaVencimiento;

    public Lote() {
    }

    public Lote(String id, String medicamentoId, String numeroLote,
                int cantidad, LocalDate fechaRecepcion, LocalDate fechaVencimiento) {
        this.id = id;
        this.medicamentoId = medicamentoId;
        this.numeroLote = numeroLote;
        this.cantidad = cantidad;
        this.fechaRecepcion = fechaRecepcion;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getId() {
        return id;
    }


    public String getMedicamentoId() {
        return medicamentoId;
    }

    public void setMedicamentoId(String medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(LocalDate fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
