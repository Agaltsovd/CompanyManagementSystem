
public class CmdAddTeamMember extends RecordedCommand{

	
	private Team team;
	private Employee member;
	@Override
	public void execute(String[] cmdParts) {
		try {
			if(cmdParts.length<3) {
				throw new ExInsufficientArguments();//checking if there are enough arguments
			}
			Company company=Company.getInstance();
			
			team=Team.searchTeam(company.getListOfTeams(), cmdParts[1]);
			member=Employee.searchEmployee(company.getListOfEmployees(), cmdParts[2]);
			

			team.addTeamMember(member);
			
			addUndoCommand(this);
			clearRedoList();
			System.out.println("Done.");
		} catch (ExEmployeeAlreadyJoined e) {
			
			System.out.println(e.getMessage());
		} catch (ExInsufficientArguments e) {
			
			System.out.println(e.getMessage());
		} catch (ExEmployeeNotFound e) {
		
			System.out.println(e.getMessage());
		} catch (ExTeamNotFound e) {
			
			System.out.println(e.getMessage());
		}
		
		
	}

	@Override
	public void undoMe() {
		team.removeTeamMember(member);
		addRedoCommand(this);
		
	}

	@Override
	public void redoMe() {
		try {
			team.addTeamMember(member);
		} catch (ExEmployeeAlreadyJoined e) {
			
			e.printStackTrace();
		}
		addUndoCommand(this);
		
	}
//	public void isInTeam(Employee e) throws ExEmployeeAlreadyJoined {
//		if(team.getMembers().contains(e)) {
//			throw new ExEmployeeAlreadyJoined("The employee has joined the team already!");
//		}
//	}

}
