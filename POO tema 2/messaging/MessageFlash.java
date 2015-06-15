package messaging;

import types.*;

public class MessageFlash extends MessageImage {

	/**
	 * type of the flash: auto / on / off
	 */
	FlashType type;

	public MessageFlash (TaskType taskType, int[][][] pixels,
						int width, int height, FlashType type) {
		super(taskType, pixels, width, height);
		this.type = type;
	}

	public FlashType getType() {
		return type;
	}

	public void setType(FlashType type) {
		this.type = type;
	}
}
