package messaging;

import types.TaskType;

public class MessageZoom extends MessageImage {

	/**
	 * coordinates of top-left and bottom-right pixels of the part
	 * of the image on which the zoom task will be applied
	 */
	int [] coord;

	public MessageZoom (TaskType taskType, int[][][] pixels,
						int width, int height, int [] coord) {
		super(taskType, pixels, width, height);
		this.coord = coord;
	}

	public int [] getCoord() {
		return coord;
	}

	public void setCoord(int [] coord) {
		this.coord = coord;
	}
}
