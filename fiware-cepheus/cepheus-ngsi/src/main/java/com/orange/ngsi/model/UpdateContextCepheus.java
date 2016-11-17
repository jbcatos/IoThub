/*
 * Copyright (C) 2015 Orange
 *
 * This software is distributed under the terms and conditions of the 'GNU GENERAL PUBLIC LICENSE
 * Version 2' license which can be found in the file 'LICENSE.txt' in this package distribution or
 * at 'http://www.gnu.org/licenses/gpl-2.0-standalone.html'.
 */

package com.orange.ngsi.model;


public class UpdateContextCepheus {
    
	private UpdateContext updateContext;
	
	// connection details of remote broker/url
    private String connectionRemoteBroker;

    // service details of remote broker i.e.: queue name/servicePath
    private String queueName;
    
    public UpdateContextCepheus() {
    }
    
	public UpdateContext getUpdateContext() {
		return updateContext;
	}

	public void setUpdateContext(UpdateContext updateContext) {
		this.updateContext = updateContext;
	}

	public String getConnectionRemoteBroker() {
		return connectionRemoteBroker;
	}

	public void setConnectionRemoteBroker(String connectionRemoteBroker) {
		this.connectionRemoteBroker = connectionRemoteBroker;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	@Override
    public String toString() {
        return "UpdateContext{" +
                "contextElements=" + updateContext.getContextElements() +
                ", updateAction=" + updateContext.getUpdateAction() +
                ", connectionRemoteBroker=" + connectionRemoteBroker +
                ", queueName=" + queueName +
                '}';
    }
}
