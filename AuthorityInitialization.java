import java.util.ArrayList;
import java.util.HashMap;

public class AuthorityInitialization {
	private ArrayList<Object>PP=new ArrayList<>();
	private HashMap<Integer,HashMap<Integer,ArrayList<String>>>AA=new HashMap<>();
	
	AuthorityInitialization(ArrayList<Object>PP){
		this.PP=PP;
		
	}
	void SetAttributes(int authId,int userId,ArrayList<String>attributes) {
		if(AA.containsKey(authId)) {
			AA.get(authId).put(userId, attributes);
		}
		else {
			HashMap<Integer,ArrayList<String>> temp=new HashMap<>();
			temp.put(userId,attributes);
			AA.put(authId,temp);
		}
	}
	
}
