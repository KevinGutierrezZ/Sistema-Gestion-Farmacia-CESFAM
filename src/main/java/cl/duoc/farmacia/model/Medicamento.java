package cl.duoc.farmacia.model;

public class Medicamento {

    private String id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String unidadMedida;

    public Medicamento() {
    }

public Medicamento(String id, String codigo, String nombre,
                    String descripcion, String unidadMedida) {
    this.id = id;
    this.codigo = codigo;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.unidadMedida = unidadMedida;
    }

    public String getId() {
        return id;
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
}