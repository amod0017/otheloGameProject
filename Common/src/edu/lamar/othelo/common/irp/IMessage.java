package edu.lamar.othelo.common.irp;

import java.io.Serializable;

/**
 * This is a message interface, all the message should be wrapped inside this
 * and sent across.
 *
 * @author agehlot
 *
 */
public interface IMessage extends Serializable {
	String getMessage();

	String getMessageType();

	String getLogin();

	String getMakeAMoveCoordinates();

	String getPassword();
}
