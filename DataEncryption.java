import java.util.ArrayList;
import java.util.Random;

public class DataEncryption {
	private ArrayList<Object>PP=new ArrayList<>();
	private AesEncryption aes;
	private String CTDATA;
	private int HCDATA;
	private Pair HCT,C0,CKPOINT;
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
	
	void cpabeEnc() {
		int sindex,s;
		ECC ee=(ECC)PP.get(2);
		CKPOINT=ee.textToPoint(aes.getSecretKey());
		System.out.println("CKPOINT : "+CKPOINT.x + " "+CKPOINT.y);
		Random rand=new Random();
		do {
		 sindex=rand.nextInt(ee.randomVal.size());
		 s=ee.randomVal.get(sindex);
		Pair sG=(Pair) PP.get(1);
		Pair G=(Pair) PP.get(1);
		for(int i=2;i<=s;i++) {
			sG=ee.add(sG,G);
		}
//		System.out.println("sG : "+sG.x + " "+sG.y);
		
		C0=ee.add(CKPOINT, sG);
//		System.out.println("C0 : "+C0.x + " "+C0.y);
		}while(! ee.checkKey(C0, ee.pt));
		
		System.out.println("C0 : "+C0.x + " "+C0.y + " S : "+s);
		
		AccessPolicy ap=new AccessPolicy();
		int rvsize=ap.l;
		int rvindex=rand.nextInt(ap.n);
		int ruindex=rand.nextInt(ap.n);
		int v[]=new int[rvsize];
		int u[]=new int[rvsize];
		u[0]=0 %ee.q;
		v[0]=s % ee.q;
		for(int i=1;i<rvsize;i++) {
			v[i]=ap.m[rvindex][i] % ee.q;
			if(v[i]<0) {
			v[i]=ee.findPos(v[i]);
			}
		}
		for(int i=1;i<rvsize;i++) {
			u[i]=ap.m[ruindex][i] % ee.q;
			if(u[i]<0) {
				u[i]=ee.findPos(u[i]);
				}
		}
		for(int i=0;i<v.length;i++)
		System.out.print(v[i]+" ");
		for(int i=0;i<v.length;i++)
			System.out.print(u[i]+" ");
		
	}
	
}
