package aed;

import java.util.ArrayList;

public class BestEffort {
    private Promedio promedio;
    private ArrayList<Ciudad> ciudades;
    private Heap<Traslado> heapTimestamp;
    private Heap<Traslado> heapGanancia;
    public Heap<Ciudad> heapSuperavit;
    private ArrayList<Traslado> despachados;
    private Integer maxGanancia;
    private Integer maxPerdida;
    private ArrayList<Integer> arregloGanancias;
    private ArrayList<Integer> arregloPerdidas;

    public class Promedio {
        private int totalGanancia;
        private int despachados;
        private int promedio;

        public Promedio() {
            totalGanancia = 0;
            despachados = 0;
            promedio = 0;
        }
    }

    public BestEffort(int cantCiudades, Traslado[] traslados) {     //--> O(|C| + |T|)
        promedio = new Promedio();
        ciudades = new ArrayList<>();
        for (int i = 0; i < cantCiudades; i++) {                    //O(|C|)
            ciudades.add(new Ciudad(i));
        }
        heapGanancia = new Heap<>(false);
        heapTimestamp = new Heap<>(true);
        heapSuperavit = new Heap<>(false);
        ArrayList<Traslado> trasList = new ArrayList<Traslado>();
        for(int i = 0; i<traslados.length; i++) {                   //O(|T|)
            trasList.add(traslados[i]);
        }
        heapTimestamp.arrayList2heap(trasList);                     //O(|T|)
        heapGanancia.arrayList2heap(trasList);                      //O(|T|)
        heapSuperavit.arrayList2heap(ciudades);                     //O(|C|)
        despachados = new ArrayList<>();
        arregloGanancias = new ArrayList<>();
        arregloPerdidas = new ArrayList<>();
        maxGanancia = 0;
        maxPerdida = 0;
    }

    public void registrarTraslados(Traslado[] traslados) {         //-->O(|traslados| * log(T))
        for (Traslado traslado : traslados) {       //O(|traslados|)
            heapTimestamp.encolar(traslado);        //O(log(T))
            heapGanancia.encolar(traslado);         //O(log(T))
        }                                                  
    }

    private void actualizarPromedio(Traslado t) {                   //-->O(1)
        promedio.totalGanancia += t.gananciaNeta;
        promedio.despachados++;
        promedio.promedio = promedio.totalGanancia / promedio.despachados;
    }

    private void actualizarEstadisticas(Ciudad ciudad, boolean esGanancia) { //--> O(1)
        int valor;
        ArrayList<Integer> lista;
        Integer max;
        if (esGanancia) {
            valor = ciudad.ganancia;
            lista = arregloGanancias;
            max = maxGanancia;
        } else {
            valor = ciudad.perdida;
            lista = arregloPerdidas;
            max = maxPerdida;
        }
        if (valor > max) {
            lista.clear();
            lista.add(ciudad.id);
            if (esGanancia) {
                maxGanancia = valor;
            } else {
                maxPerdida = valor;
            }
        } else if (valor == max) {
            boolean yaEsta = false;
            for (int id : lista) {
                if (id == ciudad.id) {
                    yaEsta = true;
                    break;
                }
            }
            if (!yaEsta) {
                lista.add(ciudad.id);
            }
        }
    }
    public ArrayList<Integer> despacharMasRedituables(int n) {                            //--> O(n (log|T| + log|C|))
        ArrayList<Integer> idsDespachados = new ArrayList<>();
        int cantDisponibles = Math.min(n, heapGanancia.longitud);
        for (int i = 0; i < cantDisponibles; i++) {                             //O(n)
            Traslado traslado = heapGanancia.desencolar();                      //O(log|T|)
            heapTimestamp.sacarElem(heapTimestamp.inds.get(traslado.getId()));  //O(log|T|)
            idsDespachados.add(traslado.id);
            despachados.add(traslado);
            actualizarPromedio(traslado);
            int monto = traslado.gananciaNeta;
            Ciudad origen = heapSuperavit.elems.get(heapSuperavit.inds.get(traslado.origen));
            Ciudad destino = heapSuperavit.elems.get(heapSuperavit.inds.get(traslado.destino));
            origen.ganancia += monto;
            origen.superavit += monto;
            destino.perdida += monto;
            destino.superavit -= monto;
            heapSuperavit.actualizarPrioridad(origen, origen);                  //O(log|C|)
            heapSuperavit.actualizarPrioridad(destino, destino);                //O(log|C|)
            actualizarEstadisticas(origen, true);
            actualizarEstadisticas(destino, false);
            heapSuperavit.heapifyDown(0, 0);                               //O(log|C|)
        }
        return idsDespachados;
    }

    public ArrayList<Integer> despacharMasAntiguos(int n) {                             //--> O(n (log|T| + log|C|))
        ArrayList<Integer> idsDespachados = new ArrayList<>();
        int cantDisponibles = Math.min(n, heapTimestamp.longitud);
        for (int i = 0; i < cantDisponibles; i++) {                             //O(n)
            Traslado traslado = heapTimestamp.desencolar();                     //O(log|T|)
            heapGanancia.sacarElem(heapGanancia.inds.get(traslado.getId()));    //O(log|T|)
            idsDespachados.add(traslado.id);
            despachados.add(traslado);
            actualizarPromedio(traslado);
            int monto = traslado.gananciaNeta;
            Ciudad origen = heapSuperavit.elems.get(heapSuperavit.inds.get(traslado.origen));
            Ciudad destino = heapSuperavit.elems.get(heapSuperavit.inds.get(traslado.destino));
            origen.ganancia += monto;
            origen.superavit += monto;
            destino.perdida += monto;
            destino.superavit -= monto;
            heapSuperavit.actualizarPrioridad(origen, origen);                  //O(log|C|)
            heapSuperavit.actualizarPrioridad(destino, destino);                //O(log|C|)
            actualizarEstadisticas(origen, true);
            actualizarEstadisticas(destino, false);
            heapSuperavit.heapifyDown(0, 0);
        }

        return idsDespachados;
    }

    public int ciudadConMayorSuperavit() {                                  //-->O(1)
        return heapSuperavit.elems.get(0).getId();
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() {                  //-->O(1)
        return arregloGanancias;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() {                   //-->O(1)
        return arregloPerdidas;
    }

    public int gananciaPromedioPorTraslado() {                              //-->O(1)
        return promedio.promedio;
    }
}