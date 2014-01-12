package mpr.proj.pedigree;

public class Color {

	private long id;
	private String lname;
	private String sname;

	public Color(long id, String lname, String sname) {
		this.id = id;
		this.lname = lname;
		this.sname = sname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getLname() {
		return lname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSname() {
		return sname;
	}

}
