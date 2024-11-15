package aed;

import java.util.ArrayList;


public class BestEffort {
    private Promedio promedio;
    private ArrayList<Ciudad> ciudades;
    private Heap<Traslado> heapTimestamp;
    private Heap<Traslado> heapGanancia;
    private Heap<Ciudad> heapSuperavit;
    private ArrayList<Traslado> despachados;

    public class Promedio {
        private int totalGanancia;
        private int despachados;
        private int promedio;

        public Promedio(){
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
        heapGanancia = new Heap<Traslado>(false);
        heapTimestamp = new Heap<Traslado>(true);
        heapSuperavit = new Heap<Ciudad>(false);
        heapTimestamp.array2heap(traslados);
        heapGanancia.array2heap(traslados);
        despachados = new ArrayList<Traslado>();
    }

    public void registrarTraslados(Traslado[] traslados){
        for (Traslado traslado : traslados) {
            heapTimestamp.encolar(traslado);
            heapGanancia.encolar(traslado);
        }
    }

    private void actualizarPromedio(Traslado t) {
        promedio.totalGanancia += t.gananciaNeta;
        promedio.despachados ++;
        promedio.promedio = promedio.totalGanancia / promedio.despachados;
    }

    public int[] despacharMasRedituables(int n){
        ArrayList<Integer> idsDespachados = new ArrayList<>();
        int cantDisponibles = Math.min(n, heapGanancia.longitud);
        for (int i = 0; i < cantDisponibles; i++) {
            Traslado traslado = heapGanancia.desencolar();
            heapTimestamp.elems.remove(heapTimestamp.inds.get(traslado.getId()));
            idsDespachados.add(traslado.id);
            despachados.add(traslado);
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
        int cantDisponibles = Math.min(n, heapTimestamp.longitud);
        for (int i = 0; i < cantDisponibles; i++) {
            Traslado traslado = heapTimestamp.desencolar();
            heapGanancia.elems.remove(heapGanancia.inds.get(traslado.getId()));
            idsDespachados.add(traslado.id);
            despachados.add(traslado);
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
