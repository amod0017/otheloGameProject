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
	private final String password;

	/**
	 * makeAMoveCoordinates are null if message is not related to makeAMove.
	 * password can be null if not registered/loggedIn request
	 * 
	 * @param message
	 * @param messageType
	 * @param loginId
	 * @param makeAMoveCoordinates
	 * @param password
	 */
	MessageImpl(final String message, final String messageType,
			final String loginId, final String makeAMoveCoordinates,
			final String password) {
		this.message = message;
		this.messageType = messageType;
		this.loginId = loginId;
		this.makeAMoveCoordinates = makeAMoveCoordinates;
		this.password = password;
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

	/**
	 * @return the password
	 */
	@Override
	public String getPassword() {
		return password;
	}

}
