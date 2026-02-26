package es.fplumara.dam1.campeonato.app;

import es.fplumara.dam1.campeonato.io.*;
import es.fplumara.dam1.campeonato.model.Deportista;
import es.fplumara.dam1.campeonato.model.Resultado;
import es.fplumara.dam1.campeonato.model.TipoPrueba;
import es.fplumara.dam1.campeonato.repository.DeportistaRepository;
import es.fplumara.dam1.campeonato.repository.DeportistaRepositoryImpl;
import es.fplumara.dam1.campeonato.repository.ResultadoRepository;
import es.fplumara.dam1.campeonato.repository.ResultadoRepositoryImpl;
import es.fplumara.dam1.campeonato.service.CampeonatoService;
import es.fplumara.dam1.campeonato.service.CampeonatoServiceImpl;

import java.nio.file.Path;
import java.time.Clock;
import java.util.List;

/**
 * Main de ejemplo para demostrar el flujo mínimo del examen (sin menú complejo).
 * Debe leer ficheros de entrada y escribir un fichero de salida.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Examen DAM1 - Campeonato deportivo (Java 21)");
DeportistaRepository deportistaRepository=new DeportistaRepositoryImpl();
ResultadoRepository resultadoRepository=new ResultadoRepositoryImpl();
        CampeonatoService campeonatoService=new CampeonatoServiceImpl(deportistaRepository,resultadoRepository);
        DeportistaCsvIO deportistaCsvIO=new DeportistaCsvIO();
        List<RegistroDeportistaCsv>registroDeportistaCsvs= deportistaCsvIO.leer(Path.of("C:\\Users\\juanc\\IdeaProjects\\campeonatos\\data\\deportistas.csv"));
        for(RegistroDeportistaCsv d :registroDeportistaCsvs) {
            Deportista deportista = new Deportista();
            deportista.setId(d.id());
            deportista.setPais(d.pais());
            deportista.setNombre(d.nombre());

            campeonatoService.registrarDeportista(deportista);
        }
        ResultadoCsvIO resultadoCsvIO=new ResultadoCsvIO();
       List<RegistroResultadoCsv>registroResultadoCsvs= resultadoCsvIO.leer(Path.of("C:\\Users\\juanc\\IdeaProjects\\campeonatos\\data\\resultados.csv"));
       for(RegistroResultadoCsv r:registroResultadoCsvs){
           Resultado resultado=new Resultado();
           resultado.setId(r.id());
           resultado.setIdDerportista(r.idDeportista());
           resultado.setIdPrueba(r.idPrueba());
           resultado.setPosicion(r.posicion());
           if(r.tipoPrueba().equalsIgnoreCase("Carrera")){
               resultado.setTipoPrueba(TipoPrueba.CARRERA);
           }else if(r.tipoPrueba().equalsIgnoreCase("salto")){
               resultado.setTipoPrueba(TipoPrueba.SALTO);
           }
           campeonatoService.registrarResultado(resultado);

       }
       System.out.println(campeonatoService.paisesParticipantes());
       System.out.println(campeonatoService.ranking());
       System.out.println(campeonatoService.resultadosDePais("España"));
        RankingCsvWriter rankingCsvWriter=new RankingCsvWriter();
        rankingCsvWriter.escribir(Path.of("C:\\Users\\juanc\\IdeaProjects\\campeonatos\\data\\resultados.csv"),campeonatoService.ranking());
        /*
         * FLUJO MÍNIMO (lo que debe hacer tu main)
         *
         * 1) Crear repositorios en memoria
         *    - DeportistaRepositoryImpl
         *    - ResultadoRepositoryImpl
         *
         * 2) Crear el servicio
         *    - CampeonatoService (usa ambos repositorios)
         *
         * 3) Leer datos de ficheros (CSV recomendado)
         *    - Leer "deportistas.csv" y por cada línea crear Deportista y llamar a registrarDeportista(...)
         *    - Leer "resultados.csv" y por cada línea crear Resultado (incluyendo tipoPrueba como enum) y llamar a registrarResultado(...)
         *
         * 4) Mostrar por consola
         *    - Países participantes (Set)
         *    - Ranking (List ordenada por puntos)
         *    - Resultados de un país (List filtrada)
         *
         * 5) Escribir salida a fichero
         *    - Generar el ranking y escribir "ranking.csv" con: idDeportista,nombre,pais,puntos
         */
    }
}
