/*
 * Copyright (C) 2015 Orange
 *
 * This software is distributed under the terms and conditions of the 'GNU GENERAL PUBLIC LICENSE
 * Version 2' license which can be found in the file 'LICENSE.txt' in this package distribution or
 * at 'http://www.gnu.org/licenses/gpl-2.0-standalone.html'.
 */

package com.orange.ngsi.client;

import com.orange.ngsi.TestConfiguration;
import com.orange.ngsi.model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.inject.Inject;

import java.util.function.Consumer;

import static org.hamcrest.Matchers.hasToString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.mockito.Mockito.*;
import static com.orange.ngsi.Util.*;

/**
 * Test for the NGSI UnsubscribeContext request
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfiguration.class)
public class UnsubscribeContextRequestTest {

    private static String baseUrl = "http://localhost:8080";

    private final String subscriptionID = "SLJLSKDM%LSKDM%LKDS";

    private MockRestServiceServer mockServer;

    @Autowired
    private MappingJackson2HttpMessageConverter jsonConverter;

    @Autowired
    private MappingJackson2XmlHttpMessageConverter xmlConverter;

    @Autowired
    NgsiClient ngsiClient;

    @Inject
    private AsyncRestTemplate asyncRestTemplate;

    private Consumer<UnsubscribeContextResponse> onSuccess = Mockito.mock(Consumer.class);

    private Consumer<Throwable> onFailure = Mockito.mock(Consumer.class);

    @Before
    public void tearUp() {
        this.mockServer = MockRestServiceServer.createServer(asyncRestTemplate);
    }

    @After
    public void tearDown() {
        reset(onSuccess);
        reset(onFailure);
    }

    @Test(expected = HttpServerErrorException.class)
    public void unsubscribeContextRequestWith500() throws Exception {

        this.mockServer.expect(requestTo(baseUrl + "/ngsi10/unsubscribeContext")).andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        ngsiClient.unsubscribeContext(baseUrl, null, subscriptionID).get();
    }

    @Test(expected = HttpClientErrorException.class)
    public void unsubscribeContextRequestWith404() throws Exception {

        this.mockServer.expect(requestTo(baseUrl + "/ngsi10/unsubscribeContext")).andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        ngsiClient.unsubscribeContext(baseUrl, null, subscriptionID).get();
    }

    @Test
    public void unsubscribeContextRequestOK() throws Exception {

        ngsiClient.protocolRegistry.registerHost(baseUrl, true);
        String responseBody = json(jsonConverter, createUnsubscribeContextResponse(CodeEnum.CODE_200, subscriptionID));

        this.mockServer.expect(requestTo(baseUrl + "/ngsi10/unsubscribeContext"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header("Accept", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.subscriptionId", hasToString(subscriptionID)))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        UnsubscribeContextResponse response = ngsiClient.unsubscribeContext(baseUrl, null, subscriptionID).get();

        this.mockServer.verify();

        Assert.assertEquals(subscriptionID, response.getSubscriptionId());
        Assert.assertEquals(CodeEnum.CODE_200.getLabel(), response.getStatusCode().getCode());
    }

    @Test
    public void unsubscribeContextRequestOK_XML() throws Exception {

        ngsiClient.protocolRegistry.unregisterHost(baseUrl);

        String responseBody = xml(xmlConverter, createUnsubscribeContextResponse(CodeEnum.CODE_200, subscriptionID));

        this.mockServer.expect(requestTo(baseUrl + "/ngsi10/unsubscribeContext"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Content-Type", MediaType.APPLICATION_XML_VALUE))
                .andExpect(header("Accept", MediaType.APPLICATION_XML_VALUE))
                .andExpect(xpath("unsubscribeContextRequest/subscriptionId").string(subscriptionID))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_XML));

        UnsubscribeContextResponse response = ngsiClient.unsubscribeContext(baseUrl, null, subscriptionID).get();

        this.mockServer.verify();

        Assert.assertEquals(subscriptionID, response.getSubscriptionId());
        Assert.assertEquals(CodeEnum.CODE_200.getLabel(), response.getStatusCode().getCode());
    }

    private UnsubscribeContextResponse createUnsubscribeContextResponse(CodeEnum code, String subcriptionID) {
        return new UnsubscribeContextResponse(new StatusCode(code), subcriptionID);
    }
}
