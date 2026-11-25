package cl.duoc.farmacia.dao;
import com.google.gson.Gson;
import cl.duoc.farmacia.model.Lote;
import cl.duoc.farmacia.model.Medicamento;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.io.IOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FarmaciaDAOImpl implements FarmaciaDAO {

    private static final String ARCHIVO_MEDICAMENTOS = "data/medicamentos.json";
    private static final String ARCHIVO_LOTES = "data/lotes.json";

    private final Gson gson;

    @Override
    public List<Medicamento> cargarMedicamentos() {

        Path ruta = Path.of(ARCHIVO_MEDICAMENTOS);

        if (!Files.exists(ruta)) {
            return new ArrayList<>();
        }

        try {
            String json = Files.readString(ruta);

            Type tipoLista = new TypeToken<List<Medicamento>>() {}.getType();

            List<Medicamento> medicamentos = gson.fromJson(json, tipoLista);

            if (medicamentos == null) {
                return new ArrayList<>();
            }

            return medicamentos;

        } catch (IOException e) {
            throw new RuntimeException("Error al cargar los medicamentos", e);
        }
    }

    @Override
    public void guardarMedicamentos(List<Medicamento> medicamentos) {

        Path ruta = Path.of(ARCHIVO_MEDICAMENTOS);

        try {
            Files.createDirectories(ruta.getParent());

            String json = gson.toJson(medicamentos);

            Files.writeString(ruta, json);

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar los medicamentos", e);
        }
    }

    @Override
    public List<Lote> cargarLotes() {

        Path ruta = Path.of(ARCHIVO_LOTES);

        if (!Files.exists(ruta)) {
            return new ArrayList<>();
        }

        try {
            String json = Files.readString(ruta);

            Type tipoLista = new TypeToken<List<Lote>>() {}.getType();

            List<Lote> lotes = gson.fromJson(json, tipoLista);

            if (lotes == null) {
                return new ArrayList<>();
            }

            return lotes;

        } catch (IOException e) {
            throw new RuntimeException("Error al cargar los lotes", e);
        }
    }

    @Override
    public void guardarLotes(List<Lote> lotes) {

        Path ruta = Path.of(ARCHIVO_LOTES);

        try {
            Files.createDirectories(ruta.getParent());

            String json = gson.toJson(lotes);

            Files.writeString(ruta, json);

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar los lotes", e);
        }
    }

    public FarmaciaDAOImpl() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() {

                    @Override
                    public void write(JsonWriter out, LocalDate fecha) throws IOException {
                        out.value(fecha.toString());
                    }

                    @Override
                    public LocalDate read(JsonReader in) throws IOException {
                        return LocalDate.parse(in.nextString());
                    }

                }.nullSafe())
                .setPrettyPrinting()
                .create();
    }
}