import java.util.ArrayList;
import java.util.HashMap;

public class GlobalInitializationPhase {
	private ArrayList<Object>PP=new ArrayList<>();
	private int a,b,q;
	private ECC ec;
	private Pair bp;
	private ArrayList<String>attributes=new ArrayList<>();
	private HashMap<Integer,ArrayList<Integer>>H=new HashMap<>();
	private int countLatest=0;
	GlobalInitializationPhase(int a,int b, int q,ArrayList<String>attr){
		this.a=a;
		this.b=b;
		this.q=q;
		this.ec=new ECC(a,b,q);
		this.attributes=attr;
//		for(String a1: attributes) {
//			System.out.println(a1);
//		}
		ec.EccPointGen();
		bp =ec.findBasePoint();
//		ec.PrintMap(ec.pt);
		System.out.println(" Base Point : "+bp.x + " , "+ bp.y);
		ec.PrintMap(ec.subgrouppt);
	}
	void HashUser(int id) {
		int val=id % ec.randomVal.size();
		int hashId=ec.randomVal.get(val);
		if(!H.containsKey(hashId)) {
			ArrayList<Integer>tempuser=new ArrayList<>();
			tempuser.add(id);
			H.put(hashId, tempuser);
		}
		else {
			ArrayList<Integer>tempuser=H.get(hashId);
			tempuser.add(id);
			H.put(hashId,tempuser);
		}
	}
	void setPP() {
		PP.add(ec.subgrouppt);
		PP.add(bp);
		PP.add(ec);
		PP.add(attributes);
		PP.add(H);
	}
	ArrayList<Object> getPP() {
		return PP;
	}
}
