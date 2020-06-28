import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, ExWrongCommand {

		Scanner in = new Scanner(System.in);

		System.out.print("Please input the file pathname: ");
		String filepathname = in.nextLine();
		
		Scanner inFile = null;
		try {
			inFile = new Scanner(new File(filepathname));
			
			//The first command in the file must be to set the system date 
			//(eg. "startNewDay|01-Jan-2020"); and it cannot be undone
			try {
				String cmdLine1 = inFile.nextLine();
				String[] cmdLine1Parts = cmdLine1.split("\\|");
				System.out.println("\n> "+cmdLine1);
				SystemDate.createTheInstance(cmdLine1Parts[1]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			while (inFile.hasNext())		
			{
				String cmdLine = inFile.nextLine().trim();
				
				//Blank lines exist in data file as separators.  Skip them.
				if (cmdLine.equals("")) continue;  

				System.out.println("\n> "+cmdLine);
				
				// split the words in actionLine => create an array of word strings
				// http://stackoverflow.com/questions/5675704/java-string-split-not-returning-the-right-values
				// https://community.oracle.com/thread/2084308
				String[] cmdParts = cmdLine.split("\\|"); 
				
				if(cmdParts[0].equals("startNewDay")) {
					(new CmdSetupDay()).execute(cmdParts);

				}
				else if(cmdParts[0].equals("takeLeave")) {
					(new CmdTakeLeave()).execute(cmdParts);

				}
				else if(cmdParts[0].equals("setupTeam")) {
					(new CmdTeamSetup()).execute(cmdParts);

				}
				else if (cmdParts[0].equals("hire")) {
					(new CmdHire()).execute(cmdParts);
				}
				else if (cmdParts[0].equals("listLeaves")) {
					(new CmdListLeaves()).execute(cmdParts);
				}
				else if (cmdParts[0].equals("listEmployees")) {
					(new CmdListEmployees()).execute(cmdParts);
				}
				else if (cmdParts[0].equals("listTeams")) {
					(new CmdListTeams()).execute(cmdParts);
			
				}
				else if (cmdParts[0].equals("listTeamMembers")) {
					(new CmdListTeamMembers()).execute(cmdParts);
			
				}
				else if (cmdParts[0].equals("listTeams")) {
					(new CmdListTeams()).execute(cmdParts);
			
				}
				else if (cmdParts[0].equals("listRoles")) {
					(new CmdListRoles()).execute(cmdParts);
			
				}
				else if (cmdParts[0].equals("addTeamMember")) {
					(new CmdAddTeamMember()).execute(cmdParts);
			
				}
				
				else if (cmdParts[0].equals("undo")) {
					RecordedCommand.undoOneCommand();

					
				}
				else if (cmdParts[0].equals("redo")) {
					RecordedCommand.redoOneCommand();

				}
				
				else throw new ExWrongCommand();
			}
		} catch (FileNotFoundException e) {
			/
			e.printStackTrace();
		}
			catch (InputMismatchException e) {
				System.out.println("File content problem. Program terminated.");}
		 catch (ExWrongCommand e) {
		
			e.printStackTrace();
		}
		finally {
			if (inFile!=null) //If it has been opened successfully, close it
			inFile.close();
			in.close();
		
	}
}
}
