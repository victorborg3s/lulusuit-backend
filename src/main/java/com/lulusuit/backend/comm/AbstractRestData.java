package com.lulusuit.backend.comm;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRestData {

	protected Object value = null;
	protected List<String> errors = null;
	protected List<String> messages = null;

	protected AbstractRestData(Object value) {
		this.value = value;
		this.errors = new ArrayList<String>();
		this.messages = new ArrayList<String>();
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
}
