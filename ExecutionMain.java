import java.util.ArrayList;

public class ExecutionMain {
	public static void main(String[] args) {
		ArrayList<String>attr=new ArrayList<>();
		attr.add("ComputerScince");
		attr.add("Studnet");
		AccessPolicy ap=new AccessPolicy();
		//****************** GLOBAL INITIALIZATION PHASE STARTED ********************
		GlobalInitializationPhase sc=new GlobalInitializationPhase(7,17,2969,attr);
		sc.HashUser(101);
		sc.setPP();
		ArrayList<Object>PP=sc.getPP();
//		for(Object c:PP) {
//			System.out.println(c);
//		}
		ECC er=(ECC) PP.get(2);
		System.out.println("*******************************************************");
		er.PrintMap(er.pt);
		//****************** GLOBAL INITIALIZATION PHASE COMPLETED ********************
		
		//****************** AUTHORITY INITIALIZATION PHASE STARTED ********************
		AuthorityInitialization ai=new AuthorityInitialization(PP);
		ai.setAttributes(1, 101, attr);
		ai.generateKeys(1);
//		ai.printKeys();
		//****************** AUTHORITY INITIALIZATION PHASE COMPLETED ********************
		
		//****************** DATA ENCRYPTION PHASE STARTED ********************
		
		String MESSAGE="AVINASH CHANDEL WELCOME TO NIT ROURKELA ";
		DataEncryption de=new DataEncryption(PP);
		de.EncryptMessage(MESSAGE);
		System.out.println("ENCRYPTED DATA USING AES : "+de.getCTDATA());
		de.createHCT();
		System.out.println(" HCDATA : "+de.getHCTDATA());
		System.out.println(" HCDATA : "+de.getHCT().x + " "+de.getHCT().y);
		de.cpabeEnc();
		
		
	}
}
