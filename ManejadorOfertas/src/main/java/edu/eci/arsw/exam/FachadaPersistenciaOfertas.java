/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.exam;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author hcadavid
 */
public class FachadaPersistenciaOfertas {

    //mapa <codigo,producto>
    final private Map<String, Product> mapaProductosSolicitados = new LinkedHashMap<>();

    //mapa <codigo,codigo del cliente con la mejor oferta>
    final private Map<String, String> mapaOferentesAsignados = new LinkedHashMap<>();

    //mapa <codigo, monto de la mejor oferta>
    final private Map<String, Integer> mapaMontosAsignados = new LinkedHashMap<>();

    //mapa <codigo, numero de ofertas recibidas>
    final private Map<String, Integer> mapaOfertasRecibidas = new LinkedHashMap<>();

    // mapa <codigo, indicador de ganador notificado>
    final private Map<String, Boolean> mapaGanadorNotificado = new LinkedHashMap<>();

    public Map<String, Product> getMapaProductosSolicitados() {
        return mapaProductosSolicitados;
    }

    public Map<String, Integer> getMapaOfertasRecibidas() {
        return mapaOfertasRecibidas;
    }

    public Map<String, String> getMapaOferentesAsignados() {
        return mapaOferentesAsignados;
    }

    public Map<String, Integer> getMapaMontosAsignados() {
        return mapaMontosAsignados;
    }

    public Map<String, Boolean> getMapaGanadorNotificado() {
        return mapaGanadorNotificado;
    }

}
