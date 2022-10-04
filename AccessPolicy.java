import java.util.Scanner;

public class AccessPolicy {
	int m[][]= {
			{0,-1,0,0,0},
			{0,0,0,-1,0},
			{1,1,0,1,0},
			{0,0,0,0,-1},
			{1,1,0,0,1},
			{0,0,-1,0,0},
			{0,0,-1,0,0},
			{1,1,1,0,0},
			{1,1,1,0,0}
	};
	int l,n;
	int r[]= {'E','A','B','C','D','A','B','C','D'};
	AccessPolicy(){
		l=m[0].length;
		n=m.length;
	}
	
}
