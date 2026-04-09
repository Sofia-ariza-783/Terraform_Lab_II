/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.exam.remote;

import edu.eci.arsw.exam.FachadaPersistenciaOfertas;
import edu.eci.arsw.exam.remote.ManejadorOfertasStub;
/**
 *
 * @author hcadavid
 */
public class ManejadorOfertas implements  ManejadorOfertasStub{

    private FachadaPersistenciaOfertas fachadaPersistenciaOfertas;
    public void setFachadaPersistenciaOfertas(FachadaPersistenciaOfertas fpers) {
        this.fachadaPersistenciaOfertas = fpers;
    }
            
    @Override
    public void agregarOferta(String codOferente,String codprod,int monto) {

        if (!fachadaPersistenciaOfertas.getMapaOfertasRecibidas().containsKey(codprod)){
            fachadaPersistenciaOfertas.getMapaOfertasRecibidas().put(codprod, 1);
            fachadaPersistenciaOfertas.getMapaMontosAsignados().put(codprod, monto);
            fachadaPersistenciaOfertas.getMapaOferentesAsignados().put(codprod, codOferente);
            return;
        }

        int ofertasActuales = fachadaPersistenciaOfertas.getMapaOfertasRecibidas().get(codprod);
        if (ofertasActuales >= 3) {
            // The winner is locked after the first three offers.
            return;
        }

        fachadaPersistenciaOfertas.getMapaOfertasRecibidas().put(codprod, ofertasActuales + 1);
        if (fachadaPersistenciaOfertas.getMapaMontosAsignados().get(codprod) > monto){
            fachadaPersistenciaOfertas.getMapaMontosAsignados().put(codprod, monto);
            fachadaPersistenciaOfertas.getMapaOferentesAsignados().put(codprod, codOferente);
        }

    }
    
    
    
}
