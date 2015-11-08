package edu.lamar.othelo.server.irp;

import java.io.Serializable;

/**
 * This is a message interface, all the message should be wrapped inside this
 * and sent across.
 * @author agehlot
 *
 */
public interface IMessage extends Serializable {
	String getMessage();
	String getMessageType();
}
