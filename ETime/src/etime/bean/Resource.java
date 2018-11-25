package etime.bean;

public class Resource {
	private static int serialNo;
	//comment
	private Integer id;
	private String name;
	private Integer capacity;
	private String type;
	private Integer superUserId;
	
	public Resource() {}
	
	public Resource(String name, Integer capacity,
			String type, Integer superUserId) {
		super();
		this.name = name;
		this.capacity = capacity;
		this.type = type;
		this.superUserId = superUserId;
	}
	
	public Resource(Integer id, String name, Integer capacity,
			String type, Integer superUserId) {
		super();
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.type = type;
		this.superUserId = superUserId;
	}
	public Resource(Integer id, String name, Integer capacity,
			String type) {
		super();
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.type = type;
	}



	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public static int getSerialNo() {
		return serialNo;
	}

	public static void setSerialNo(int serialNo) {
		Resource.serialNo = serialNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public Integer getSuperUserId() {
		return superUserId;
	}

	public void setSuperUserId(Integer superUserId) {
		this.superUserId = superUserId;
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", name=" + name + ", capacity="
				+ capacity + ", type=" + type + ", superUserId=" + superUserId
				+ "]";
	}
}
