/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundomanu.uptimer.config.vo;

import java.io.Serializable;
import java.util.List;

public class ListConfigServiceUptimerVO implements Serializable {
    
    private List<ConfigServiceUptimerVO> listConfig;

    public ListConfigServiceUptimerVO() {}
    
    public ListConfigServiceUptimerVO(List<ConfigServiceUptimerVO> listConfig) {
        this.listConfig = listConfig;
    }

    public List<ConfigServiceUptimerVO> getListConfig() {
        return listConfig;
    }

    public void setListConfig(List<ConfigServiceUptimerVO> listConfig) {
        this.listConfig = listConfig;
    }
    
}
