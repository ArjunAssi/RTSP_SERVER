package com.musigma.ird.setup;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.websocket.Session;
import org.apache.log4j.Logger;
import com.musigma.ird.message.MessageBean;

/*********************************************************************************
 * THIS CLASS PROVIDES THE WEB SOCKET FUNCTIONALITY. IT BROADCASTS THE MESSAGE
 * BEAN SO THAT CLIENTS CAN RECEIVE IT ON THEIR WEB BROWSERS. | AUTHOR : ARJUN
 * ASSI
 *********************************************************************************/

public class RTSPServerSetup {

	/*******************
	 * CLASS VARIABLES *
	 *******************/

	// Queue to hold the various connected sessions
	private static Queue<Session> queue = new ConcurrentLinkedQueue<Session>();

	// Logger
	private static org.apache.log4j.Logger log = Logger
			.getLogger(RTSPServerSetup.class.getName());

	/*****************
	 * CLASS METHODS *
	 *****************/

	/**************************************************************************
	 * THIS FUNCTION GETS INVOKED EVERYTIME THE SERVER RECEIVES A MESSAGE FROM
	 * ANY CLIENT. IN THIS IMPLEMENTATION IT DOES NOTHING
	 **************************************************************************/
	public void onMessage(Session session, String message) {

		// Just log that the message was received
		try {
			log.info("Received message " + message + " from " + session.getId());

		} catch (Exception e) {
			log.error(e);
		}
	}

	/**************************************************************************
	 * THIS FUNCTION OPENS A NEW SESSION AND ADDS IT TO THE QUEUE OF RECEIVERS.
	 ***************************************************************************/
	public void open(Session session) {

		// Add the new session to the queue
		queue.add(session);
		log.info("New session opened: " + session.getId());
	}

	/**************************************************************
	 * THIS FUNCTION REMOVES ANY SESSION IN WHICH AN ERROR OCCURED.
	 **************************************************************/
	public void error(Session session) {

		// Removes any session in which any eror occurs
		queue.remove(session);
		log.error("Error in session " + session.getId());
	}

	/***************************************************************
	 * THIS FUNCTION CLOSES A SESSION ANY REMOVES IT FROM THE QUEUE.
	 ***************************************************************/
	public void closedConnection(Session session) {

		// Remove the session from the queue
		queue.remove(session);
		log.info("Session closed: " + session.getId());
	}

	/***********************************************************************
	 * THIS FUNCTION BROADCASTS THE MESSAGE BEAN TO ALL THE CLIENTS THAT ARE
	 * CONNECTED.
	 ***********************************************************************/
	public void sendAll(MessageBean messageBean) {

		try {

			// Find out all the closed sessions
			ArrayList<Session> closedSessions = new ArrayList<>();

			// Scan through the list of sessions
			for (Session session : queue) {

				// If the session is open send the object else log that the
				// session is closed
				if (!session.isOpen()) {
					log.error("Closed session: " + session.getId());
					closedSessions.add(session);
				} else {

					// Send the message bean to the clients
					session.getBasicRemote().sendObject(messageBean);
				}
			}
			queue.removeAll(closedSessions);

			// Catch the errors
		} catch (Throwable e) {
			log.error(e);
		}
	}
}
