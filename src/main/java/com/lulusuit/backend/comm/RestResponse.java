package com.lulusuit.backend.comm;

public class RestResponse extends AbstractRestResponse {
	
	public RestResponse(boolean success, AbstractRestData data) {
		super(success, data);
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
