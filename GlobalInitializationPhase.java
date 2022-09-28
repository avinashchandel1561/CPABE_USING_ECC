import java.util.ArrayList;
import java.util.HashMap;

public class GlobalInitializationPhase {
	private ArrayList<Object>PP=new ArrayList<>();
	private int a,b,q;
	private ECC ec;
	private Pair bp;
	private ArrayList<String>attributes=new ArrayList<>();
	private HashMap<Integer,Integer>H=new HashMap<>();
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
		ec.PrintMap(ec.pt);
		System.out.println(" Base Point : "+bp.x + " , "+ bp.y);
		ec.PrintMap(ec.subgrouppt);
	}
	void HashUser(int id) {
		countLatest++;
		H.put(countLatest, id);
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
