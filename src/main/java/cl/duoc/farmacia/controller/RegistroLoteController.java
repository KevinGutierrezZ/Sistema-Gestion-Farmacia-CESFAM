package cl.duoc.farmacia.controller;
import cl.duoc.farmacia.model.Medicamento;
import cl.duoc.farmacia.repository.FarmaciaRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.util.StringConverter;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.time.LocalDate;

public class RegistroLoteController {

    @FXML
    private ComboBox<Medicamento> cmbMedicamento;

    @FXML
    private TextField txtNumeroLote;

    @FXML
    private TextField txtCantidad;

    @FXML
    private DatePicker dpFechaRecepcion;

    @FXML
    private DatePicker dpFechaVencimiento;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    private FarmaciaRepository repository;

    public void setRepository(FarmaciaRepository repository) {
        this.repository = repository;
    }

    @FXML
    public void initialize() {

        cmbMedicamento.setConverter(new StringConverter<>() {

            @Override
            public String toString(Medicamento medicamento) {
                if (medicamento == null) {
                    return "";
                }

                return medicamento.getNombre() + " (" + medicamento.getCodigo() + ")";
            }

            @Override
            public Medicamento fromString(String texto) {
                return null;
            }
        });
    }

    public void cargarMedicamentos() {
        cmbMedicamento.setItems(
                FXCollections.observableArrayList(
                        repository.obtenerMedicamentos()
                )
        );
    }

    @FXML
    private void guardarLote() {

        Medicamento medicamento = cmbMedicamento.getValue();

        if (medicamento == null) {
            mostrarAlerta("Advertencia", "Debe seleccionar un medicamento.");
            return;
        }

        try {

            String numeroLote = txtNumeroLote.getText().trim();

            int cantidad = Integer.parseInt(txtCantidad.getText().trim());

            LocalDate fechaRecepcion = dpFechaRecepcion.getValue();

            LocalDate fechaVencimiento = dpFechaVencimiento.getValue();

            repository.registrarLote(
                    medicamento.getId(),
                    numeroLote,
                    cantidad,
                    fechaRecepcion,
                    fechaVencimiento
            );

            mostrarAlerta("Éxito", "El lote fue registrado correctamente.");

            cerrarVentana();

        } catch (NumberFormatException e) {

            mostrarAlerta("Error", "La cantidad debe ser un número entero.");

        } catch (IllegalArgumentException e) {

            mostrarAlerta("Error", e.getMessage());

        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }

    private void cerrarVentana() {

        Stage ventana = (Stage) btnCancelar.getScene().getWindow();

        ventana.close();
    }

    @FXML
    private void cancelar() {
        cerrarVentana();
    }
}