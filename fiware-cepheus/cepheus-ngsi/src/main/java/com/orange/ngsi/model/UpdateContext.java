/*
 * Copyright (C) 2015 Orange
 *
 * This software is distributed under the terms and conditions of the 'GNU GENERAL PUBLIC LICENSE
 * Version 2' license which can be found in the file 'LICENSE.txt' in this package distribution or
 * at 'http://www.gnu.org/licenses/gpl-2.0-standalone.html'.
 */

package com.orange.ngsi.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pborscia on 05/06/2015.
 */
@JacksonXmlRootElement(localName = "updateContextRequest")
public class UpdateContext {

	private static Logger logger = LoggerFactory.getLogger(UpdateContext.class);

    @JacksonXmlElementWrapper(localName="contextElementList")
    @JacksonXmlProperty(localName="contextElement")
    List<ContextElement> contextElements;

    UpdateAction updateAction;

    public UpdateContext() {
    }

    public UpdateContext(UpdateAction updateAction) {
        this.updateAction = updateAction;
    }

    public List<ContextElement> getContextElements() {
        return contextElements;
    }

    public void setContextElements(List<ContextElement> contextElements) {
        this.contextElements = contextElements;
    }

    public UpdateAction getUpdateAction() {
        return updateAction;
    }

    public void setUpdateAction(UpdateAction updateAction) {
        this.updateAction = updateAction;
    }

    @Override
    public String toString() {
        return "UpdateContext{" +
                "contextElements=" + contextElements +
                ", updateAction=" + updateAction +
                '}';
    }
   
}
