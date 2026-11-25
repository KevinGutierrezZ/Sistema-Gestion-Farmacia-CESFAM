package cl.duoc.farmacia.repository;
import cl.duoc.farmacia.dao.FarmaciaDAO;
import cl.duoc.farmacia.model.Medicamento;
import cl.duoc.farmacia.model.Lote;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;
import java.util.LinkedHashMap;

public class FarmaciaRepository {

    private final FarmaciaDAO dao;

    private final List<Medicamento> medicamentos;
    private final List<Lote> lotes;

    public FarmaciaRepository(FarmaciaDAO dao) {
        this.dao = dao;

        this.medicamentos = new ArrayList<>(dao.cargarMedicamentos());
        this.lotes = new ArrayList<>(dao.cargarLotes());
    }

    public void registrarMedicamento(String codigo, String nombre,
                                     String descripcion, String unidadMedida) {

        if (codigo == null || codigo.isBlank()
                || nombre == null || nombre.isBlank()
                || unidadMedida == null || unidadMedida.isBlank()) {

            throw new IllegalArgumentException("Faltan datos obligatorios del medicamento.");
        }

        boolean existe = medicamentos.stream()
                .anyMatch(m -> m.getCodigo().equalsIgnoreCase(codigo));

        if (existe) {
            throw new IllegalArgumentException("Ya existe un medicamento con ese código.");
        }

        String id = java.util.UUID.randomUUID().toString();

        Medicamento medicamento = new Medicamento(
                id, codigo, nombre, descripcion, unidadMedida
        );

        medicamentos.add(medicamento);

        dao.guardarMedicamentos(medicamentos);
    }

    public List<Medicamento> obtenerMedicamentos() {
        return new ArrayList<>(medicamentos);
    }

    public List<Medicamento> buscarMedicamentos(String criterio) {

        if (criterio == null || criterio.isBlank()) {
            return obtenerMedicamentos();
        }

        String busqueda = criterio.toLowerCase().trim();

        return medicamentos.stream()
                .filter(m -> m.getNombre().toLowerCase().contains(busqueda)
                        || m.getCodigo().toLowerCase().contains(busqueda))
                .toList();
    }

    public void registrarLote(String medicamentoId, String numeroLote,
                              int cantidad, LocalDate fechaRecepcion,
                              LocalDate fechaVencimiento) {

        if (medicamentoId == null || medicamentoId.isBlank()
                || numeroLote == null || numeroLote.isBlank()
                || cantidad <= 0 || fechaRecepcion == null) {

            throw new IllegalArgumentException("Los datos del lote son inválidos.");
        }

        if (fechaVencimiento != null && fechaVencimiento.isBefore(fechaRecepcion)) {
            throw new IllegalArgumentException(
                    "La fecha de vencimiento no puede ser anterior a la recepción."
            );
        }

        boolean medicamentoExiste = medicamentos.stream()
                .anyMatch(m -> m.getId().equals(medicamentoId));

        if (!medicamentoExiste) {
            throw new IllegalArgumentException(
                    "No existe el medicamento asociado a este lote."
            );
        }

        String id = java.util.UUID.randomUUID().toString();

        Lote lote = new Lote(
                id, medicamentoId, numeroLote, cantidad,
                fechaRecepcion, fechaVencimiento
        );

        lotes.add(lote);

        dao.guardarLotes(lotes);
    }

    public int obtenerStock(String medicamentoId) {

        if (medicamentoId == null || medicamentoId.isBlank()) {
            throw new IllegalArgumentException("El ID del medicamento es obligatorio.");
        }

        boolean medicamentoExiste = medicamentos.stream()
                .anyMatch(m -> m.getId().equals(medicamentoId));

        if (!medicamentoExiste) {
            throw new IllegalArgumentException("El medicamento no existe.");
        }

        return lotes.stream()
                .filter(l -> l.getMedicamentoId().equals(medicamentoId))
                .mapToInt(Lote::getCantidad)
                .sum();
    }

    public List<Lote> obtenerLotesPorMedicamento(String medicamentoId) {

        if (medicamentoId == null || medicamentoId.isBlank()) {
            throw new IllegalArgumentException(
                    "El ID del medicamento es obligatorio."
            );
        }

        boolean medicamentoExiste = medicamentos.stream()
                .anyMatch(m -> m.getId().equals(medicamentoId));

        if (!medicamentoExiste) {
            throw new IllegalArgumentException(
                    "El medicamento no existe."
            );
        }

        return lotes.stream()
                .filter(l -> l.getMedicamentoId().equals(medicamentoId))
                .toList();
    }

    public void editarMedicamento(String id, String codigo, String nombre,
                                  String descripcion, String unidadMedida) {

        if (id == null || id.isBlank()
                || codigo == null || codigo.isBlank()
                || nombre == null || nombre.isBlank()
                || unidadMedida == null || unidadMedida.isBlank()) {

            throw new IllegalArgumentException("Faltan datos obligatorios.");
        }

        Medicamento medicamento = medicamentos.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("El medicamento no existe."));

        boolean codigoExiste = medicamentos.stream()
                .anyMatch(m -> !m.getId().equals(id)
                        && m.getCodigo().equalsIgnoreCase(codigo));

        if (codigoExiste) {
            throw new IllegalArgumentException(
                    "Ya existe otro medicamento con ese código.");
        }

        medicamento.setCodigo(codigo);
        medicamento.setNombre(nombre);
        medicamento.setDescripcion(descripcion);
        medicamento.setUnidadMedida(unidadMedida);

        dao.guardarMedicamentos(medicamentos);
    }

    public void eliminarMedicamento(String id) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID del medicamento es obligatorio.");
        }

        boolean eliminado = medicamentos.removeIf(
                m -> m.getId().equals(id)
        );

        if (!eliminado) {
            throw new IllegalArgumentException("El medicamento no existe.");
        }

        lotes.removeIf(l -> l.getMedicamentoId().equals(id));

        dao.guardarMedicamentos(medicamentos);
        dao.guardarLotes(lotes);
    }

    public Map<Medicamento, Integer> obtenerInventario() {

        Map<Medicamento, Integer> inventario = new LinkedHashMap<>();

        for (Medicamento medicamento : medicamentos) {

            int stock = obtenerStock(medicamento.getId());

            inventario.put(medicamento, stock);
        }

        return inventario;
    }
}
