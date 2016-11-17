/*
 * Copyright (C) 2015 Orange
 *
 * This software is distributed under the terms and conditions of the 'GNU GENERAL PUBLIC LICENSE
 * Version 2' license which can be found in the file 'LICENSE.txt' in this package distribution or
 * at 'http://www.gnu.org/licenses/gpl-2.0-standalone.html'.
 */

package com.orange.ngsi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.net.URI;
import java.time.Instant;
import java.util.List;

/**
 * Created by pborscia on 17/07/2015.
 */
@JacksonXmlRootElement(localName = "subscribeContextRequest")
public class SubscribeContext {

    @JsonProperty("entities")
    @JacksonXmlElementWrapper(localName = "entityIdList")
    @JacksonXmlProperty(localName = "entityId")
    List<EntityId> entityIdList;

    @JsonProperty("attributes")
    @JacksonXmlElementWrapper(localName = "attributeList")
    @JacksonXmlProperty(localName = "attribute")
    List<String> attributeList;

    URI reference;

    String duration;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Restriction restriction;

    @JsonProperty("notifyConditions")
    @JacksonXmlElementWrapper(localName = "notifyConditions")
    @JacksonXmlProperty(localName = "notifyCondition")
    List<NotifyCondition> notifyConditionList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String throttling;

    public SubscribeContext() {
    }

    public List<EntityId> getEntityIdList() {
        return entityIdList;
    }

    public void setEntityIdList(List<EntityId> entityIdList) {
        this.entityIdList = entityIdList;
    }

    public List<String> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<String> attributeList) {
        this.attributeList = attributeList;
    }

    public URI getReference() {
        return reference;
    }

    public void setReference(URI reference) {
        this.reference = reference;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Restriction getRestriction() {
        return restriction;
    }

    public void setRestriction(Restriction restriction) {
        this.restriction = restriction;
    }

    public List<NotifyCondition> getNotifyConditionList() {
        return notifyConditionList;
    }

    public void setNotifyConditionList(List<NotifyCondition> notifyConditionList) {
        this.notifyConditionList = notifyConditionList;
    }

    public String getThrottling() {
        return throttling;
    }

    public void setThrottling(String throttling) {
        this.throttling = throttling;
    }

    @Override
    public String toString() {
        return "SubscribeContext{" +
                "entityIdList=" + entityIdList +
                ", attributeList=" + attributeList +
                '}';
    }
}
