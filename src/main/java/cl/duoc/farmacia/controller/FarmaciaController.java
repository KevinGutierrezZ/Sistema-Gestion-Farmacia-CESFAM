package cl.duoc.farmacia.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import cl.duoc.farmacia.model.Medicamento;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import cl.duoc.farmacia.dao.FarmaciaDAOImpl;
import cl.duoc.farmacia.repository.FarmaciaRepository;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import cl.duoc.farmacia.controller.RegistroMedicamentoController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import cl.duoc.farmacia.controller.RegistroLoteController;
import javafx.scene.control.TableCell;


public class FarmaciaController {
    private FarmaciaRepository repository;

        @FXML
        private TextField txtBuscar;

        @FXML
        private Button btnRegistrar;

        @FXML
        private Button btnEditar;

        @FXML
        private Button btnEliminar;

        @FXML
        private TableView<Medicamento> tablaMedicamentos;

        @FXML
        private TableColumn<Medicamento, String> colCodigo;

        @FXML
        private TableColumn<Medicamento, String> colNombre;

        @FXML
        private TableColumn<Medicamento, String> colUnidad;

        @FXML
        private TableColumn<Medicamento, Integer> colStock;

        @FXML
        private TableColumn<Medicamento, String> colEstado;

    @FXML
    public void initialize() {

        repository = new FarmaciaRepository(
                new FarmaciaDAOImpl()
        );

        tablaMedicamentos.setPlaceholder(
                new Label("No hay medicamentos registrados.")
        );

        colCodigo.setCellValueFactory(
                new PropertyValueFactory<>("codigo")
        );

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        colUnidad.setCellValueFactory(
                new PropertyValueFactory<>("unidadMedida")
        );

        colStock.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(
                        repository.obtenerStock(
                                cellData.getValue().getId()
                        )
                )
        );

        colEstado.setCellValueFactory(cellData -> {

            int stock = repository.obtenerStock(
                    cellData.getValue().getId()
            );

            String estado = stock > 0 ? "Disponible" : "Sin stock";

            return new ReadOnlyObjectWrapper<>(estado);
        });

        colEstado.setCellFactory(column -> new TableCell<>() {

            @Override
            protected void updateItem(String estado, boolean empty) {

                super.updateItem(estado, empty);

                if (empty || estado == null) {

                    setText(null);
                    setStyle("");

                } else {

                    setText(estado);

                    if (estado.equals("Disponible")) {
                        setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #c62828; -fx-font-weight: bold;");
                    }
                }
            }
        });

        cargarMedicamentos();
        txtBuscar.textProperty().addListener((observable, valorAnterior, nuevoValor) -> {

            tablaMedicamentos.setItems(
                    FXCollections.observableArrayList(
                            repository.buscarMedicamentos(nuevoValor)
                    )
            );

        });
    }

    private void cargarMedicamentos() {

        tablaMedicamentos.setItems(
                FXCollections.observableArrayList(
                        repository.obtenerMedicamentos()
                )
        );

        tablaMedicamentos.refresh();
    }

    @FXML
    private void abrirFormularioRegistro() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/cl/duoc/farmacia/view/RegistroMedicamentoView.fxml"
                    )
            );

            Parent root = loader.load();
            RegistroMedicamentoController controller = loader.getController();
            controller.setRepository(repository);

            Stage ventana = new Stage();
            ventana.setTitle("Registrar medicamento");
            ventana.setScene(new Scene(root));

            ventana.initModality(Modality.APPLICATION_MODAL);

            ventana.showAndWait();
            cargarMedicamentos();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @FXML
    private void abrirFormularioLote() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/cl/duoc/farmacia/view/RegistroLoteView.fxml"
                    )
            );

            Parent root = loader.load();

            RegistroLoteController controller = loader.getController();

            controller.setRepository(repository);

            controller.cargarMedicamentos();

            Stage ventana = new Stage();

            ventana.setTitle("Registrar ingreso de medicamentos");

            ventana.setScene(new Scene(root));

            ventana.initModality(Modality.APPLICATION_MODAL);

            ventana.showAndWait();

            cargarMedicamentos();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    @FXML
    private void abrirFormularioEdicion() {

        Medicamento seleccionado = tablaMedicamentos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Editar medicamento");
            alerta.setHeaderText(null);
            alerta.setContentText("Debe seleccionar un medicamento para editarlo.");
            alerta.showAndWait();

            return;
        }

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/cl/duoc/farmacia/view/RegistroMedicamentoView.fxml"
                    )
            );

            Parent root = loader.load();

            RegistroMedicamentoController controller = loader.getController();

            controller.setRepository(repository);
            controller.setMedicamentoEditar(seleccionado);

            Stage ventana = new Stage();
            ventana.setTitle("Editar medicamento");
            ventana.setScene(new Scene(root));

            ventana.initModality(Modality.APPLICATION_MODAL);

            ventana.showAndWait();

            cargarMedicamentos();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarMedicamento() {

        Medicamento seleccionado = tablaMedicamentos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Eliminar medicamento");
            alerta.setHeaderText(null);
            alerta.setContentText("Debe seleccionar un medicamento para eliminarlo.");
            alerta.showAndWait();

            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("Eliminar medicamento");
        confirmacion.setContentText(
                "¿Está seguro de eliminar " + seleccionado.getNombre() + "?"
        );

        confirmacion.showAndWait().ifPresent(respuesta -> {

            if (respuesta == ButtonType.OK) {

                repository.eliminarMedicamento(seleccionado.getId());

                cargarMedicamentos();

                Alert resultado = new Alert(Alert.AlertType.INFORMATION);
                resultado.setTitle("Eliminación exitosa");
                resultado.setHeaderText(null);
                resultado.setContentText("El medicamento fue eliminado correctamente.");
                resultado.showAndWait();
            }
        });
    }


    @FXML
    private void abrirConsultaLotes() {

        Medicamento seleccionado = tablaMedicamentos
                .getSelectionModel()
                .getSelectedItem();

        if (seleccionado == null) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Consultar lotes");
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "Debe seleccionar un medicamento para consultar sus lotes."
            );
            alerta.showAndWait();

            return;
        }

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/cl/duoc/farmacia/view/ConsultarLotesView.fxml"
                    )
            );

            Parent root = loader.load();

            ConsultarLotesController controller = loader.getController();

            controller.setRepository(repository);
            controller.setMedicamento(seleccionado);
            controller.cargarLotes();

            Stage ventana = new Stage();

            ventana.setTitle("Consultar lotes del medicamento");
            ventana.setScene(new Scene(root));

            ventana.initModality(Modality.APPLICATION_MODAL);

            ventana.showAndWait();

        } catch (IOException e) {

            e.printStackTrace();

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText(
                    "No fue posible abrir la consulta de lotes."
            );
            alerta.showAndWait();
        }
    }


}

