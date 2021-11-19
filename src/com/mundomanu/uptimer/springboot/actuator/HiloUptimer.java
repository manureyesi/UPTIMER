/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundomanu.uptimer.springboot.actuator;

import com.mundomanu.uptimer.config.vo.ConfigServiceUptimerVO;
import static com.mundomanu.uptimer.springboot.actuator.IniciarUpTimerHilos.pararUptimer;
import java.awt.TrayIcon;
import java.util.logging.Logger;

/**
 *
 * @author manuel.reyes
 */
public class HiloUptimer implements Runnable {

    private static Logger log = Logger.getLogger(HiloUptimer.class.getName());
    
    public ConfigServiceUptimerVO config;
    private TrayIcon trayIcon;
    
    public HiloUptimer(final ConfigServiceUptimerVO config, final TrayIcon trayIcon) {
        this.config = config;
        this.trayIcon = trayIcon;
    }
    
    @Override
    public void run() {
        
        boolean continua = Boolean.TRUE;
        while (continua) {
            try {
                if (pararUptimer) {
                    log.info("Se para el hilo de ".concat(config.getNombre()));
                    Thread.currentThread().interrupt();
                    return;
                }
                
                log.info("Se lanza comprobación para ".concat(config.getNombre()));
                UptimerJavaServer.comprobarEstadoApp(config, trayIcon);
                IniciarUpTimerHilos.guardarConfig();
                Thread.sleep(config.getTiempoConsultaSegundos());
            } catch (InterruptedException ex) {
                log.severe("No se puede lanzar el hilo para ".concat(config.getNombre()).concat(" - ").concat(ex.getMessage()));
            }
        }
    }
    
}
