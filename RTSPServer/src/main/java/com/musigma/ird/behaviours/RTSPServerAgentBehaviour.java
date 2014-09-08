package com.musigma.ird.behaviours;

import org.apache.log4j.Logger;
import com.musigma.ird.message.MessageBean;
import com.musigma.ird.setup.RTSPServerSetup;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

/*******************************************************************************
 * THIS CLASS IMPLEMENTS THE CYCLIC BEHAVIOUR OF THE RTSP SERVER AGENT. IT
 * RECEIVES THE A MESSAGE FROM THE IMAGE PROCESSING AGENT IN THE FORM OF A BYTE
 * STREAM. AND BROADCASTS THE BYTE STREAM TO ALL THE RECIEVER CLIENTS. | AUTHOR
 * : ARJUN ASSI
 *******************************************************************************/
public class RTSPServerAgentBehaviour extends CyclicBehaviour {

	/*******************
	 * CLASS VARIABLES *
	 *******************/

	private static final long serialVersionUID = 1L;

	// Handler for broadcasting the messages to the client
	RTSPServerSetup rtspServerSetup;

	// ACL message that is received from the previous client
	ACLMessage aclMessage;

	// Message bean that contains image as a byte stream
	MessageBean messageBean;

	// Logger
	private static org.apache.log4j.Logger log = Logger
			.getLogger(RTSPServerAgentBehaviour.class.getName());

	/*****************
	 * CLASS METHODS *
	 *****************/

	/********************************************************************
	 * THIS IS THE CONSTRUCTOR METHOD FOR THIS BEHAVIOUR. IT ASSIGNS THE
	 * ARGUMENTS PASSED FROM THE SETUP FUNCTION TO THE BEHAVIOUR.
	 ********************************************************************/
	public RTSPServerAgentBehaviour(RTSPServerSetup rtspServerSetup,
			ACLMessage aclMessage, MessageBean messageBean) {

		// Assign the references to the objects of this class
		this.rtspServerSetup = rtspServerSetup;
		this.aclMessage = aclMessage;
		this.messageBean = messageBean;
	}

	/**********************************************************************
	 * THIS FUNCTION RECEIVES THE MESSAGE FROM THE PREVIOUS AGENT AND THEN
	 * BROADCASTS THE MESSAGE TO THE VARIOUS CLIENTS.
	 **********************************************************************/
	public void action() {

		// Receive the message
		aclMessage = myAgent.receive();

		// If message is not null then broadcast else block
		if (aclMessage != null) {

			try {
				// Get the content of the message
				messageBean = (MessageBean) aclMessage.getContentObject();
			} catch (UnreadableException e) {
				log.error(e);
			}

			// Broadcast the message bean to all the clients
			rtspServerSetup.sendAll(messageBean);

		} else {

			// Wait for the message to arrive
			block();
		}
	}
}
