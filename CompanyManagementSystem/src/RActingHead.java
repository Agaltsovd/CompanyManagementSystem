import java.util.ArrayList;

public class RActingHead extends Role{
	 
	private Employee employee;
	
	private LeaveRecord lr;
	private Team team;
	public RActingHead(Team t ,LeaveRecord lr,Employee e){
		super(t);
		employee=e;
		this.lr=lr;
		
	}
	public Employee getEmployee() {
		return employee;
	}
	public boolean isOverlapped(Day d1,Day d2) {
		boolean flag=false;
		
		if(lr.getStartDay().isBetween(d1, d2)||lr.getEndDay().isBetween(d1, d2)) {
			flag=true;
		}
		return flag;
	}
	public ArrayList<LeaveRecord> getLeaveRecords(){
		return employee.getLeaveRecords();
	}
	@Override
	public String toString() {
		return lr+": "+employee.getName();
	}
	public String startToEnd() {
		return lr.toString();
	}
	public LeaveRecord getLeaveRecord() {
		return this.lr;
	}
	@Override
	public String getRole() {
		return "Acting Head";
	}
	@Override
	public Team getTeam() {
		return this.team;
	}
	
	
}
