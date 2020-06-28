
public class RHead extends Role{
	
	
	public RHead( Team t) {
		
		super(t);
	}
	@Override
	public String getRole() {
		String s=" (Head of Team)";
		return s;
	}



	@Override
	public String toString() {
		return this.getTeam().getName()+getRole();
	}

}
