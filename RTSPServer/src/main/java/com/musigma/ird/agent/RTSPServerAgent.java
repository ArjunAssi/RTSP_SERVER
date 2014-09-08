package com.musigma.ird.agent;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import com.musigma.ird.behaviours.RTSPServerAgentBehaviour;
import com.musigma.ird.message.MessageBean;
import com.musigma.ird.setup.RTSPServerSetup;

/******************************************************************************
 * THIS CLASS IS THE RTSP SERVER AGENT. IT SENDS THE BYTE STREAM IMAGES TO THE
 * CLIENTS. | AUTHOR : ARJUN ASSI
 ******************************************************************************/
public class RTSPServerAgent extends Agent {

	/*******************
	 * CLASS VARIABLES *
	 *******************/

	private static final long serialVersionUID = 1L;

	// ACL message received
	private ACLMessage aclMessage;

	// Message bean object
	private MessageBean messageBean;

	/*****************
	 * CLASS METHODS *
	 *****************/

	/*******************************************************************
	 * THIS FUNCTION SETS UP THE AGENT. IT ADDS THE CYCLIC BEHAVIOUR TO
	 * BROADCAST THE MESSAGE BEAN TO MULTIPLE CLIENTS
	 *******************************************************************/
	protected void setup() {

		// Initialize the ACL message 
		aclMessage = new ACLMessage(ACLMessage.INFORM);
		
		// Initialize the message bean
		messageBean = new MessageBean();
		
		// Initialize the RTSP server 
		RTSPServerSetup rtspServerSetup = new RTSPServerSetup();
		
		// Add the Behaviour
		addBehaviour(new RTSPServerAgentBehaviour(rtspServerSetup, aclMessage,
				messageBean));
	}

}
