package es.fplumara.dam1.campeonato.service;

import es.fplumara.dam1.campeonato.exception.DuplicadoException;
import es.fplumara.dam1.campeonato.exception.NoEncontradoException;
import es.fplumara.dam1.campeonato.exception.OperacionNoPermitidaException;
import es.fplumara.dam1.campeonato.io.RegistroRankingCsv;
import es.fplumara.dam1.campeonato.model.Deportista;
import es.fplumara.dam1.campeonato.model.Resultado;
import es.fplumara.dam1.campeonato.repository.DeportistaRepository;
import es.fplumara.dam1.campeonato.repository.ResultadoRepository;

import java.util.*;

public class CampeonatoServiceImpl  implements CampeonatoService {
    private DeportistaRepository deportistaRepository;
    private ResultadoRepository resultadoRepository;

    public CampeonatoServiceImpl(DeportistaRepository deportistaRepository, ResultadoRepository resultadoRepository) {
        this.deportistaRepository = deportistaRepository;
        this.resultadoRepository = resultadoRepository;
    }

    public CampeonatoServiceImpl() {
    }

    public DeportistaRepository getDeportistaRepository() {
        return deportistaRepository;
    }

    public void setDeportistaRepository(DeportistaRepository deportistaRepository) {
        this.deportistaRepository = deportistaRepository;
    }

    public ResultadoRepository getResultadoRepository() {
        return resultadoRepository;
    }

    public void setResultadoRepository(ResultadoRepository resultadoRepository) {
        this.resultadoRepository = resultadoRepository;
    }

    @Override
    public void registrarDeportista(Deportista d) {
if(d==null || d.getId()==null || d.getPais()==null || d.getNombre()==null){
    throw new IllegalArgumentException("no existen");
}
Optional<Deportista> opts=deportistaRepository.findById(d.getId());
if(opts.isPresent()){
    throw new DuplicadoException("ya existe");
}

deportistaRepository.save(d);
    }

    @Override
    public void registrarResultado(Resultado r) {
if(r==null || r.getId()==null || r.getIdPrueba()==null || r.getIdDerportista()==null || r.getTipoPrueba()==null || r.getPosicion()<=0){
    throw new IllegalArgumentException("no existen");
}
Optional<Resultado> opts=resultadoRepository.findById(r.getId());
if(opts.isPresent()){
    throw new DuplicadoException("ya existe");
}
Optional<Deportista> opts1=deportistaRepository.findById(r.getIdDerportista());
if(opts1.isEmpty()){
    throw new NoEncontradoException("no encontrado");
}
if(resultadoRepository.existsByPruebaYDeportista(r.getIdPrueba(), r.getIdDerportista())){
    throw new OperacionNoPermitidaException("operacion no permitida");
}
resultadoRepository.save(r);
    }

    @Override
    public List<RegistroRankingCsv> ranking() {
        return List.of();
    }

    @Override
    public List<Resultado> resultadosDePais(String pais) {
        List<Resultado>resultados=new ArrayList<>();
        List<Deportista>dep=deportistaRepository.findByPais(pais);
        Set<String>idsDeportista=new HashSet<>();
        for(Deportista d:dep){
            idsDeportista.add(d.getId());
        }
List<Resultado>todos=resultadoRepository.listAll();
        for(Resultado r:todos){
            if(idsDeportista.contains(r.getIdDerportista())){
                resultados.add(r);
            }
        }
        return resultados;
    }

    @Override
    public Set<String> paisesParticipantes() {
        Set<String>paises=new HashSet<>();
        List<Deportista>deportistas=deportistaRepository.listAll();
        for(Deportista d:deportistas){
            paises.add(String.valueOf(d.getPais()));
        }
        return paises;
    }
}
