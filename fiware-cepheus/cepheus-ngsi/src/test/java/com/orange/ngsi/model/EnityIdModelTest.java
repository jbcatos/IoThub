/*
 * Copyright (C) 2015 Orange
 *
 * This software is distributed under the terms and conditions of the 'GNU GENERAL PUBLIC LICENSE
 * Version 2' license which can be found in the file 'LICENSE.txt' in this package distribution or
 * at 'http://www.gnu.org/licenses/gpl-2.0-standalone.html'.
 */

package com.orange.ngsi.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by pborscia on 16/09/2015.
 */
public class EnityIdModelTest {

    @Test
    public void deserializationXMLEntityId() throws IOException {

        String xml =
                "        <entityId type=\"T1\" isPattern=\"false\">\n" +
                "        <id>E1</id>\n" +
                "        </entityId>\n";

        ObjectMapper xmlmapper = new XmlMapper();
        EntityId entityId = xmlmapper.readValue(xml, EntityId.class);
        assertEquals("E1", entityId.getId());
    }

    @Test
    public void serializationXMLEntityId() throws IOException {

        EntityId entityId = new EntityId("E1", "T1", false);

        ObjectMapper xmlmapper = new XmlMapper();
        String xml = xmlmapper.writeValueAsString(entityId);

        assertTrue(xml.contains("E1"));
        assertTrue(xml.contains("T1"));
    }
}
