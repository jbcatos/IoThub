/*
 * Copyright (C) 2015 Orange
 *
 * This software is distributed under the terms and conditions of the 'GNU GENERAL PUBLIC LICENSE
 * Version 2' license which can be found in the file 'LICENSE.txt' in this package distribution or
 * at 'http://www.gnu.org/licenses/gpl-2.0-standalone.html'.
 */

package com.orange.ngsi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Context Entity
 */
public class ContextElement {
	
	private static Logger logger = LoggerFactory.getLogger(ContextElement.class);

    private EntityId entityId;

    @JsonProperty("attributes")
    @JacksonXmlElementWrapper(localName = "contextAttributeList")
    @JacksonXmlProperty(localName = "contextAttribute")
    private List<ContextAttribute> contextAttributeList;  
    
    public ContextElement() {
    }

    public EntityId getEntityId() {
        return entityId;
    }

    public void setEntityId(EntityId entityId) {
        this.entityId = entityId;
    }

    public List<ContextAttribute> getContextAttributeList() {
        if (contextAttributeList == null) {
            return Collections.emptyList();
        }
        return contextAttributeList;
    }

    public void setContextAttributeList(List<ContextAttribute> contextAttributeList) {
        this.contextAttributeList = contextAttributeList;
    }


	@Override
	public String toString() {
		return "ContextElement [entityId=" + entityId + ", contextAttributeList=" + contextAttributeList
				+ "]";
	}
	
}
