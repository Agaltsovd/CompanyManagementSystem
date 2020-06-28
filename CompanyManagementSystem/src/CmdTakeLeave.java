
public class CmdTakeLeave extends RecordedCommand {
	private Day leaveStart;
	private Day leaveEnd;
	private Employee e;
	private LeaveRecord leaveRecord;
	private Team team1;
	private Team team2;
	private Employee sub1;
	private Employee sub2;
	private RActingHead actingHead1;
	private RActingHead actingHead2;
	
	@Override
	public void execute(String[] cmdParts) {
		try { 
			
			Company company =Company.getInstance();
			e=company.findEmployee(cmdParts[1]);
			leaveStart= new Day(cmdParts[2]);
			leaveEnd=new Day(cmdParts[3]);
			
			
			leaveRecord = new LeaveRecord(leaveStart,leaveEnd);
			
			
			
			if(cmdParts.length==6) {
			
				team1=company.findTeam(cmdParts[4]);
				
				sub1=company.findEmployeeForTeam(cmdParts[5], team1);
				e.isHeadOfMoreThanOneTeam(team1);
				actingHead1= new RActingHead(team1,leaveRecord, sub1);
				team1.addActingHead(actingHead1);
				
				
			}
			else if(cmdParts.length==8) {
				team1=company.findTeam(cmdParts[4]);
				team2=company.findTeam(cmdParts[6]);
				sub1=company.findEmployeeForTeam(cmdParts[5], team1);
				sub2=company.findEmployeeForTeam(cmdParts[7], team2);
				
				actingHead1= new RActingHead(team1,leaveRecord, sub1);
				team1.addActingHead(actingHead1);
				
				actingHead2= new RActingHead(team2,leaveRecord, sub2);
				team2.addActingHead(actingHead2);
			}
//			checkIfDayPassed(leaveStart);
//			isOverlapped(leaveStart,leaveEnd);
			e.addLeaveRecord(leaveRecord);
//			this.enoughBalance(leaveRecord);
			addUndoCommand(this);
			clearRedoList();
			System.out.println("Done.");
		} catch (ExDateHasAlreadyPassed e) {
			
			System.out.println(e.getMessage());
		} catch (ExOverlappedLeaves e) {
			System.out.println(e.getMessage());
		} catch (ExInsufficientBalance e) {
			System.out.println(e.getMessage());
		} catch (ExEmployeeNotFound e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (ExInsufficientArguments e) {
			
			System.out.println(e.getMessage());
		}
		
		
	}

	@Override
	public void undoMe() {
		
		e.removeLeaveRecord(leaveRecord);
		if(actingHead1!=null) {
			team1.removeActingHead(actingHead1);
		}
		if(actingHead2!=null) {
			team2.removeActingHead(actingHead2);
		}
		addRedoCommand(this);
		
		
		
	}

	@Override
	public void redoMe() {
		try {
			e.addLeaveRecord(leaveRecord);
			
			if(actingHead1!=null) {
				team1.addActingHead(actingHead1);
			}
			if(actingHead2!=null) {
				team2.addActingHead(actingHead2);
			}
			
		} catch (ExInsufficientBalance e) {
			System.out.println(e.getMessage());
		}
		catch (ExOverlappedLeaves e) {
			System.out.println(e.getMessage());
		}
		catch (ExEmployeeNotFound e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (ExDateHasAlreadyPassed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addUndoCommand(this);
		
	}
//	public void checkIfDayPassed(Day d) throws ExDateHasAlreadyPassed {
//		if(d.compareTo(SystemDate.getInstance())==1) {
//			return;
//		}
//		else if(d.compareTo(SystemDate.getInstance())==-1) {
//			throw new ExDateHasAlreadyPassed("Wrong Date. System date is already "+SystemDate.getInstance()+"!");
//		}
//	}
//	public void isOverlapped(Day start,Day end ) throws ExOverlappedLeaves {
//		for(LeaveRecord lr: e.getLeaveRecords()) {
//			if((lr.getStartDay().less(start)&&lr.getEndDay().more(start))||(lr.getStartDay().less(end)&&lr.getEndDay().more(end))) {
//				throw new ExOverlappedLeaves("Leave overlapped: The leave period "+lr+" is found!");
//			}
//		}
//	}
//	public void enoughBalance(LeaveRecord lr) throws ExInsufficientBalance {
//		if(lr.countDays()>e.getRemainingLeaves())
//			throw new ExInsufficientBalance("Insufficient balance. "+e.getRemainingLeaves()+" days left only!");
//	}
}
