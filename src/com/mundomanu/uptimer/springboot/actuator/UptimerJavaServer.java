/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundomanu.uptimer.springboot.actuator;

import com.mundo.manu.uptimer.pantallas.UptimerView;
import com.mundo.manu.uptimer.pantallas.UtilPantallas;
import com.mundomanu.uptimer.config.vo.ConfigServiceUptimerVO;
import static com.mundomanu.uptimer.springboot.actuator.IniciarUpTimerHilos.pararUptimer;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * UptimerJavaServer
 */
public class UptimerJavaServer {
    
    private static Logger log = Logger.getLogger(UptimerJavaServer.class.getName());
    
    public static void comprobarEstadoApp (final ConfigServiceUptimerVO config, final TrayIcon trayIcon) {
    
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = null;
        try {
            response = rt.exchange(new URI(config.getUrl()), config.getMethod(), null, String.class);
        } catch (ResourceAccessException e) {
            log.warning("Se encuentra fuera de la red corporativa, no se puede alcanzar el server ".concat(config.getNombre()));
        } catch (Exception ex) {
            log.severe("Error al comprobar estado de server ".concat(config.getNombre()).concat(" - ").concat(ex.getMessage()));
            lanzarErrorServer(config, ex.getMessage(), trayIcon);
        }
    
        if (response != null) {
            switch (response.getStatusCode()) {

                case OK:
                    comprobarCambioJson(config, response.getBody(), trayIcon);
                    break;

                default:
                    lanzarErrorServer(config, response.getStatusCode().name(), trayIcon);
            }
        }
    }
    
    private static void lanzarErrorServer (final ConfigServiceUptimerVO config, final String error, final TrayIcon trayIcon) {
        
        if (error != null & !error.equals(config.getUltimoRegistroStatus())) {
            config.setUltimoRegistroStatus(error);
            config.setUltimoRegistro(null);
            config.setUltimaActualizacion(new Date());
            
            StringBuilder st = new StringBuilder("Error en el servicio ");
            st.append(config.getUrl());
            st.append(" con error ");
            st.append(config.getProtocolo());
            st.append(" ");
            st.append(error);
            notificacionEscritorio(MessageType.ERROR, st.toString(), trayIcon);
            log.warning(st.toString());
        }
    }
    
    private static void comprobarCambioJson (final ConfigServiceUptimerVO config, final String json, final TrayIcon trayIcon) {
        
        if (json != null && config.getComprobarCambio()) {
                
            String[] listaCampos = config.getCampoBusqueda().split("\\.");
            
            // Buscar objeto
            String campo = null;
            JSONObject jsonObject = new JSONObject(json);
            for (int i = 0; i < listaCampos.length; i++) {
                if (i == (listaCampos.length-1)) {
                    campo = jsonObject.getString(listaCampos[i]);
                } else {
                    jsonObject = jsonObject.getJSONObject(listaCampos[i]);
                }
            }
            
            if (campo != null && !campo.equals(config.getUltimoRegistro())) {
                config.setUltimoRegistro(campo);
                config.setUltimoRegistroStatus(null);
                config.setUltimaActualizacion(new Date());

                StringBuilder st = new StringBuilder();
                st.append("Se acaba de detectar un cambio en ");
                st.append(config.getNombre());
                st.append(" con el cambio en el campo ");
                st.append(config.getCampoBusqueda());
                st.append(" valor: ");
                st.append(config.getUltimoRegistro());
                
                notificacionEscritorio(MessageType.INFO, st.toString(), trayIcon);
            }
            
        }
    }
    
    public static TrayIcon crearTraiIcon () {
        TrayIcon trayIcon = null;
        try {
        
            SystemTray tray = SystemTray.getSystemTray();

            Image image = Toolkit.getDefaultToolkit().createImage("robot.png");
            trayIcon = new TrayIcon(image, "Uptime Robot");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("Uptime Robot");
            
            final JPopupMenu menu = new JPopupMenu();
            
            JMenuItem pausar = new JMenuItem("Pausar");
            pausar.addMouseListener( new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    pararUptimer = Boolean.TRUE;
                    menu.setVisible(false);
                }
            });
            
            JMenuItem iniciar = new JMenuItem("Iniciar");
            iniciar.addMouseListener( new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    pararUptimer = Boolean.FALSE;
                    menu.setVisible(false);
                    IniciarUpTimerHilos.foorIniciarContenedores(IniciarUpTimerHilos.trayIcon);
                }
            });
            
            JMenuItem cerrarMenu = new JMenuItem("Cerrar Menu");
            cerrarMenu.addMouseListener( new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    menu.setVisible(false);
                }
            });
            
            JMenuItem cerrarApplicacion = new JMenuItem("Cerrar Aplicación");
            cerrarApplicacion.addMouseListener( new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    System.exit(0);
                }
            });
            
            JMenuItem verEstadoApplicacion = new JMenuItem("Ver Uptimer");
            verEstadoApplicacion.addMouseListener( new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    UptimerView u = new UptimerView();
                    u.setVisible(true);
                    menu.setVisible(false);
                }
            });
            
            menu.add(pausar);
            menu.add(iniciar);
            menu.add(cerrarMenu);
            menu.add(verEstadoApplicacion);
            menu.add(cerrarApplicacion);
            
            trayIcon.addMouseListener( new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    menu.setLocation(evt.getPoint());
                    menu.setVisible(true);
                }
            });
                    
            tray.add(trayIcon);
            
        } catch (Exception ex) {
            Logger.getLogger(UptimerJavaServer.class.getName()).log(Level.SEVERE, null, ex);
        }   
        
        return trayIcon;
    }
    
    private static void notificacionEscritorio(final MessageType tipoNotificacion, final String descripcion, final TrayIcon trayIcon) {
    
        try {
        
            trayIcon.displayMessage("Uptime Robot", descripcion, tipoNotificacion);
        
        } catch (Exception ex) {
            Logger.getLogger(UptimerJavaServer.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
}
