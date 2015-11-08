package edu.lamar.othelo.common;

import edu.lamar.othelo.common.irp.IMessage;

/**
 * @author agehlot
 *
 */
public class MessageImpl implements IMessage {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final String message;
	private final String messageType;

	MessageImpl(final String message, final String messageType) {
		this.message = message;
		this.messageType = messageType;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getMessageType() {
		return messageType;
	}

}
