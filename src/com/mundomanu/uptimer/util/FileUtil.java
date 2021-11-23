/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundomanu.uptimer.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mundomanu.uptimer.config.vo.ConfigServiceUptimerVO;
import com.mundomanu.uptimer.config.vo.ListConfigServiceUptimerVO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FileUtil {
    
    private static Logger log = Logger.getLogger(FileUtil.class.getName());
    
    private String nombreFile = "C:\\DEV\\UPTIMER\\uptimer.json";
    
    private final boolean existeArchivo () {
        File file = new File(this.nombreFile);
        return file.isFile();
    }
    
    private final void comprobarArchivo() throws IOException {
        File file = new File(this.nombreFile);
        if(!existeArchivo()) {
            file.createNewFile();
        }
    }
    
    public final void guardarArchivoNew (final List<ConfigServiceUptimerVO> listConfig) {
        
        try {

            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // convert map to JSON file
            mapper.writeValue(Paths.get(this.nombreFile).toFile(), new ListConfigServiceUptimerVO(listConfig));
    
        } catch ( Exception e) {
            log.severe("Error al cargar el archivo de config - ".concat(e.getMessage()));
        }
        
    
    }
    
    public final List<ConfigServiceUptimerVO> recargarArchivoNew () {
    
        ObjectMapper mapper = new ObjectMapper();
        
        List<ConfigServiceUptimerVO> auxList = new ArrayList<>();
        ListConfigServiceUptimerVO list = null;
        
        try {

            // convert JSON string to Book object
            list = mapper.readValue(Paths.get(this.nombreFile).toFile(), ListConfigServiceUptimerVO.class);

        } catch ( Exception e) {
            log.severe("Error al cargar el archivo de config - ".concat(e.getMessage()));
        }
        
        if (list != null && list.getListConfig() != null) {
            auxList = list.getListConfig();
        }
        
        return  auxList;
    }
    
    public final List<ConfigServiceUptimerVO> recargarArchivo () {
        List<ConfigServiceUptimerVO> list = null;
        if (existeArchivo()) {
            try {
                ObjectInputStream leyendoFichero = new ObjectInputStream( 
                new FileInputStream(this.nombreFile));
                list = (List<ConfigServiceUptimerVO>)leyendoFichero.readObject();
                leyendoFichero.close();
            } catch (Exception ex) {
                log.severe("Error al cargar el archivo de config - ".concat(ex.getMessage()));
            }
        }
        return list;
    }
    
}
