package es.fplumara.dam1.campeonato.repository;

import es.fplumara.dam1.campeonato.model.Deportista;

import java.util.*;

public class DeportistaRepositoryImpl implements DeportistaRepository {
    private final Map<String, Deportista> datos;

    public DeportistaRepositoryImpl() {
        this.datos = new HashMap<>();
    }


    @Override
    public void save(Deportista d) {
        datos.put(d.getId(), d);
    }

    @Override
    public Optional<Deportista> findById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(datos.get(id));
    }

    @Override
    public List<Deportista> listAll() {
        List<Deportista> resultado = new ArrayList<>();
        for (Deportista deporte : datos.values()) {
            resultado.add(deporte);
        }
        return resultado;
    }

    @Override
    public List<Deportista> findByPais(String pais) {
        List<Deportista> resultado = new ArrayList<>();
        for (Deportista d : datos.values()) {
            if (d.getPais().equals(pais)) {
                resultado.add(d);
            }

        }
        return resultado;
    }
}


