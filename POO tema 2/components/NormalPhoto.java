package components;

import messaging.Message;
import types.TaskType;

/**
 * Component used for returning a photo after it has been
 * turned upside-down using the RawPhoto component.
 */
public class NormalPhoto extends Component {

	public NormalPhoto() {
		super(TaskType.NORMAL_PHOTO);
	}

	@Override
	public Message notify(Message message) {
		return (new RawPhoto()).notify(message);
	}
}
