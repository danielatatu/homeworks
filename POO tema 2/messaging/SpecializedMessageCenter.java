package messaging;

import components.Component;
import java.util.ArrayList;

/**
 * Class that extends MessageCenter and implements its abstract methods.
 */
public class SpecializedMessageCenter extends MessageCenter {

	private ArrayList <Component> components;
	private ArrayList <MessageCenter> connectedCenters;
	/**
	 * list of unique IDs of messages that have
	 * already been processed by this center
	 */
	private ArrayList <Integer> processedMessages;

	public SpecializedMessageCenter(String centerName) {
		super(centerName);
	}

	public void subscribe (Component c) {
		if (components == null)
			components = new ArrayList <Component>();
		components.add(c);
	}

	public void addConectedCenter (MessageCenter mc) {
		if (connectedCenters == null)
			connectedCenters = new ArrayList <MessageCenter>();
		connectedCenters.add(mc);
	}

	public Message publishAlgorithm (Message message) {

		if ( processedMessages == null )
			processedMessages = new ArrayList <Integer>();

		if ( ! processedMessages.isEmpty() )
		    for (Integer id : processedMessages)
		        if (id == message.getId())
		            return null;

	    processedMessages.add (message.getId());

	    if ( components != null )
		    for ( Component c : components )
		        if (c.getTaskType() == message.getTaskType())
		            return c.notify(message);

	    if ( connectedCenters != null ) {
	    	Message m;
		    for (MessageCenter v : connectedCenters) {
		    	m = v.publish(message);
		    	if (m != null)
		    		return m;
		    }
	    }

		return null;
	}
}
