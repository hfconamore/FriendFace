
public class PotentialFriend implements Comparable<PotentialFriend> {
	
	private String name;
	private Integer totalDist;
	
	
	public PotentialFriend(String name, int totalDist) {
		this.name = name;
		this.totalDist = totalDist;
		
	}

	public String getName() {
		return name;
	}
	
	public int getTotalDist() {
		return totalDist;
	}

	@Override
	public int compareTo(PotentialFriend o) {
		return this.totalDist.compareTo(o.totalDist);
	}


	@Override
	public String toString() {
		return name;
	}
	
}
