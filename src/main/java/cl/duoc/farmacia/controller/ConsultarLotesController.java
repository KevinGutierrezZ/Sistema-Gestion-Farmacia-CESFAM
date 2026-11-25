package cl.duoc.farmacia.controller;
import cl.duoc.farmacia.model.Lote;
import cl.duoc.farmacia.model.Medicamento;
import cl.duoc.farmacia.repository.FarmaciaRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConsultarLotesController {

    @FXML
    private Label lblTitulo;

    @FXML
    private TableView<Lote> tablaLotes;

    @FXML
    private TableColumn<Lote, String> colNumeroLote;

    @FXML
    private TableColumn<Lote, Integer> colCantidad;

    @FXML
    private TableColumn<Lote, String> colFechaRecepcion;

    @FXML
    private TableColumn<Lote, String> colFechaVencimiento;

    private FarmaciaRepository repository;

    private Medicamento medicamento;

    public void setRepository(FarmaciaRepository repository) {
        this.repository = repository;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;

        lblTitulo.setText(
                "Lotes de " + medicamento.getNombre()
                        + " (" + medicamento.getCodigo() + ")"
        );
    }

    public void cargarLotes() {

        colNumeroLote.setCellValueFactory(
                new PropertyValueFactory<>("numeroLote")
        );

        colCantidad.setCellValueFactory(
                new PropertyValueFactory<>("cantidad")
        );

        DateTimeFormatter formatoFecha =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        colFechaRecepcion.setCellValueFactory(cellData -> {

            LocalDate fecha = cellData.getValue().getFechaRecepcion();

            String fechaFormateada = fecha == null
                    ? "Sin fecha"
                    : fecha.format(formatoFecha);

            return new ReadOnlyObjectWrapper<>(fechaFormateada);
        });

        colFechaVencimiento.setCellValueFactory(cellData -> {

            LocalDate fecha = cellData.getValue().getFechaVencimiento();

            String fechaFormateada = fecha == null
                    ? "Sin fecha"
                    : fecha.format(formatoFecha);

            return new ReadOnlyObjectWrapper<>(fechaFormateada);
        });

        tablaLotes.setItems(
                FXCollections.observableArrayList(
                        repository.obtenerLotesPorMedicamento(
                                medicamento.getId()
                        )
                )
        );
    }
}
