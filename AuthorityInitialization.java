import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AuthorityInitialization {
	private ArrayList<Object>PP=new ArrayList<>();
	private HashMap<Integer,HashMap<Integer,ArrayList<String>>>AA=new HashMap<>();// AuthID -> (UserID -> Attribute_list)
	private HashMap<Integer,HashMap<Integer,HashMap<Integer,ArrayList<Pair>>>> MSK_PK=new HashMap<>(); // AuthID ->( UserID -> (AttrID ->MSK_PK_PAIR))
	
	AuthorityInitialization(ArrayList<Object>PP){
		this.PP=PP;
		
	}
	void setAttributes(int authId,int userId,ArrayList<String>attributes) {
		if(AA.containsKey(authId)) {
			AA.get(authId).put(userId, attributes);
		}
		else {
			HashMap<Integer,ArrayList<String>> temp=new HashMap<>();
			temp.put(userId,attributes);
			AA.put(authId,temp);
		}
	}
	void generateKeys(int authId) {
		
		if(!MSK_PK.containsKey(authId)) {
			HashMap<Integer,HashMap<Integer,ArrayList<Pair>>> Hashtu=new HashMap<>();
			for(Map.Entry<Integer, HashMap<Integer,ArrayList<String>>> e : AA.entrySet()) {
				HashMap<Integer,ArrayList<String>> tempHash=e.getValue();
				for(Map.Entry<Integer, ArrayList<String>> te : tempHash.entrySet()) {
					int tId=te.getKey();
					int tattr=te.getValue().size();
					HashMap<Integer,ArrayList<Pair>> Hashta=new HashMap<>();
					for(int ta=0;ta<tattr;ta++) {
						ArrayList<Pair> temp0=new ArrayList<>();
						ECC tempec=(ECC) PP.get(2);
						Random rand=new Random();
						int y=rand.nextInt(tempec.randomVal.size());
						int k=rand.nextInt(tempec.randomVal.size());
						Pair MSK=new Pair(y,k);
						Pair yG=(Pair) PP.get(1);
						Pair bp=(Pair) PP.get(1);
						for(int i=2;i<=y;i++) {
							yG=tempec.add(yG,bp);
						}
						bp=(Pair) PP.get(1);
						Pair kG=(Pair) PP.get(1);
						for(int i=2;i<=k;i++) {
							kG=tempec.add(kG, bp);
						}
						temp0.add(MSK);
						temp0.add(yG); // PK
						temp0.add(kG); // PK
						
						Hashta.put(ta,temp0);
					}
					
					Hashtu.put(tId, Hashta);
				}
				
			}
		
			
			MSK_PK.put(authId, Hashtu);
		}
	}
	
	void printKeys() {
		for(Map.Entry<Integer,HashMap<Integer,HashMap<Integer,ArrayList<Pair>>>> e1:MSK_PK.entrySet()) {
			System.out.print("AuthID : "+e1.getKey());
			for(Map.Entry<Integer, HashMap<Integer,ArrayList<Pair>>> e2:e1.getValue().entrySet()) {
				System.out.print("  UserId : "+e2.getKey());
				for(Map.Entry<Integer, ArrayList<Pair>> e3 : e2.getValue().entrySet()) {
					System.out.println("Attribute : "+e3.getKey());
					for(int i=0;i<e3.getValue().size();i++) {
						System.out.println(e3.getValue().get(i).x + " "+e3.getValue().get(i).y);
					}
				}
			}
		}
	}
}
