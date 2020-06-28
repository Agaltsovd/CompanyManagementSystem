
public abstract class Role implements Comparable<Role> {

private Team team;
public Role(Team t) {
	this.team=t;
}
public abstract String getRole();
public  Team getTeam() {
	return this.team;
}
@Override
public int compareTo(Role another)
{
return getTeam().compareTo(another.getTeam());

}
}
