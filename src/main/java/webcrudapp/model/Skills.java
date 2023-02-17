package webcrudapp.model;

import java.util.List;

public class Skills {
	private int id;
	private List<String> skillList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<String> getSkillList() {
		return skillList;
	}
	public void setSkillList(List<String> skillList) {
		this.skillList = skillList;
	}
}
