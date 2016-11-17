/*
 * Copyright (C) 2015 Orange
 *
 * This software is distributed under the terms and conditions of the 'GNU GENERAL PUBLIC LICENSE
 * Version 2' license which can be found in the file 'LICENSE.txt' in this package distribution or
 * at 'http://www.gnu.org/licenses/gpl-2.0-standalone.html'.
 */

package com.orange.cepheus.broker.model;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.orange.ngsi.model.ContextAttribute;

/**
 * Context Entity
 */
public class ContextElementDTO {

//    private EntityId entityId;
	private String Device;

    @JsonProperty("attributes")
    @JacksonXmlElementWrapper(localName = "resources")
    @JacksonXmlProperty(localName = "contextAttribute")
    private List<ContextAttribute> resources;  
    
    public ContextElementDTO() {
    }


    public List<ContextAttribute> getResources() {
        if (resources == null) {
            return Collections.emptyList();
        }
        return resources;
    }

    public void setResources(List<ContextAttribute> contextAttributeList) {
        this.resources = contextAttributeList;
    }


	public String getDevice() {
		return Device;
	}


	public void setDevice(String device) {
		Device = device;
	}


	@Override
	public String toString() {
		return "ContextElementDTO [Device=" + Device + ", contextAttributeList=" + resources + "]";
	}

}
