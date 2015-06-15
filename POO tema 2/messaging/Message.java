package messaging;

import types.TaskType;

public abstract class Message {

	private static int ID = 0;
	private TaskType taskType;
	private int messageId;

	public Message(TaskType taskType) {
		super();
		this.taskType = taskType;
		generateId();
	}

	/**
	 * 	Generates unique IDs for Message;
	 */
	public void generateId() {
		this.messageId = ID++;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public int getId() {
		return messageId;
	}
}
