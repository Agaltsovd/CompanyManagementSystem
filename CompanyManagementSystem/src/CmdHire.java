
public class CmdHire extends RecordedCommand {

	Company company = Company.getInstance();
	private Employee e;
	@Override
	public void execute(String[] cmdParts)  {
	
		try {
			if(cmdParts.length<3) {
				throw new ExInsufficientArguments();//checking if there are enough arguments
			}
			if(Integer.parseInt(cmdParts[2])>300) { //checking if the number of leaveDays more than 300
				throw new ExOutOfRange();
			}
			
			Company company = Company.getInstance();
			
			if(company.findEmployee(cmdParts[1])!=null) throw new ExEmployeeAlreadyExists(); //check if Employee already exists
			
			 e=new Employee(cmdParts[1], Integer.parseInt(cmdParts[2]));
			company.addEmployee(e);
			
			addUndoCommand(this);
			clearRedoList();
			System.out.println("Done.");
			
		} catch ( ExInsufficientArguments e) {
			
			System.out.println(e.getMessage());
		}
		catch(ExEmployeeAlreadyExists e) {
			System.out.println(e.getMessage());
		}
		catch(ExOutOfRange e) {
			System.out.println(e.getMessage());
		} 
		
		
	}
	@Override
	public void undoMe() {
		// TODO Auto-generated method stub
		Company company = Company.getInstance();
		
		company.removeEmployee(e);
		addRedoCommand(this);
		
		
		
	}
	
	@Override
	public void redoMe() {
		// TODO Auto-generated method stub
		
		Company company = Company.getInstance();
		company.addEmployee(e);
		
		addUndoCommand(this);
	}
}
