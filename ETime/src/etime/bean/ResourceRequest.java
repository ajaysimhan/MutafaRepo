package etime.bean;

import java.util.Date;

public class ResourceRequest {
	private Integer resourceRequestId;
	private Integer resourceId;
	private String username;
	private Date from;
	private Date to;
	private String status;
	
	public ResourceRequest(Integer resourceId, String username, Date from,
			Date to) {
		super();
		this.resourceId = resourceId;
		this.username = username;
		this.from = from;
		this.to = to;
	}
	public ResourceRequest(Date from, Date to) {
		super();
		this.from = from;
		this.to = to;
	}
	public ResourceRequest(Integer resourceRequestId, Integer resourceId,
			String username, Date from, Date to, String status) {
		super();
		this.resourceRequestId = resourceRequestId;
		this.resourceId = resourceId;
		this.username = username;
		this.from = from;
		this.to = to;
		this.status = status;
	}
	public Integer getResourceRequestId() {
		return resourceRequestId;
	}
	public void setResourceRequestId(Integer resourceRequestId) {
		this.resourceRequestId = resourceRequestId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Request [resourceRequestId=" + resourceRequestId
				+ ", resourceId=" + resourceId + ", username=" + username
				+ ", from=" + from + ", to=" + to + ", status=" + status + "]";
	}
}
