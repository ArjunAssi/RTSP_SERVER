package com.musigma.ird.message;

import jade.util.leap.Serializable;

/******************************************************************************
 * THIS CLASS IS FOR STORING THE MESSAGE STRUCTURE THAT IS GOING TO BE SENT TO
 * THE RECEIVER CLIENT. IT HAS THE TIME STAMP AND THE FRAME AS A BYTE ARRAY
 * OBJECT. | AUTHOR : ARJUN ASSI
 ******************************************************************************/

public class MessageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*******************
	 * CLASS VARIABLES *
	 *******************/

	// Decoded image stored as a byte array object
	private byte[] byteArray;

	/*****************
	 * CLASS METHODS *
	 *****************/

	/*****************************
	 * GETTER AND SETTER FUNCTIONS
	 *****************************/

	public byte[] getByteArray() {
		return byteArray;
	}

	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}

}
