
public class CmdListLeaves implements Command {
	

	@Override
	public void execute(String[] cmdParts) {
	
		Company company = Company.getInstance();
		if(cmdParts.length==1) {
			company.ListLeaves();
		}
		
		else {
			Employee e = company.findEmployee(cmdParts[1]);
			e.ListLeaves();
		}
	
}
}
