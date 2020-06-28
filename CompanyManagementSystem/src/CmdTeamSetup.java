

public class CmdTeamSetup extends RecordedCommand{
		private Team team;
		private Employee head;
		
		
		
	@Override
	public void execute(String[] cmdParts) {
		// TODO Auto-generated method stub
		try {
			if(cmdParts.length<3) {
				throw new ExInsufficientArguments();
			}
			
			Company company=Company.getInstance();
			head=Employee.searchEmployee(company.getListOfEmployees(), cmdParts[2]);
			if(company.teamExists(cmdParts[1])) {
				throw new ExTeamAlreadyExists();
			}
			 team=company.createTeam(cmdParts[1], cmdParts[2]);
			
			addUndoCommand(this);
			clearRedoList();
			System.out.println("Done.");
		} catch (ExEmployeeNotFound e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		catch(ExInsufficientArguments e) {
			System.out.println(e.getMessage());
		}
		catch(ExTeamAlreadyExists e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void undoMe() {
		// TODO Auto-generated method stub
		Company company = Company.getInstance();
		company.deleteTeam(team);
		addRedoCommand(this);
		head.removeRole(head.findRole(team));
		
		
	}
	

	@Override
	public void redoMe() {
		// TODO Auto-generated method stub
		Company company = Company.getInstance();
		company.addTeam(team);
		addUndoCommand(this);
		head.addRole(new RHead(team));
		
	}

}
