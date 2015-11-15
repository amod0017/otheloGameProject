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
	private final String loginId;
	private final String makeAMoveCoordinates;

	/**
	 * makeAMoveCoordinates are null if message is not related to makeAMove.
	 * @param message
	 * @param messageType
	 * @param loginId
	 * @param makeAMoveCoordinates
	 */
	MessageImpl(final String message, final String messageType,
			final String loginId, final String makeAMoveCoordinates) {
		this.message = message;
		this.messageType = messageType;
		this.loginId = loginId;
		this.makeAMoveCoordinates = makeAMoveCoordinates;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getMessageType() {
		return messageType;
	}

	@Override
	public String getLogin() {
		return loginId;
	}

	@Override
	public String getMakeAMoveCoordinates() {
		return makeAMoveCoordinates;
	}

}
