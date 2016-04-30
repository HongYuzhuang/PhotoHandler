import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import javax.imageio.ImageIO;
class shape{
	LinkedList<pixl> ls;
	int id;
	float[] centercolor=new float[3];
	int counter;
	shape(pixl pp){
		ls=new LinkedList <pixl>() ;
		for(int i =0 ;i<3;i++){
		centercolor[i]=pp.color[i];
		counter=1;
		}
	}
	public void add(pixl ob){
		ls.add(ob);
		counter+=1;
		for(int i =0 ;i<3;i++){
			centercolor[i]=(float) ((counter-1.0)*centercolor[i]/counter+ob.color[i]/(float)counter);
		}
	}
	public void emerge(shape sp2){
		for(pixl k:sp2.ls){
			this.add(k);
		}
		
	}
}
class pixl{
	shape sp;
	boolean expended;
	int x,y;
	extractor et;
	int[] color=new int[3];
	
	pixl(extractor et){
		x=0;
		y=0;
		this.et=et;
		expended=false;
	}
	pixl(extractor et,int x,int y){
		this.et=et;
		this.x=x;
		this.y=y;
		et.im.getRaster().getPixel(x, y, color);
		expended=false;
	}
	public void setshape(shape e){
		this.sp=e;
	}
	public float distance(shape sp){
		float distance =0;
		for(int i=0;i<3;i++){
			distance+=Math.pow((color[i]-sp.centercolor[i]), 2.0)/3.0;
		}
		distance=(float) Math.pow(distance, 0.5);
		return distance;
	}
	public void expand(){
		sp.add(this);
		this.expended=true;
		
		
		if(x<et.w-1){
			pixl right=et.plls[x+1][y];
			if(right.expended==false){
				if( right.distance(sp)<et.val){
					right.setshape(sp);
					right.expand();	
				}
			}
		}

		if(y<et.h-1){
			pixl down=et.plls[x][y+1];
			if(down.expended==false){
				if( down.distance(sp)<et.val){
					down.setshape(sp);
					down.expand();	
				}
			}
		}
		

		
		
	}
}
class extractor{
	float val=(float) 50;
	File inf=new File("c:/users/user/desktop/test/zhaop2.jpg");
	BufferedImage im;
	File outf= new File("c:/users/user/desktop/test/zhaopz.jpg");
	int w,h;
	pixl[][] plls;
	int count=0;
	LinkedList <shape> a=new LinkedList<shape>();
	
	extractor(File f){
		inf=f;
		try {
			im=ImageIO.read(inf);
		} catch (IOException e) {
			System.out.print("wrong path");
		}
		w=im.getWidth(null);
		h=im.getHeight();
		plls=new pixl[w][h];
		for(int i=0;i<w;i++){
			for(int j=0;j<h;j++){
				plls[i][j]=new pixl(this,i,j);
			}
		}

	}
	extractor(){
		try {
			im=ImageIO.read(inf);
		} catch (IOException e) {
			System.out.print("wrong path");
		}
		w=im.getWidth(null);
		h=im.getHeight();
		plls=new pixl[w][h];
		for(int i=0;i<w;i++){
			for(int j=0;j<h;j++){
				plls[i][j]=new pixl(this,i,j);
			}
		}

	}
	public float distance(shape sp1,shape sp2){
		float distance =0;
		for(int i=0;i<3;i++){
			distance+=Math.pow((sp2.centercolor[i]-sp1.centercolor[i]), 2.0)/3.0;
		}
		distance=(float) Math.pow(distance, 0.5);
		return distance;
	}
	public void write(){
		
	}
	public void sampleextract(){
		pixl a=plls[0][0];
		shape k=new shape(a);
		a.setshape(k);
		a.expand();
		this.a.add(k);
	}
	public void clean(){
		while(true){
			for(shape i:a){
				if(i.ls.size()<500){
					a.remove(i);
					break;
				}
			}
			break;
		}
	}
	public void emerge(){
		Boolean none=true;
		int counter2=0;
		int lid=a.getLast().id;
		while(none==true){
			shape k1=a.getFirst();
			Iterator<shape> it=a.iterator();
			Boolean bl2=true;
			while(bl2==true){
				if(it.hasNext()){
					
					shape k=it.next();
					if(k.equals(k1)==false && this.distance(k1,k)<val){
						counter2+=1;
						k1.emerge(k);
						k1.id=k.id;
						a.remove(k);
						bl2=false;
						System.out.println("emerged"+counter2);
					}
				}
				else if(bl2==true){
					a.addLast(a.removeFirst());
					if(a.getFirst().id==lid){
						none=false;
						System.out.println("end");
					}
					bl2=false;
				}
				
			}
		}
		clean();
	}
	public void ot() throws IOException{
		BufferedImage wt=new BufferedImage(this.w,this.h, BufferedImage.TYPE_INT_RGB);
		Iterator<shape> it=a.iterator();
		Integer counter=(Integer) 0;
		while(it.hasNext()==true){
			shape k=(shape) it.next();
			counter+=1;
			for(pixl pl:k.ls){
				wt.getRaster().setPixel(pl.x, pl.y, k.centercolor);
				
				
			}
		}
	
		FileOutputStream w1=new FileOutputStream(this.outf);
		ImageIO.write(wt,"jpg",w1 );
		
	}
	public void extract() throws IOException{
		for(int i =0;i<w;i++){
			for(int j = 0;j<h;j++){
				pixl temp=plls[i][j];
				if(temp.expended==false){
					this.count+=1;
					shape k=new shape(temp);
					k.id=count;
					temp.setshape(k);
					temp.expand();
					a.add(k);
				}	
			}
		}
		clean();
		emerge();
		ot();
	}
	
}
public class shapeExtractor {

	public static void main(String[] args) throws IOException, AWTException {
		
		File inf=new File("c:/users/user/desktop/test/zhaop3.jpg");
		extractor a=new extractor(inf);
		a.extract();
		
	}

}
