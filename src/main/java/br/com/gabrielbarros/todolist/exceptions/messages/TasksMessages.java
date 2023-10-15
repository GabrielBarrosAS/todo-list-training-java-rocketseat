package br.com.gabrielbarros.todolist.exceptions.messages;

public class TasksMessages {
	public static final String INVALID_START_DATE = "Invalid start date for task: {0}";
	public static final String INVALID_END_DATE = "Invalid end date for task: {0}";
	public static final String START_DATE_AFTER_END_DATE = "Start date ({0}) must be later than end date ({1})";
	public static final String TASK_NOT_FOUND = "Task not found: filter = {0}, value = {1}";
	public static final String ERROR_UPDATE_TASK_FROM_OTHER_USER = "User not allowed to update another user's task";
}
