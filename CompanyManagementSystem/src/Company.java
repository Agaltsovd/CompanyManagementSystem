import java.util.ArrayList;
import java.util.Collections;

public class Company {
	private ArrayList<Employee> allEmployees;
	private ArrayList<Team> allTeams;
	
	private static Company instance = new Company();
	
	private Company() {
		this.allEmployees=new ArrayList<>();
		this.allTeams=new ArrayList<>();
		
	}
	
	public static Company getInstance() { return instance; }
	
	public void listTeams() //See how it is called in main()
	{
	Team.list(allTeams); // allTeams
	}
	
	public void ListEmployees() {
		Employee.list(allEmployees);
	}
	
	public void ListLeaves() {
		for(Employee e:allEmployees) {
			System.out.println(e.getName()+":");
			e.ListLeaves();
		}
	}
	
	public Employee createEmployee(String s,int n) //See how it is called in main()
	{
	Employee e = new Employee(s,n);
	allEmployees.add(e);
	Collections.sort(allEmployees); // allEmployees
	return e; // the return value is useful for later undoable command.
	}
	
	public void addEmployee(Employee e) {
		allEmployees.add(e);
		Collections.sort(allEmployees);
	}
	
	public void removeEmployee(Employee e) {
		allEmployees.remove(e);
	}
	
	public Employee findEmployee(String s)  {
		Employee emp=null;
		for(Employee e: this.allEmployees) {
			if(e.getName().equals(s)) {
				emp=e;
			}
		}
		return emp;
		
	}
	
	public Employee findEmployeeForTeam(String emp, Team t) throws ExEmployeeNotFound {
		Employee employee=null;
		for(Employee e: t.getMembers()) {
			if(e.getName().equals(emp)) {
				employee=e;
			}
		}
		if(employee!=null) {
			return employee;
		}
		else {
			throw new ExEmployeeNotFound("Employee ("+emp+") not found for "+t.getName()+"!");
		}
	}
	
	public boolean teamExists(String s) {
		for(Team t:this.allTeams) {
			if(t.getName().equals(s))
				return true;
		}
		return false;
	}
	
	public Team findTeam(String s) {
		Team team=null;
		for(Team t:this.allTeams) {
			if(t.getName().equals(s))
				team=t;
		}
		 return team;
	}
	
	public void ListTeamMembers() {
		for(Team t:allTeams) {
			t.listTeamMembers();
			System.out.println();
		}
	}
	
	public ArrayList<Employee> getListOfEmployees(){
		return allEmployees;
	}
	
	public ArrayList<Team> getListOfTeams(){
		return allTeams;
	}
	
	public Team createTeam(String s, String n) throws ExEmployeeNotFound //See how it is called in main()
	{
	
		
		Employee e = Employee.searchEmployee(allEmployees,n);
		Team t = new Team(s,e);
		allTeams.add(t);
		Collections.sort(allTeams); 
		return t;
  
	}
	
	public void deleteTeam(Team t) {
		
		allTeams.remove(t);
	}
	
	public void addTeam(Team t) {
		allTeams.add(t);
		Collections.sort(allTeams);
	}
}