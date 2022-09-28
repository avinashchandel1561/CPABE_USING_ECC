import java.util.ArrayList;

public class ExecutionMain {
	public static void main(String[] args) {
		ArrayList<String>attr=new ArrayList<>();
		attr.add("ComputerScince");
		attr.add("Studnet");
		//****************** GLOBAL INITIALIZATION PHASE STARTED ********************
		GlobalInitializationPhase sc=new GlobalInitializationPhase(1,1,97,attr);
		sc.HashUser(101);
		sc.setPP();
		ArrayList<Object>PP=sc.getPP();
		for(Object c:PP) {
			System.out.println(c);
		}
		//****************** GLOBAL INITIALIZATION PHASE COMPLETED ********************
	}
}
