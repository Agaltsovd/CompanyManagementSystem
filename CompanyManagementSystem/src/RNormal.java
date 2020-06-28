
public class RNormal extends Role{
	
	
	public RNormal( Team t) {
		
		super(t);
	}
	@Override
	public String getRole() {
		return"";
	}

	
	@Override
	public String toString() {
		return this.getTeam().getName()+getRole();
	}

}
