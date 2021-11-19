/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundomanu.uptimer.springboot.actuator;

import com.mundomanu.uptimer.config.vo.ConfigServiceUptimerVO;
import com.mundomanu.uptimer.util.FileUtil;
import java.awt.TrayIcon;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * IniciarUpTimerHilos
 */
public class IniciarUpTimerHilos {

    private static Logger log = Logger.getLogger(IniciarUpTimerHilos.class.getName());
    
    public static List<ConfigServiceUptimerVO> config = new ArrayList<>();
    
    public static Boolean pararUptimer = Boolean.FALSE;
    
    public static TrayIcon trayIcon = null;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        org.apache.log4j.BasicConfigurator.configure();
        
        TrayIcon trayIcon = UptimerJavaServer.crearTraiIcon();
        IniciarUpTimerHilos.trayIcon = trayIcon;
        foorIniciarContenedores(trayIcon);
        
    }
    
    public static void foorIniciarContenedores (final TrayIcon trayIcon) {
        
        // Carga el archivo de configuracion de log4J
        //PropertyConfigurator.configure("log4j.properties");
        iniciarContenedores();
        
        for (ConfigServiceUptimerVO config: config) {
            // TODO code application logic here
            crearHilos(config, trayIcon);
        }
        
    }
    
    private static void  iniciarContenedores () {
        
        // Datos Config
        List<ConfigServiceUptimerVO> list = config;
        
        FileUtil fil = new FileUtil();
        config = fil.recargarArchivoNew();
        list = config;
        
        if (list == null) {
            config = new ArrayList<>();
            log.warning("No se detecta el archivo por lo que no se ejecuta el programa");
        }
        
    }
    
    public static void guardarConfig () {
        FileUtil fil = new FileUtil();
        fil.guardarArchivoNew(config);
    }
    
    private static void crearHilos (final ConfigServiceUptimerVO config, final TrayIcon trayIcon) {
    
        HiloUptimer hiloUptimer = new HiloUptimer(config, trayIcon);
        Thread nuevoh=new Thread(hiloUptimer);
        nuevoh.start();
        
    }
    
}
