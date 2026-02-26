package es.fplumara.dam1.campeonato.service;

import es.fplumara.dam1.campeonato.model.Deportista;
import es.fplumara.dam1.campeonato.model.LineaRanking;
import es.fplumara.dam1.campeonato.model.Resultado;

import java.util.List;
import java.util.Set;

public interface CampeonatoService {
    void registrarDeportista(Deportista d);

void registrarResultado(Resultado r);

List<LineaRanking> ranking();

List<Resultado> resultadosDePais(String pais);

Set<String> paisesParticipantes();
}
