package aed;

import java.util.ArrayList;


public class BestEffort {
    private Promedio promedio;
    private ArrayList<Ciudad> ciudades;
    private Heap<Traslado> heapTimestamp;
    private Heap<Traslado> heapGanancia;
    private Heap<Ciudad> heapSuperavit;
    private ArrayList<Traslado> despachados;
    private Integer maxGanancia;
    private Integer maxPerdida;
    private ArrayList<Integer> arregloGanancias;
    private ArrayList<Integer> arregloPerdidas;

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
        promedio = new Promedio();
        ciudades = new ArrayList<Ciudad>();
        for (int i = 0; i < cantCiudades; i++) {
            ciudades.add(new Ciudad(i));
        }
        heapGanancia = new Heap<Traslado>(false);
        heapTimestamp = new Heap<Traslado>(true);
        heapSuperavit = new Heap<Ciudad>(false);
        heapTimestamp.array2heap(traslados);
        heapGanancia.array2heap(traslados);
        heapSuperavit.arrayList2heap(ciudades);
        despachados = new ArrayList<Traslado>();
        arregloGanancias = new ArrayList<Integer>();
        arregloPerdidas = new ArrayList<Integer>();
        maxGanancia = 0;
        maxPerdida = 0;
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
            heapTimestamp.sacarElem(heapTimestamp.inds.get(traslado.getId()));
            idsDespachados.add(traslado.id);
            despachados.add(traslado);
            actualizarPromedio(traslado);
            // Actualizar ciudades
            int monto = traslado.gananciaNeta;
            Ciudad origen = heapSuperavit.elems.get(heapSuperavit.inds.get(traslado.origen));
            Ciudad destino = heapSuperavit.elems.get(heapSuperavit.inds.get(traslado.destino));
            origen.ganancia += monto;
            origen.superavit += monto;
            destino.perdida += monto;
            destino.superavit -= monto;
            heapSuperavit.actualizarPrioridad(heapSuperavit.elems.get(heapSuperavit.inds.get(traslado.origen)), origen);
            heapSuperavit.actualizarPrioridad(heapSuperavit.elems.get(heapSuperavit.inds.get(traslado.destino)), destino);
            // Actualizar estadisticas
            if (origen.ganancia > maxGanancia) {
                maxGanancia = origen.ganancia;
                arregloGanancias.clear();
                arregloGanancias.add(origen.id);
            } else {
                if (origen.ganancia == maxGanancia) {
                    arregloGanancias.add(origen.id);
                }
            }
            if (origen.perdida > maxPerdida) {
                maxPerdida = origen.perdida;
                arregloPerdidas.clear();
                arregloPerdidas.add(origen.id);
            } else {
                if (origen.perdida == maxPerdida) {
                    arregloPerdidas.add(origen.id);
                }
            }
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
            heapGanancia.arrayList2heap(heapGanancia.elems);
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
        return heapSuperavit.elems.get(0).getId();
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return arregloGanancias;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return arregloPerdidas;
    }

    public int gananciaPromedioPorTraslado(){
        return promedio.promedio;
    }
    
}
