package cl.duoc.farmacia.dao;

import cl.duoc.farmacia.model.Medicamento;
import cl.duoc.farmacia.model.Lote;

import java.util.List;

public interface FarmaciaDAO {

    List<Medicamento> cargarMedicamentos();

    void guardarMedicamentos(List<Medicamento> medicamentos);

    List<Lote> cargarLotes();

    void guardarLotes(List<Lote> lotes);

}