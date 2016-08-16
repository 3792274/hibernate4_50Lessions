package compositeID.pojo;

import java.io.Serializable;



/**
 * 复合主键:
 * 1.必须实现Serializable
 * 2.最好实现 equals()、hashCode()
 */
public class ScoreId implements Serializable { 
	
	
	
	private int stuId;// 学生编号
	private int subjectId;// 科目编号

	public int getStuId() {
		return stuId;
	}

	public void setStuId(int stuId) {
		this.stuId = stuId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + stuId;
		result = prime * result + subjectId;
		return result;
	}

	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScoreId other = (ScoreId) obj;
		if (stuId != other.stuId)
			return false;
		if (subjectId != other.subjectId)
			return false;
		return true;
	}
	
	
	
}
