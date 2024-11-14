package aed;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import aed.Heap.Nodo;

public class BestEffort {
    private Promedio promedio;
    private ArrayList<Ciudad> ciudades;
    private Heap heapPorTimestamp;
    private Heap heapPorGanancia;
    private Heap heapSuperavit;

    public class Promedio {
        private int totalGanancia;
        private int despachados;
        private int promedio;

        Promedio (){
            totalGanancia = 0;
            despachados = 0;
            promedio = 0;
        }

    }

    public BestEffort(int cantCiudades, Traslado[] traslados){
        this.ciudades = new ArrayList<Ciudad>();
        for (int i = 0; i < cantCiudades; i++) {
            ciudades.add(new Ciudad(i));
        }
        heapPorGanancia = new Heap(false);
        heapPorTimestamp = new Heap(true);
        heapSuperavit = new Heap(false);
        heapPorTimestamp.heapify(traslados);
        heapPorGanancia.heapify(traslados);
    }

    public void registrarTraslados(Traslado[] traslados){
        for (Traslado traslado : traslados) {
            heapPorTimestamp.encolar(traslado);
            heapPorGanancia.encolar(traslado);
        }
    }

    private void actualizarPromedio(Traslado t) {
        promedio.totalGanancia = promedio.totalGanancia + t.gananciaNeta;
        promedio.despachados ++;
        promedio.promedio = promedio.totalGanancia / promedio.despachados;
    }

    public int[] despacharMasRedituables(int n){
        ArrayList<Integer> idsDespachados = new ArrayList<>();
        int numTrasladosDisponibles = Math.min(n, heapPorGanancia.elems.size());
        for (int i = 0; i < numTrasladosDisponibles; i++) {
            Traslado traslado = heapPorGanancia.desencolar();
            idsDespachados.add(traslado.id);
            actualizarPromedio(traslado);
        }
        int[] idsSeq = new int[idsDespachados.size()];
        for (int i = 0; i < idsDespachados.size(); i++) {
            idsSeq[i] = idsDespachados.get(i);
        }
        return idsSeq;
    }

    public int[] despacharMasAntiguos(int n){
        ArrayList<Integer> idsDespachados = new ArrayList<>();
        int numTrasladosDisponibles = Math.min(n, heapPorTimestamp.elems.size());
        for (int i = 0; i < numTrasladosDisponibles; i++) {
            Traslado traslado = heapPorTimestamp.desencolar();
            idsDespachados.add(traslado.id);
            actualizarPromedio(traslado);
        }
        int[] idsSeq = new int[idsDespachados.size()];
        for (int i = 0; i < idsDespachados.size(); i++) {
            idsSeq[i] = idsDespachados.get(i);
        }
        return idsSeq;
    }

    public int ciudadConMayorSuperavit(){
        // Implementar
        return 0;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        // Implementar
        return null;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        // Implementar
        return null;
    }

    public int gananciaPromedioPorTraslado(){
        return promedio.promedio;
    }
    
}
