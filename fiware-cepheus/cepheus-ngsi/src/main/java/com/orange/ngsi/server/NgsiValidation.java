package com.orange.ngsi.server;

import com.orange.ngsi.exception.MismatchIdException;
import com.orange.ngsi.exception.MissingRequestParameterException;
import com.orange.ngsi.exception.UnsupportedOperationException;
import com.orange.ngsi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.Optional;



/**
 * Created by pborscia on 07/08/2015.
 */
@Component
public class NgsiValidation {

    private static Logger logger = LoggerFactory.getLogger(NgsiValidation.class);

    public void checkUpdateContext(UpdateContext updateContext) throws MissingRequestParameterException {

        if (updateContext.getUpdateAction() == null) {
            throw new MissingRequestParameterException("updateAction", "string");
        }
        if (nullOrEmpty(updateContext.getContextElements())) {
            throw new MissingRequestParameterException("contextElements", "List<ContextElement>");
        }
        for (ContextElement contextElement : updateContext.getContextElements()) {
            checkContextElement(contextElement);
        }
    }
    
    public void checkUpdateContextCepheus(UpdateContextCepheus updateContextCepheus) throws MissingRequestParameterException {
		
    	if (updateContextCepheus.getUpdateContext().getUpdateAction() == null) {
            throw new MissingRequestParameterException("updateAction", "string");
        }
        if (nullOrEmpty(updateContextCepheus.getUpdateContext().getContextElements())) {
            throw new MissingRequestParameterException("contextElements", "List<ContextElement>");
        }
        for (ContextElement contextElement : updateContextCepheus.getUpdateContext().getContextElements()) {
            checkContextElement(contextElement);
        }
		
	}

    public void checkNotifyContext(NotifyContext notifyContext) throws MissingRequestParameterException {

        if (nullOrEmpty(notifyContext.getSubscriptionId())) {
            throw new MissingRequestParameterException("subscriptionId", "string");
        }
        if (nullOrEmpty(notifyContext.getOriginator())){
            throw new MissingRequestParameterException("originator", "URI");
        }
        if (nullOrEmpty(notifyContext.getContextElementResponseList()))  {
            throw new MissingRequestParameterException("contextElementResponse", "List<ContextElementResponse>");
        }
        for (ContextElementResponse contextElementResponse : notifyContext.getContextElementResponseList()) {
            checkContextElementResponse(contextElementResponse);
        }
    }

    public void checkRegisterContext(RegisterContext registerContext) throws MissingRequestParameterException {

        if (nullOrEmpty(registerContext.getContextRegistrationList()))  {
            throw new MissingRequestParameterException("contextRegistrations", "List<ContextRegistration>");
        }
        for (ContextRegistration contextRegistration : registerContext.getContextRegistrationList()) {
            checkContextRegistration(contextRegistration);
        }
    }

    public void checkSubscribeContext(SubscribeContext subscribeContext) throws MissingRequestParameterException {
        if (nullOrEmpty(subscribeContext.getEntityIdList())) {
            throw new MissingRequestParameterException("entities", "List<EntityId>");
        }
        for(EntityId entityId: subscribeContext.getEntityIdList()) {
            checkEntityId(entityId);
        }
        if (nullOrEmpty(subscribeContext.getReference())){
            throw new MissingRequestParameterException("reference", "URI");
        }
        if (subscribeContext.getRestriction() != null) {
            if (nullOrEmpty(subscribeContext.getRestriction().getAttributeExpression())
                    && nullOrEmpty(subscribeContext.getRestriction().getScopes())) {
                throw new MissingRequestParameterException("attributeExpression or scopes", "string");
            }
        }
    }

    public void checkUpdateContextSubscription(UpdateContextSubscription updateContextSubscription) throws MissingRequestParameterException {
        if (nullOrEmpty(updateContextSubscription.getSubscriptionId())){
            throw new MissingRequestParameterException("subscriptionId", "String");
        }
        if (updateContextSubscription.getRestriction() != null) {
            if (nullOrEmpty(updateContextSubscription.getRestriction().getAttributeExpression())
                    && nullOrEmpty(updateContextSubscription.getRestriction().getScopes())) {
                throw new MissingRequestParameterException("attributeExpression or scopes", "string");
            }
        }
    }

    public void checkUnsubscribeContext(UnsubscribeContext unsubscribeContext) throws MissingRequestParameterException {
        if (nullOrEmpty(unsubscribeContext.getSubscriptionId())){
            throw new MissingRequestParameterException("subscriptionId", "String");
        }
    }

    public void checkQueryContext(QueryContext queryContext) throws MissingRequestParameterException {
        if (nullOrEmpty(queryContext.getEntityIdList())) {
            throw new MissingRequestParameterException("entities", "List<EntityId>");
        }
        for(EntityId entityId : queryContext.getEntityIdList()) {
            checkEntityId(entityId);
        }
        if (queryContext.getRestriction() != null) {
            if (nullOrEmpty(queryContext.getRestriction().getAttributeExpression())) {
                throw new MissingRequestParameterException("attributeExpression", "string");
            }
        }
    }
    public void checkAppendContextElement(AppendContextElement appendContextElement) throws MissingRequestParameterException {
        if (nullOrEmpty(appendContextElement.getAttributeList())) {
            throw new MissingRequestParameterException("contextAttributes", "List<ContextAttribute>");
        }
    }

    public void checkUpdateContextElement(UpdateContextElement updateContextElement) throws MissingRequestParameterException {
        if (nullOrEmpty(updateContextElement.getContextAttributes())) {
            throw new MissingRequestParameterException("contextAttributes", "List<ContextAttribute>");
        }
    }

    public void checkUpdateContextAttribute(String entityID, String attributeName, String valueID, UpdateContextAttribute updateContextAttribute)
            throws MissingRequestParameterException, MismatchIdException {
        if (nullOrEmpty(entityID)) {
            throw new MissingRequestParameterException("entityID", "string");
        }
        if (nullOrEmpty(attributeName)) {
            throw new MissingRequestParameterException("attributeName", "string");
        }
        if (updateContextAttribute == null || updateContextAttribute.getAttribute() == null) {
            throw new MissingRequestParameterException("attribute", "ContextAttribute");
        }
        // Check attribute name matching
        if (!attributeName.equals(updateContextAttribute.getAttribute().getName())) {
            throw new MismatchIdException(attributeName, updateContextAttribute.getAttribute().getName());
        }
        // Check optional valueID matching
        if (valueID != null) {
            if (nullOrEmpty(valueID)) { // tests just emptiness
                throw new MissingRequestParameterException("valueID", "string");
            }
            if (updateContextAttribute.getAttribute().getMetadata() == null) {
                throw new MissingRequestParameterException("metadata", "Metadata");
            }
            // Check Metadata ID exists and equals valueID
            for (ContextMetadata metadata : updateContextAttribute.getAttribute().getMetadata()) {
                if ("ID".equals(metadata.getName())) {
                    if (valueID.equals(metadata.getValue())) {
                        return; // ! \\ Early return !
                    }
                    throw new MismatchIdException(valueID, String.valueOf(metadata.getValue()));
                }
            }
            throw new MissingRequestParameterException("ID", "Metadata ID");
        }
    }

    public void checkUpdateSubscription(String subscriptionID, UpdateContextSubscription updateContextSubscription) throws MissingRequestParameterException, MismatchIdException {
        checkUpdateContextSubscription(updateContextSubscription);
        // Check that subscriptionID parameter is equal to the one given in the message body
        if (!subscriptionID.equals(updateContextSubscription.getSubscriptionId())) {
            throw new MismatchIdException(subscriptionID, updateContextSubscription.getSubscriptionId());
        }
    }

    private void checkContextElementResponse(ContextElementResponse contextElementResponse) throws MissingRequestParameterException {

        if (contextElementResponse.getStatusCode() == null) {
            throw new MissingRequestParameterException("statusCode", "StatusCode");
        }
        if (contextElementResponse.getContextElement() == null) {
            throw new MissingRequestParameterException("contextElement", "ContextElement");
        }
        checkContextElement(contextElementResponse.getContextElement());
    }

    private void checkContextElement(ContextElement contextElement) throws MissingRequestParameterException {

        if (contextElement.getEntityId() == null) {
            throw new MissingRequestParameterException("entityId", "EntityId");
        }
        checkEntityId(contextElement.getEntityId());
        if (nullOrEmpty(contextElement.getContextAttributeList())) {
            throw new MissingRequestParameterException("contextAttributes", "List<ContextAttribute>");
        }
    }

    private void checkEntityId(EntityId entityId) throws MissingRequestParameterException {

        if (nullOrEmpty(entityId.getId())) {
            throw new MissingRequestParameterException("id", "string");
        }
        if (nullOrEmpty(entityId.getType())) {
            throw new MissingRequestParameterException("type", "string");
        }
        if (entityId.getIsPattern() == null)  {
            entityId.setIsPattern(false);
        }
    }

    private void checkContextRegistration(ContextRegistration contextRegistration) throws MissingRequestParameterException {
        if (nullOrEmpty(contextRegistration.getProvidingApplication())){
            throw new MissingRequestParameterException("providingApplication", "URI");
        }
        if (contextRegistration.getEntityIdList() != null) {
            for(EntityId entityId: contextRegistration.getEntityIdList()) {
                checkEntityId(entityId);
            }
        }
        if (contextRegistration.getContextRegistrationAttributeList() != null) {
            for(ContextRegistrationAttribute attribute: contextRegistration.getContextRegistrationAttributeList()) {
                checkContextRegistrationAttribute(attribute);
            }
        }
    }

    private void checkContextRegistrationAttribute(ContextRegistrationAttribute attribute) throws MissingRequestParameterException {
        if ((attribute.getName() == null) || (attribute.getName().isEmpty())) {
            throw new MissingRequestParameterException("name", "string");
        }
        if (attribute.getIsDomain() == null)  {
            throw new MissingRequestParameterException("isDomain", "boolean");
        }
    }

    private static boolean nullOrEmpty(URI e) {
        return e == null || e.toString().isEmpty();
    }

    private static boolean nullOrEmpty(String e) {
        return e == null || e.isEmpty();
    }

    private static boolean nullOrEmpty(List e) {
        return e == null || e.isEmpty();
    }

}
