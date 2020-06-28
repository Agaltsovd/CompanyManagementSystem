import java.util.ArrayList;
import java.util.Collections;
public class Employee implements Comparable<Employee>{
	private String name;
	private int yrLeavesEntitled;
	private ArrayList<LeaveRecord> leavesList;
	private ArrayList<Role> roles;
	public Employee(String n, int yle) {
		name=n;
		this.yrLeavesEntitled=yle;
		leavesList =new ArrayList<>();
		roles= new ArrayList<>();
	}
	public String getName() {
		return this.name;
	}
	
	public void isHeadOfMoreThanOneTeam(Team t) throws ExInsufficientArguments {
		for(Role r: roles) {
			if(r.getRole().equals(" (Head of Team)")&&r.getTeam()!=t) {
				throw new ExInsufficientArguments("Missing input:  Please give the name of the acting head for "+r.getTeam().getName());
			}
		}
		
	}
	
	public void addLeaveRecord(LeaveRecord lr) throws ExInsufficientBalance, ExOverlappedLeaves, ExDateHasAlreadyPassed {
		for(Role r:roles) {
			for(RActingHead ah: r.getTeam().getActingHeads()) {
				if(ah.getEmployee()==this) {
					if(ah.isOverlapped(lr.getStartDay(), lr.getEndDay())) {
						throw new ExOverlappedLeaves("Cannot take leave. "+this.getName()+" is the acting head of "+r.getTeam().getName()+" during "+ah.startToEnd()+"!");
					}
				}
			}
		}
		if(lr.getStartDay().compareTo(SystemDate.getInstance())==-1) {
			throw new ExDateHasAlreadyPassed("Wrong Date. System date is already "+SystemDate.getInstance()+"!");//check if the DatePassed
		}
		for(LeaveRecord leaveRecord:this.getLeaveRecords()) {
			if(lr.isOverlapped(leaveRecord.getStartDay(), leaveRecord.getEndDay())) {
				throw new ExOverlappedLeaves("Leave overlapped: The leave period "+leaveRecord+" is found!");//check if Leaves overlap
			}
		}
		if(lr.countDays()>this.getRemainingLeaves())
			throw new ExInsufficientBalance("Insufficient balance. "+this.getRemainingLeaves()+" days left only!");//check if insufficient balance
		
		leavesList.add(lr);
		this.yrLeavesEntitled=this.yrLeavesEntitled-lr.countDays();
		Collections.sort(leavesList);
		
		
		
	}
	public void removeRole(Role r) {
		
		roles.remove(r);
		Collections.sort(roles);
		
	}
	public void addRole(Role r) {
		
		roles.add(r);
		Collections.sort(roles);
	}
	public void listRoles() {
		
		if(roles.size()==0) {
			System.out.println("No role");
		}
		else {
		for(Role r:roles) {
			System.out.println(r);
		}
		}
	}
	
	public Role findRole(Team t) {
		for(Role r:roles) {
			if(r.getTeam().equals(t)) {
				return r;
			}
		}
		return null;
	}
	
	public String getRole(Team t) {
			for(Role r:roles) {
				if(r.getTeam().equals(t)) {
					return r.getRole();
				}
			}
			return null;
	}
	
	public int getRemainingLeaves() {
		return this.yrLeavesEntitled;
	}
	
	public void removeLeaveRecord(LeaveRecord lr) {
		leavesList.remove(lr);
		this.yrLeavesEntitled+=lr.countDays();
		
	}
	
	public ArrayList<LeaveRecord> getLeaveRecords(){
		return leavesList;
		
	}
	
	public int getLeaves() {
		return this.yrLeavesEntitled;
	}
	
	public void ListLeaves() {
		if(leavesList.size()==0) {
			System.out.println("No leave record");
		}
		for(LeaveRecord lr:leavesList) {
			System.out.println(lr);
		}
	}
	
	public static Employee searchEmployee(ArrayList<Employee> list,String nameToSearch) throws ExEmployeeNotFound {
			for(Employee e:list) {
				if(e.getName().contentEquals(nameToSearch)) {
					return e;
				}
			}
			throw new ExEmployeeNotFound();
			}
	
	@Override
	public int compareTo(Employee another) {
		// TODO Auto-generated method stub
		return this.name.compareTo(another.name);
	}
	
	public static void list(ArrayList<Employee> list) {
		for(Employee e:list) {
				System.out.println(e.getName()+" (Entitled Annual Leaves: "+e.getLeaves()+" days)");
		
	}
	
}
}