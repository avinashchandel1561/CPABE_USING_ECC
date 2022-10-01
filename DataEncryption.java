import java.util.ArrayList;

public class DataEncryption {
	private ArrayList<Object>PP=new ArrayList<>();
	private AesEncryption aes;
	private String CTDATA;
	private int HCDATA;
	private Pair HCT;
	DataEncryption(ArrayList<Object>PP){
		aes=new AesEncryption();
		this.PP=PP;
	}
	void EncryptMessage(String MESSAGE) {
		CTDATA=aes.encrypt(MESSAGE);
	}
	String getCTDATA() {
		return CTDATA;
	}
	
	void createHCT() {
		int sum=0;
		for(int i=0;i<CTDATA.length();i++) {
//			System.out.print((int)CTDATA.charAt(i)+" ");
			sum+=CTDATA.charAt(i);
		}
//		System.out.println(sum);
		ECC ee=(ECC) PP.get(2);
		int val=sum % ee.randomVal.size();
		HCDATA=ee.randomVal.get(val);
		
		HCT=(Pair)PP.get(1);
		Pair p=(Pair) PP.get(1);
		for(int i=2;i<=HCDATA;i++) {
			HCT=ee.add(HCT, p);
		}
	}
	
	int getHCTDATA() {
		return HCDATA;
	}
	
	Pair getHCT() {
		return HCT;
	}
	
}
