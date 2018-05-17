package com.lulusuit.backend.comm;

public abstract class AbstractRestResponse {

	protected boolean success = false;
	protected AbstractRestData data = null;
	
	protected AbstractRestResponse(boolean success, AbstractRestData data) {
		this.success = success;
		this.data = data;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public AbstractRestData getData() {
		return data;
	}
	
	public void setData(AbstractRestData data) {
		this.data = data;
	}
	

}
