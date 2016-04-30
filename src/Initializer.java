import java.io.*;

public class Initializer {
	public static void main(String args[]) throws Exception {
		PhotoFilter pf;
		try {
			pf = new PhotoFilter();
			pf.setlowbound(0);
			pf.setupbound(255);
			pf.setimbydict("C:/Users/user/Desktop/zhaopz.jpg");
			pf.setoutbydict("C:/Users/user/Desktop/zhaopp.jpg");
			/**
			File a=new File("C:/Users/user/Desktop/");
			File[] b=a.listFiles();
			for(File f:b){
				pf.setimbydict(f.getAbsolutePath());
				pf.setoutbydict("C:/Users/user/Desktop/zhaop3/"+1+f.getName());
				pf.transsin();
				pf.jiahei(1.5);
			}
			**/
			
			//pf.setoutbyname("changshi2");
			//pf.rotate180();
			//pf.setim(pf.filter());
			//pf.setkenel(new double[][] {{0,1/3.0,0},{1/3.0,-1/3.0,1/3.0},{0,1/3.0,0}});
			//pf.setim(pf.filter());
			//±ßÔµ¼ì²â1
			//pf.setkenel(new double[][] {{-1,-1,-1,-1,-1},{-1,-3,-3,-3,-1},{-1,-3,40,-3,-1},{-1,-3,-3,-3,-1},{-1,-1,-1,-1,-1}});
			//±ßÔµ¼ì²â2
			//pf.setkenel(new double[][] {{-1,-1,-1},{-1,8,-1},{-1,-1,-1}});
			//±ßÔµ¼ì²â3
			pf.setkenel(new double[][] {{-1,-1,-1,-1,-1,-1,-1},{-1,-3,-3,-3,-3,-3,-1},{-1,-3,-7,-7,-7,-3,-1},{-1,-3,-7,128,-7,-3,-1},{-1,-3,-7,-7,-7,-3,-1},{-1,-3,-3,-3,-3,-3,-1},{-1,-1,-1,-1,-1,-1,-1}});
			//¸¡µñ
			//pf.setkenel(new double[][] {{2,0,0},{0,-1,0},{0,0,-1}});
			//¸ßË¹Ä£ºý
			//pf.setkenel(new double[][] {{1,4,7,4,1},{4,16,26,16,4},{7,26,41,26,7},{4,16,26,16,4},{1,4,7,4,1}});
			//pf.atimekenel(1/273.0);
	
			
		
			
			pf.setim(pf.filter());
		
			//pf.dianzhentu3(10);
			//pf.dianzhentu(50,145);
			//pf.setim(pf.String_to_im(50,"SB"));
			//pf.dianzhentu(20, 255);
			//pf.picMix(pf.filter(), pf.im,1);
			
		
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
