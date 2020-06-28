
public class LeaveRecord implements Comparable<LeaveRecord>{
	private Day startDay;
	private Day endDay;
	public LeaveRecord(Day s, Day e) {
		this.startDay=s;
		this.endDay=e;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(startDay);
		sb.append(" to ");
		sb.append(endDay);
		return sb.toString();
		
	}
	public int countDays() {
		if(startDay.getMonth()==endDay.getMonth()) {
			int difference =endDay.getAsInt()-startDay.getAsInt()+1;
			return difference;
		}
		else {
			
			int difference = startDay.daysInMonth()+endDay.getDay()-startDay.getDay()+1;
			return difference;
		}
		
	}
	public boolean isOverlapped(Day d1,Day d2) {
		boolean flag=false;
		
		if(startDay.isBetween(d1, d2)||endDay.isBetween(d1, d2)) {
			flag=true;
		}
		return flag;
	}
	@Override
	public int compareTo(LeaveRecord another)
	{
	return this.startDay.compareTo(another.getStartDay());
	
	}
	public Day getStartDay() {
		return startDay;
	}
	public Day getEndDay() {
		return endDay;
	}
	
}
