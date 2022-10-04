import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class ECC{
	int a,b,q;
	HashMap<Pair,Pair> pt=new HashMap<>();
	HashMap<Pair,Pair> subgrouppt=new HashMap<>();
//	HashMap<Integer,Integer>inverses=new HashMap<>();
	ArrayList<Integer>randomVal=new ArrayList<>();
	private final String keyHash="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	ECC(int a,int b,int q){
		this.a=a;
		this.b=b;
		this.q=q;
	}
	
	
	void EccPointGen() {
		int x=0;
		while(x<q) {
			int xc=(int)Math.pow(x, 3);
			int w=(xc+(a*x)+b)%q;
			int wr=find_sq(w);
			if(!(wr==0 || (q-wr)==0)) {
				
			if(wr!=Integer.MAX_VALUE && !(pt.containsKey(new Pair(x,wr)) || pt.containsKey(new Pair(x,(q-wr)%q))) ) {
				pt.put(new Pair(x,wr),new Pair(x,(q-wr)%q));
			}
			}
			x++;
		}
	}
	
	
	private int find_sq(int w) {
		
		for(int i=0;i<q;i++) {
			if((i*i)%q==w) {
				return i;
			}
		}
		return Integer.MAX_VALUE;
	}
	
	
	void PrintMap(HashMap<Pair,Pair> pt) {
		for(Entry<Pair, Pair> e:pt.entrySet()) {
			System.out.println("{"+e.getKey().x+" , "+e.getKey().y+"}  "+"{"+e.getValue().x+" , "+e.getValue().y+"}  ");
		}
	}
	
	
	
	Pair findBasePoint() {
		Pair bp,bp1;
		
		int n=2*pt.size() + 1;
		ArrayList<Integer>factors=new ArrayList<>();
		generatePrimeFactors(n,factors);
		for(int index=0;index<factors.size();index++) {
			int sbsize=factors.get(factors.size()-1-index);
			System.out.println(sbsize);
			for(Entry<Pair,Pair> e:pt.entrySet()) {
				bp=e.getKey();
				bp1=e.getValue();
//				System.out.println("BP : "+bp.x+" "+bp.y);
//				System.out.println("BP1 : "+bp1.x+" "+bp1.y);
				if(findBasePointUtility(bp,sbsize)) {
					return bp;
				}
				if(findBasePointUtility(bp1,sbsize)) {
					return bp1;
				}
			}
		}
		
		
		
		return new Pair(Integer.MAX_VALUE,Integer.MAX_VALUE);
	}
	
	boolean findBasePointUtility(Pair bp,int n) {
		
		Pair d=bp;
//		subgrouppt.put(d,new Pair(d.x,(q-d.y)%q));
//		randomVal.add(1);
		for(int i=2;i<=n;i++) {
			d=add(d,bp);
//			System.out.println("D : "+d.x + " "+d.y);
			if(d.y!=0 && (q-d.y)%q !=0) {
//				System.out.println("NOt zero"+pt.size());
				
				if(checkKey(d,pt)) {
//					System.out.println("Exist in PT");
					randomVal.add(i);
					if(!checkKey(d,subgrouppt)) {
//						System.out.println("Adding");
						subgrouppt.put(d,new Pair(d.x,(q-d.y)%q));
					}
				}
			}
		}
//		System.out.println("Subgroup SIze : "+subgrouppt.size());
		if(subgrouppt.size()==(n-1)/2) {
			return true;
		}
		subgrouppt.clear();
		randomVal.clear();
		return false;
	}
	
	boolean checkKey(Pair bp,HashMap<Pair,Pair>hm) {
		for(Entry <Pair,Pair> ent : hm.entrySet() ) {
			Pair k=ent.getKey();
			Pair val=ent.getValue();
			if(bp.x==k.x && bp.y==k.y) {
				return true;
			}
			if(bp.x==val.y && bp.y==val.y) {
				return true;
			}
		}
		return false;
	}
	 Pair add(Pair p,Pair d) {
		if(p.x==d.x && p.y==d.y) {
			
			int temp0= p.x * p.x;
			temp0*=3;
			temp0+=a;
			temp0=temp0%q;
			int temp1=(2*p.y);
			int temp1_inv= find_inverse(q,temp1);
			int lmbda=temp0*temp1_inv;
			lmbda=lmbda%q;
			
			int l2=(lmbda*lmbda);
			int x3=l2-p.x-d.x;
			x3=x3%q;
			if(x3<0) {
				x3=findPos(x3);
			}
			int temp2=p.x-x3;
			int y3=lmbda*temp2;
			y3-=p.y;
			y3=y3%q;
			if(y3<0) {
				y3=findPos(y3);
			}
//			System.out.println(temp1_inv);
			return new Pair(x3,y3);
		}
		else {
			int temp0=d.y-p.y;
			temp0=temp0%q;
			if(temp0<0) {
				temp0=findPos(temp0);
			}
			int temp1=d.x-p.x;
			temp1=temp1%q;
			if(temp1<0) {
				temp1=findPos(temp1);
			}
			int temp1_inv=find_inverse(q,temp1);
			int lmbda=temp0*temp1_inv;
			lmbda=lmbda%q;
			
			int l2=lmbda*lmbda;
			int x3=l2-p.x-d.x;
			x3=x3%q;
			if(x3<0) {
				x3=findPos(x3);
			}
			int temp2=p.x-x3;
			int y3=lmbda*temp2;
			y3=y3-p.y;
			y3=y3%q;
			if(y3<0) {
				y3=findPos(y3);
			}
			return new Pair(x3,y3);
			
		}
	}
	
	private int find_inverse(int q , int num) {
		int r1=q,r2=num;
		int t1=0,t2=1;
		int r,t;
		while(r2>0) {
			q=r1/r2;
			r=r1-(q*r2);
			r1=r2;
			r2=r;
			
			t=t1-(q*t2);
			t1=t2;
			t2=t;
		}
		if(r1==1) {
			if(t1<0)
				return findPos(t1);
			return t1;
		}
		return Integer.MAX_VALUE;
	}
	 int findPos(int n) {
		while(n<0) {
			n +=q;
		}
		return n;
	}
	
	
	private int findMax(ArrayList<Integer> factors) {
		int max=Integer.MIN_VALUE;
		for(int i=0;i<factors.size();i++) {
			max=Math.max(max, factors.get(i));
		}
		return max;
	}
	
	
	private void generatePrimeFactors(int n,ArrayList<Integer> factors){
		 int c = 2;
	        while (n > 1) {
	            if (n % c == 0) {
	            	if(!factors.contains(c))
	                factors.add(c);
	                n /= c;
	            }
	            else
	                c++;
	        }
	}
	
	
	private boolean isPrime(int n)
    {
 
        if (n <= 1)
            return false; 
        else if (n == 2)
            return true;       
        else if (n % 2 == 0)
            return false;       
        for (int i = 3; i <= Math.sqrt(n); i += 2)
        {
            if (n % i == 0)
                return false;
        }
        return true;
    }
	
	Pair textToPoint(String text) {
		Pair p=new Pair(Integer.MAX_VALUE,Integer.MAX_VALUE);
		int M=0;
		for(int i=0;i<text.length();i++) {
			M=M+keyHash.indexOf(text.charAt(i));
//			System.out.println(M);
		}
		int k=10;
		for(;(M+1)*k < q;k++) {
			
		}
//		System.out.println(M+"  K= "+k);
		int x,y;
		int tempk=k;
		while(k>10) {
		for(int j=0;j<k;j++) {
			x=((M*k)+j)%q;
			int xc=(int)Math.pow(x, 3);
			int w=(xc+(a*x)+b)%q;
			int wr=find_sq(w);
			if(!(wr==0 || (q-wr)==0)) {
				if(wr!=Integer.MAX_VALUE)
				{
//					System.out.println(k);
					return new Pair(x,wr);
				}
			
			}
		}
		k--;
		}
		return p;
	
}
}
class Pair{
	int x,y;
	Pair(int x,int y){
		this.x=x;
		this.y=y;
	}
}
