import java.io.*;

import javax.imageio.ImageIO;

import org.w3c.dom.css.RGBColor;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

class photo{
	File file;
	BufferedImage im;
	BufferedImage wt;
	int w;
	int h;
	double[][] kenel;
	
	public void spkenel(){
		//double[][] spkenel={{-0.25,-0.5,-0.25},{-0.5,4,-0.5},{-0.25,-0.5,-0.25}};
		//double[][] spkenel={{-1,-1.0,-1.0},{-1.0,9,-1.0},{-1,-1.0,-1.0}};
		//double[][] spkenel={{-0.125,-0.125,-0.125,-0.125,-0.125}
	      //                ,{-0.125,-0.25,-0.25,-0.25,-0.125}
	        //              ,{-0.125,-0.25,5,-0.25,-0.125}
	          //            ,{-0.125,-0.25,-0.25,-0.25,-0.125}
	            //          ,{-0.125,-0.125,-0.125,-0.125,-0.125}};
		//double[][] spkenel={{-0.5,-0.5,-1,-0.5,-0.5}
          //                 ,{-0.5,-1,2,-1,-0.5}
            //               ,{-1,2,7,2,-1}
              //             ,{-0.5,-1,2,-1,-0.5}
                //           ,{-0.5,-0.5,-1,-0.5,-0.5}};
		double a=1.025;
		double[][] spkenel={{-0.5*a,-0.5*a,-0.5*a,-0.5*a,-0.5*a,-0.5,-0.5*a},
				             {-0.5*a, 1*a , 1*a , 1*a , 1*a , 1*a ,-0.5*a},
				             {-0.5*a, 1*a ,  -1*a ,  -1*a ,  -1*a , 1*a ,-0.5*a},
				             {-0.5*a, 1*a ,  -1*a , 4*a+1,  -1*a , 1*a ,-0.5*a},
				             {-0.5*a, 1*a ,  -1*a ,  -1*a ,  -1*a , 1*a ,-0.5*a},
				             {-0.5*a, 1*a , 1*a , 1*a , 1*a , 1*a ,-0.5*a},
				             {-0.5*a,-0.5*a,-0.5*a,-0.5*a,-0.5*a,-0.5*a,-0.5*a}};
		                                                  
		//double[][] spkenel={{-1.0,-1.0,-1.0,-1.0,0},{-1.0,-1.0,-1.0,0,1},{-1.0,-1,0,1.0,1.0},{-1,0,1,1.0,1.0},{0,1.0,1.0,1,1}};
		kenel=spkenel;
        }
		
	
	photo() throws IOException{
		spkenel();
		int a=(kenel.length)-1;
		file=new File("C:/Users/user/Desktop","zhaop1.jpg");
		im=ImageIO.read(file);
		w=im.getWidth(null);
		h=im.getHeight(null);		
		wt=new BufferedImage(w+a,h+a, BufferedImage.TYPE_INT_RGB);		
		int alpha[][],red[][],green[][],blue[][],cRGB[][];
		alpha=new int[w+a][h+a];
		red=new int[w+a][h+a];
		green=new int[w+a][h+a];
		blue=new int[w+a][h+a];
		cRGB=new int[w+a][h+a];
		
		for(int j=0;j<h+a;j++){
			for(int i=0;i<w+a;i++){
				alpha[i][j]= 0; 
				red [i][j]= 0;
			    green[i][j] = 0;
				blue[i][j] = 0;
			}
		
		}
		for(int j=a/2;j<h+a/2;j++){
			for(int i=a/2;i<w+a/2;i++){
				alpha[i][j]= (im.getRGB(i-a/2, j-a/2)>> 24)& 0xff; 
				red [i][j]= (im.getRGB(i-a/2, j-a/2)>> 16) &0xff;
			    green[i][j] = (im.getRGB(i-a/2, j-a/2)>> 8) &0xff;
				blue[i][j] = im.getRGB(i-a/2, j-a/2)& 0xff;			
			}
		}
		
		int sum1[][]=new int[w][h],sum2[][]=new int[w][h],sum3[][]=new int[w][h],sum4[][]=new int[w][h];
		for(int j=a/2;j<h+a/2;j++){
			for(int i=a/2;i<w+a/2;i++){
				
				sum2[i-a/2][j-a/2]=0;
				sum3[i-a/2][j-a/2]=0;
				sum4[i-a/2][j-a/2]=0;
				
				for(int z=0;z<a+1;z++){
					for(int k=0;k<a+1;k++){
						sum2[i-a/2][j-a/2]+=red[i+a/2-z][j+a/2-k]*kenel[z][k];
						sum3[i-a/2][j-a/2]+=green[i+a/2-z][j+a/2-k]*kenel[z][k];
						sum4[i-a/2][j-a/2]+=blue[i+a/2-z][j+a/2-k]*kenel[z][k];
						
						
					}
				}
			}
		}
		
		for(int j=a/2;j<h+a/2;j++){
			for(int i=a/2;i<w+a/2;i++){
				if(!(sum2[i-a/2][j-a/2]<20|sum2[i-a/2][j-a/2]>230)){
					red[i+a/2][j+a/2]=sum2[i-a/2][j-a/2];
				}
				
				if(!(sum3[i-a/2][j-a/2]<20|sum3[i-a/2][j-a/2]>230)){
					green[i+a/2][j+a/2]=sum3[i-a/2][j-a/2];
				}
				if(!(sum4[i-a/2][j-a/2]<0|sum4[i-a/2][j-a/2]>230)){
					blue[i+a/2][j+a/2]=sum4[i-a/2][j-a/2];
				}
				
				
				
			}
		}
		for(int j=0;j<h+a;j++){
			for(int i=0;i<w+a;i++){
				cRGB[i][j] = (alpha [i][j]<< 24) | (red[i][j]<< 16) | (green[i][j] << 8) | blue[i][j]; 
				wt.setRGB(i, j, cRGB[i][j]);
				
			}
		}
		File f=new File("C:/Users/user/Desktop","zhaopz.jpg");
		FileOutputStream w1=new FileOutputStream(f);
		ImageIO.write(wt,"jpg",w1 );
	}		
}
class zhanshitai extends Frame{
	zhanshitai() throws IOException{
		photo a = null;
		try {
			a = new photo();
		} catch (Exception e) {
		}
		Canvas1 ll=new Canvas1(a);
		add(ll);
		ll.setBounds(0,0,100,100);
		ll.setVisible(true);
		ll.validate();
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		setBounds(100,100,500,500);
		setVisible(true);
		validate();
	}
}
class Canvas1 extends Panel{
	photo k;
	Canvas1(photo p){
		k=p;
		
	}
	public void paint(Graphics g){
		g.drawImage(k.wt, 0,0,500,500, this);
		g.drawImage(k.im,500,0,500,500, this);
		
	}
}
public class example {
	public static void main(String args[]) throws IOException{
		new zhanshitai();
		
	}
}
