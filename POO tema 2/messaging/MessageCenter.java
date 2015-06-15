package messaging;

import components.Component;

public abstract class MessageCenter {

	private String centerName;

	public MessageCenter(String centerName) {
		super();
		this.centerName = centerName;
	}

	/**
	 * Subscribes an associated component to this MessageCenter.
	 *
	 * @param c component
	 */
	public abstract void subscribe(Component c);

	/**
	 * Adds a MessageCenter to the list of neighbors
	 * (connected centers) of this center.
	 *
	 * @param mc connected center (neighbor)
	 */
	public abstract void addConectedCenter(MessageCenter mc);

	public String getName () {
		return centerName;
	}

	/**
	 * Prints this center`s name in order to notify that
	 * the message arrived here, then processes the message
	 * through 'publishAlgorithm' method.
	 *
	 * @param message message that has to be processed
	 */
	public Message publish(Message message)	{
		System.out.println(centerName);
		return publishAlgorithm(message);
	}

	/**
	 * Verifies if the message has already been processed by this center and
	 * if not, sends it to the specific component or to a connected center.
	 */
	protected abstract Message publishAlgorithm(Message message);
}
