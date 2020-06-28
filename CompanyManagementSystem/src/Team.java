import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
public class Team implements Comparable<Team>{
	private String teamName;
	private Employee head;
	private Day dateSetup;
	private ArrayList<Employee> teamMembers;
	private ArrayList<RActingHead> actingHeadsList;
	public Team(String n, Employee hd) {
		this.teamName=n;
		this.head=hd;
		dateSetup=SystemDate.getInstance().clone();
		teamMembers=new ArrayList<>();
		teamMembers.add(hd);
		hd.addRole(new RHead(this));
		this.actingHeadsList=new ArrayList<>();
	}
	public static Team searchTeam(ArrayList<Team> list,String teamToSearch) throws ExTeamNotFound {
		for(Team t: list) {
			if(t.getName().contentEquals(teamToSearch)) {
				return t;
			}
		}
		throw new ExTeamNotFound();
		}
	public void addActingHead(RActingHead ah) throws ExOverlappedLeaves, ExEmployeeNotFound {
		for(LeaveRecord lr:ah.getLeaveRecords()) {
			if(ah.isOverlapped(lr.getStartDay(), lr.getEndDay())) {
				throw new ExOverlappedLeaves(ah.getEmployee().getName()+" is on leave during "+lr+"!");
			}
		}
		
		if(!teamMembers.contains(ah.getEmployee())) {
			throw new ExEmployeeNotFound("Employee ("+ah.getEmployee().getName()+") not found for "+this.teamName+"!");
		}
		this.actingHeadsList.add(ah);
		Collections.sort(actingHeadsList, new Comparator<RActingHead>()
		{
			public int compare(RActingHead r1, RActingHead r2)
			{
				return r1.getLeaveRecord().compareTo(r2.getLeaveRecord());
			}
		}
);
		
	}
	public void removeActingHead(RActingHead ah) {
		this.actingHeadsList.remove(ah);
		Collections.sort(this.actingHeadsList);
	}
	
	public String getName() {
		return this.teamName;
	}
	
	public ArrayList<Employee> getMembers(){
		return teamMembers;
	}
	
	
	public String getLeaderName() {
		return this.head.getName();
	}
	
	
	public ArrayList<RActingHead> getActingHeads(){
		return this.actingHeadsList;
	}
	
	
	public static void list(ArrayList<Team> list)
	{
	//Learn: "-" means left-aligned
	System.out.printf("%-30s%-10s%-13s\n", "Team Name", "Leader","Setup Date");
	for (Team t : list)
	System.out.printf("%-30s%-10s%-13s\n",t.getName(),t.getLeaderName(),t.dateSetup.toString()); //display t.teamName, etc..
	}
	
	
	@Override
	public int compareTo(Team another) {
		
		return this.getName().compareTo(another.getName());
	}
	
	
	public void addTeamMember(Employee e) throws ExEmployeeAlreadyJoined {
		if(teamMembers.contains(e)) throw new ExEmployeeAlreadyJoined("The employee has joined the team already!");
		teamMembers.add(e);
		e.addRole(new RNormal(this));
		Collections.sort(teamMembers);
		
	}
	
	
	public void removeTeamMember(Employee e) {
		teamMembers.remove(e);
		e.removeRole(e.findRole(this));
		Collections.sort(teamMembers);
	}
	
	
	public void listTeamMembers() {
		System.out.println(this.getName()+":");
		for(Employee e: teamMembers) {
			System.out.println(e.getName()+e.getRole(this));
		}
		if(this.actingHeadsList.size()!=0) {
		System.out.println("Acting heads:");
		for(RActingHead ah:this.actingHeadsList) {
			System.out.println(ah);
		}
		}
		
	}

}
