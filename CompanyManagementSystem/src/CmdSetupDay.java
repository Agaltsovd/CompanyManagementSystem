

public class CmdSetupDay extends RecordedCommand{

	private String stringDate ;
	@Override
	
	public void execute(String[] cmdParts) {

		SystemDate systemDate= SystemDate.getInstance();
		//
		String s =systemDate.toString();
		stringDate=cmdParts[1];
		
		systemDate.createTheInstance(stringDate);//We either creating new SystemDate or change it
		stringDate=s;
		addUndoCommand(this);
		clearRedoList();
		System.out.println("Done.");
		
	}

	@Override
	public void undoMe() {

		SystemDate systemDate= SystemDate.getInstance();
		systemDate.createTheInstance(stringDate);
		addRedoCommand(this);
		
	}

	@Override
	public void redoMe() {

		SystemDate systemDate= SystemDate.getInstance();
		systemDate.createTheInstance(stringDate);
		
		
		addUndoCommand(this);
	}

}
