package cl.duoc.farmacia.controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import cl.duoc.farmacia.repository.FarmaciaRepository;
import javafx.scene.control.Alert;
import cl.duoc.farmacia.model.Medicamento;

public class RegistroMedicamentoController {

    private FarmaciaRepository repository;
    private Medicamento medicamentoEditar;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtUnidadMedida;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    @FXML
    private void guardarMedicamento() {

        try {

            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String unidadMedida = txtUnidadMedida.getText().trim();

            if (medicamentoEditar == null) {

                repository.registrarMedicamento(
                        codigo,
                        nombre,
                        descripcion,
                        unidadMedida
                );

            } else {

                repository.editarMedicamento(
                        medicamentoEditar.getId(),
                        codigo,
                        nombre,
                        descripcion,
                        unidadMedida
                );
            }

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Registro exitoso");
            alerta.setHeaderText(null);
            alerta.setContentText("El medicamento se registró correctamente.");
            alerta.showAndWait();

            btnGuardar.getScene().getWindow().hide();

        } catch (IllegalArgumentException e) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error de registro");
            alerta.setHeaderText("No se pudo registrar el medicamento");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void cancelar() {

        System.out.println("Botón Cancelar presionado.");
    }

    public void setRepository(FarmaciaRepository repository) {
        this.repository = repository;
    }

    public void setMedicamentoEditar(Medicamento medicamento) {

        this.medicamentoEditar = medicamento;

        txtCodigo.setText(medicamento.getCodigo());
        txtNombre.setText(medicamento.getNombre());
        txtDescripcion.setText(medicamento.getDescripcion());
        txtUnidadMedida.setText(medicamento.getUnidadMedida());
    }

}