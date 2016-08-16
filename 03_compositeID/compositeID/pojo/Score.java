package compositeID.pojo;

/**
 * 成绩
 * */
public class Score {
	
	
	private ScoreId scoreId;  //组合关系
	private double result;//成绩
	
	
	
	public ScoreId getScoreId() {
		return scoreId;
	}
	public void setScoreId(ScoreId scoreId) {
		this.scoreId = scoreId;
	}
	public double getResult() {
		return result;
	}
	public void setResult(double result) {
		this.result = result;
	}
}