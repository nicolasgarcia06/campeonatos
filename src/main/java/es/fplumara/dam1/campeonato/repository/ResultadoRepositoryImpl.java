package es.fplumara.dam1.campeonato.repository;

import es.fplumara.dam1.campeonato.model.Deportista;
import es.fplumara.dam1.campeonato.model.Resultado;

import java.util.*;

public class ResultadoRepositoryImpl implements ResultadoRepository {
    private Map<String, Resultado> datos;

    public ResultadoRepositoryImpl() {
        this.datos = new HashMap<>();
    }

    @Override
    public void save(Resultado r) {
        datos.put(r.getId(), r);
    }

    @Override
    public Optional<Resultado> findById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(datos.get(id));
    }

    @Override
    public List<Resultado> listAll() {
        List<Resultado> resultados = new ArrayList<>();
        for (Resultado resultado : datos.values()) {
            resultados.add(resultado);
        }
        return resultados;
    }

    @Override
    public boolean existsByPruebaYDeportista(String idPrueba, String idDeportista) {
        for (Resultado r : datos.values()) {
            if (r.getIdPrueba().equals(idPrueba) && r.getIdDerportista().equals(idDeportista)) {
                return true;
            }
        }
            return false;

    }
}
