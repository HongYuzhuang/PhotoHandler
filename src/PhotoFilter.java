import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.awt.image.*;

public class PhotoFilter {
	double a=1;
	int upbound=220;
	int lowbound=50;
	final double[][] defaultkenel={{-0.5*a,-0.5*a,-0.5*a,-0.5*a,-0.5*a,-0.5,-0.5*a},
            {-0.5*a, 1*a , 1*a , 1*a , 1*a , 1*a ,-0.5*a},
            {-0.5*a, 1*a ,  -1*a ,  -1*a ,  -1*a , 1*a ,-0.5*a},
            {-0.5*a, 1*a ,  -1*a , 4*a+1,  -1*a , 1*a ,-0.5*a},
            {-0.5*a, 1*a ,  -1*a ,  -1*a ,  -1*a , 1*a ,-0.5*a},
            {-0.5*a, 1*a , 1*a , 1*a , 1*a , 1*a ,-0.5*a},
            {-0.5*a,-0.5*a,-0.5*a,-0.5*a,-0.5*a,-0.5*a,-0.5*a}};
	double[][] kenel;
	BufferedImage im;
	File infile,otfile;
	PhotoFilter() throws IOException{
		try{
		infile=new File("C:/Users/user/Desktop","zhaop1.jpg");
		}
		catch(Exception e){
			System.out.print(e);
			infile=null;
		}
		try{
		otfile=new File("C:/Users/user/Desktop","zhaopz.jpg");
		}
		catch(Exception e2){
			System.out.print(e2);
			otfile=null;
		}
		kenel=defaultkenel;
	}
	
	PhotoFilter(double[][] kenel,BufferedImage im){
		otfile=new File("C:/Users/user/Desktop","zhaopz.jpg");
		this.im=im;
		this.kenel=kenel;
	}
	public BufferedImage rotateShun90() throws Exception{
		int w=im.getWidth(null);
		int h=im.getHeight(null);
		BufferedImage wt=new BufferedImage(h,w, BufferedImage.TYPE_INT_RGB);
		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				wt.setRGB(h-i-1, j, im.getRGB(j, i));
				
			}
		}
		FileOutputStream w1=new FileOutputStream(this.otfile);
		ImageIO.write(wt,"jpg",w1 );
		return wt;
	}
	public BufferedImage rotate180() throws Exception{
		int w=im.getWidth(null);
		int h=im.getHeight(null);
		BufferedImage wt=new BufferedImage(w,h, BufferedImage.TYPE_INT_RGB);
		for(int i=0;i<w;i++){
			for(int j=0;j<h;j++){
				wt.setRGB(w-i-1, h-j-1, im.getRGB(i,j));
				
			}
		}
		FileOutputStream w1=new FileOutputStream(this.otfile);
		ImageIO.write(wt,"jpg",w1 );
		return wt;
	}
	public BufferedImage rotateNi90() throws Exception{
		int w=im.getWidth(null);
		int h=im.getHeight(null);
		BufferedImage wt=new BufferedImage(h,w, BufferedImage.TYPE_INT_RGB);
		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				wt.setRGB(i, w-j-1, im.getRGB(j, i));
				
			}
		}
		FileOutputStream w1=new FileOutputStream(this.otfile);
		ImageIO.write(wt,"jpg",w1 );
		return wt;
	}
	public void setim(BufferedImage im){
		this.im=im;
	}
	public void setupbound(int a){
		this.upbound=a;
	}
	public void setlowbound(int a){
		this.lowbound=a;
	}
	public void setkenel(double[][] kenel){
		this.kenel=kenel;
	}
	public void setoutbyname(String name){
		otfile=new File("C:/Users/user/Desktop",name+".jpg");
	}
	public void setoutbydict(String dictory){
		otfile=new File(dictory);
	}
	public void setimbydict(String dictory){
		infile=new File(dictory);
		try {
			im=ImageIO.read(infile);
		} catch (IOException e) {
			System.out.print("wrong path");
		}
	}
	public void atimekenel(double a){
		for(int i=0;i<kenel.length;i++){
			for(int j=0;j<kenel.length;j++){
				kenel[i][j]=a*kenel[i][j];
			}
		}
	}
	public void atimekenelreservingcentralas1(double a){
		for(int i=0;i<kenel.length;i++){
			for(int j=0;j<kenel.length;j++){
				kenel[i][j]=a*kenel[i][j];
			}
		}
		kenel[(kenel.length-1)/2][(kenel.length-1)/2]-=(a-1);
	}
	
	public void dianzhentu(int a,int val) throws IOException {
		int w=im.getWidth();
		int h=im.getHeight();
		int b=a*h/w;
		int w2=w/a+1;
		int h2=h/b+1;
		int sum=0;
		int counter=0;
		BufferedImage wt=new BufferedImage(20*a,20*b, BufferedImage.TYPE_INT_RGB);
		double red[][],green[][],blue[][];
		red=new double[w][h];
		green=new double[w][h];
		blue=new double[w][h];
		Raster rs=im.getRaster();
		double cunchu[]=new double[3];
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				rs.getPixel(i, j,cunchu);
				red [i][j]= cunchu[0];
			    green[i][j] = cunchu[1];
				blue[i][j] = cunchu[2];			
			}
		}
		
		Graphics2D g=wt.createGraphics();
		g.setFont(new Font("",Font.PLAIN,8));
		for(int j=0;j<b;j++){
			for(int i=0;i<a;i++){
				for(int z=0;z<w2;z++){
					for(int x=0;x<h2;x++){
						if(((i*w2+z)<w)& (j*h2+x<h)){
							counter++;
							sum+=(red[i*w2+z][j*h2+x]+blue[i*w2+z][j*h2+x]+green[i*w2+z][j*h2+x])/3; //
						}
					}
				}
				if(counter==0){
					sum=255;
				}
				else{
					sum=sum/counter;	
				}
				if(sum<val){
					g.drawString("1", 9*i,9*j);
					System.out.print("1");
				
				}
				else{
					System.out.print(" ");
				}
				sum=0;
				counter=0;
			}
			System.out.println("");
		}
		red=new double[20*a][20*b];
		green=new double[20*a][20*b];
		blue=new double[20*a][20*b];
		rs=wt.getRaster();
		for(int j=0;j<20*b;j++){
			for(int i=0;i<20*a;i++){
				rs.getPixel(i, j,cunchu);
				red [i][j]= cunchu[0];
			    green[i][j] = cunchu[1];
				blue[i][j] = cunchu[2];			
			}
		}
		WritableRaster rs2=wt.getRaster();
		for(int j=0;j<20*b;j++){
			for(int i=0;i<20*a;i++){
				cunchu[0]=255-red[i][j];
				cunchu[1]=255-green[i][j];
				cunchu[2]=255-blue[i][j];
				rs2.setPixel(i, j, cunchu);
				
			}
		}
		FileOutputStream w1=new FileOutputStream(this.otfile);
		ImageIO.write(wt,"jpg",w1 );
	}
	public void Output(BufferedImage wt) throws IOException{
		FileOutputStream w1=new FileOutputStream(this.otfile);
		ImageIO.write(wt,"jpg",w1 );
	}
	public void Output(BufferedImage wt,String kkk) throws IOException{
		this.setoutbyname(kkk);
		FileOutputStream w1=new FileOutputStream(this.otfile);
		ImageIO.write(wt,"jpg",w1 );
	}
	public void dianzhentu2() throws IOException {
		int w=im.getWidth();
		int h=im.getHeight();
		int a=70;
		int s=40;
		int b=a*h/w;
		int w2=w/a+1;
		int h2=h/b+1;
		int sum=0;
		int counter=0;
		BufferedImage wt=new BufferedImage(s*a,s*b, BufferedImage.TYPE_INT_RGB);
		double red[][],green[][],blue[][];
		red=new double[w][h];
		green=new double[w][h];
		blue=new double[w][h];
		Raster rs=im.getRaster();
		double cunchu[]=new double[3];
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				rs.getPixel(i, j,cunchu);
				red [i][j]= cunchu[0];
			    green[i][j] = cunchu[1];
				blue[i][j] = cunchu[2];			
			}
		}
		
		Graphics2D g=wt.createGraphics();
		g.setFont(new Font("",Font.PLAIN,8));
		for(int j=0;j<b;j++){
			for(int i=0;i<a;i++){
				for(int z=0;z<w2;z++){
					for(int x=0;x<h2;x++){
						if(((i*w2+z)<w)& (j*h2+x<h)){
							counter++;
							sum+=(red[i*w2+z][j*h2+x]+blue[i*w2+z][j*h2+x]+green[i*w2+z][j*h2+x])/3; //
						}
					}
				}
				if(counter==0){
					sum=255;
				}
				else{
					sum=sum/counter;	
				}
				double p;
				/*
				if(sum==255){
					p=0;
				}
				else{
				p=Math.exp(-sum/(255-sum));					
					//g.drawString("1", 9*i,9*j);
					g.fillRect(s*i, s*j, (int)(s*p), (int)(s*p));
				}
				**/
				
				p=(255.0-sum)/255.0;
			    g.fillOval((int)(s*i)-(int)(s*p/2+5), (int)(s*j)-(int)(s*p/2+5), (int)(s*p+10), (int)(s*p)+10);
				
				
				sum=0;
				counter=0;
			}
		}
		red=new double[s*a][s*b];
		green=new double[s*a][s*b];
		blue=new double[s*a][s*b];
		rs=wt.getRaster();
		for(int j=0;j<s*b;j++){
			for(int i=0;i<s*a;i++){
				rs.getPixel(i, j,cunchu);
				red [i][j]= cunchu[0];
			    green[i][j] = cunchu[1];
				blue[i][j] = cunchu[2];			
			}
		}
		WritableRaster rs2=wt.getRaster();
		for(int j=0;j<s*b;j++){
			for(int i=0;i<s*a;i++){
				cunchu[0]=255-red[i][j];
				cunchu[1]=255-green[i][j];
				cunchu[2]=255-blue[i][j];
				rs2.setPixel(i, j, cunchu);
				
			}
		}
		FileOutputStream w1=new FileOutputStream(this.otfile);
		ImageIO.write(wt,"jpg",w1 );
	}
	public void dianzhentu3(double val1) throws IOException {
		int w=im.getWidth();
		int h=im.getHeight();
		int a=90;
		int s=40;
		int b=a*h/w;
		int w2=w/a+1;
		int h2=h/b+1;
		int counter=0;
		BufferedImage wt=new BufferedImage(s*a,s*b, BufferedImage.TYPE_INT_RGB);
		double red[][],green[][],blue[][];
		red=new double[w][h];
		green=new double[w][h];
		blue=new double[w][h];
		Raster rs=im.getRaster();
		double cunchu[]=new double[3];
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				rs.getPixel(i, j,cunchu);
				red [i][j]= cunchu[0];
			    green[i][j] = cunchu[1];
				blue[i][j] = cunchu[2];			
			}
		}
		
		Graphics2D g=wt.createGraphics();
		g.setFont(new Font("",Font.PLAIN,8));
		double ssum1[][]=new double[b][a];
		double ssum2[][]=new double[b][a];
		double ssum3[][]=new double[b][a];
		for(int j=0;j<b;j++){
			for(int i=0;i<a;i++){
				counter=0;
				for(int z=0;z<w2;z++){
					
					for(int x=0;x<h2;x++){
						if(((i*w2+z)<w)& (j*h2+x<h)){
							counter+=1;
							ssum1[j][i]+=red[i*w2+z][j*h2+x]; //
							ssum2[j][i]+=blue[i*w2+z][j*h2+x]; //
							ssum3[j][i]+=green[i*w2+z][j*h2+x]; //
						}
					}
				}
				ssum1[j][i]=ssum1[j][i]/counter;
				ssum2[j][i]=ssum2[j][i]/counter;
				ssum3[j][i]=ssum3[j][i]/counter;
			}
		}
		double p;
		for(int j=1;j<b-1;j++){
			for(int i=1;i<a-1;i++){
				double average1=(ssum1[j-1][i-1]+ssum1[j-1][i]+ssum1[j-1][i+1]+ssum1[j][i-1]+ssum1[j][i]+ssum1[j][i+1]+ssum1[j+1][i-1]+ssum1[j+1][i]+ssum1[j+1][i+1])/9;
				double average3=(ssum3[j-1][i-1]+ssum3[j-1][i]+ssum3[j-1][i+1]+ssum3[j][i-1]+ssum3[j][i]+ssum3[j][i+1]+ssum3[j+1][i-1]+ssum3[j+1][i]+ssum3[j+1][i+1])/9;
				double std=0;					
				for(int k=j-1;k<=j+1;k++){
					for(int l=i-1;l<=i+1;l++){
						std+=Math.pow((ssum1[k][l]-average1),2)+Math.pow((ssum3[k][l]-average3),2)+Math.pow((ssum3[k][l]-average3),2);
					}
				}
				std=Math.pow(std, 0.5)/9.0;
				if(std>val1){
					p=(255.0-(ssum1[j][i]+ssum2[j][i]+ssum3[j][i])/3)/255.0;
				    g.fillRect((int)(s*i)-(int)(s*p/2), (int)(s*j)-(int)(s*p/2), (int)(s*p+10), (int)(s*p)+10);
						
				}
			}
		}
		red=new double[s*a][s*b];
		green=new double[s*a][s*b];
		blue=new double[s*a][s*b];
		rs=wt.getRaster();
		for(int j=0;j<s*b;j++){
			for(int i=0;i<s*a;i++){
				rs.getPixel(i, j,cunchu);
				red [i][j]= cunchu[0];
			    green[i][j] = cunchu[1];
				blue[i][j] = cunchu[2];			
			}
		}
		WritableRaster rs2=wt.getRaster();
		for(int j=0;j<s*b;j++){
			for(int i=0;i<s*a;i++){
				cunchu[0]=255-red[i][j];
				cunchu[1]=255-green[i][j];
				cunchu[2]=255-blue[i][j];
				rs2.setPixel(i, j, cunchu);
				
			}
		}
		FileOutputStream w1=new FileOutputStream(this.otfile);
		ImageIO.write(wt,"jpg",w1 );
	}
	
	public BufferedImage jiahei(double a) throws IOException {
		int w=im.getWidth(null);
		int h=im.getHeight(null);
		double red[][],green[][],blue[][];
		red=new double[w][h];
		green=new double[w][h];
		blue=new double[w][h];

		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				red [i][j]= 0;
			    green[i][j] = 0;
				blue[i][j] = 0;
			}
		
		}
		WritableRaster rs=im.getRaster();
		double cunchu[]=new double[3];
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				rs.getPixel(i, j,cunchu);

				red [i][j]= Math.pow(cunchu[0], a)/Math.pow(255.0, a-1);
			    green[i][j] =  Math.pow(cunchu[1], a)/Math.pow(255.0, a-1);
				blue[i][j] =  Math.pow(cunchu[2], a)/Math.pow(255.0, a-1);	
				
				//red [i][j]=255*(Math.sin((Math.PI/2)*cunchu[0]/255));
			    //green[i][j] = 255*(Math.sin((Math.PI/2)*cunchu[1]/255));
				//blue[i][j] =  255*(Math.sin((Math.PI/2)*cunchu[2]/255));
				
				
			}
		}
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				cunchu[0]=red[i][j];
				cunchu[1]=green[i][j];
				cunchu[2]=blue[i][j];
				rs.setPixel(i, j, cunchu);
				
			}
		}
		FileOutputStream w1=new FileOutputStream(this.otfile);
		ImageIO.write(im,"jpg",w1 );
		return im;
	}
	
	public BufferedImage transacsin () throws IOException {
		int w=im.getWidth(null);
		int h=im.getHeight(null);
		double red[][],green[][],blue[][];
		red=new double[w][h];
		green=new double[w][h];
		blue=new double[w][h];

		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				red [i][j]= 0;
			    green[i][j] = 0;
				blue[i][j] = 0;
			}
		
		}
		WritableRaster rs=im.getRaster();
		double cunchu[]=new double[3];
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				rs.getPixel(i, j,cunchu);
				//Math.exp(a*cunchu[0])/Math.exp(a*255)
				//red [i][j]= Math.pow(cunchu[0], a)/Math.pow(255.0, a-1);
			    //green[i][j] =  Math.pow(cunchu[1], a)/Math.pow(255.0, a-1);
				//blue[i][j] =  Math.pow(cunchu[2], a)/Math.pow(255.0, a-1);	
				
				//red [i][j]=255*(Math.sin((Math.PI/2)*cunchu[0]/255));
			    //green[i][j] = 255*(Math.sin((Math.PI/2)*cunchu[1]/255));
				//blue[i][j] =  255*(Math.sin((Math.PI/2)*cunchu[2]/255));
				
				red [i][j]=Math.asin(cunchu[0]/255)/(2*Math.PI/255.0);
			    green[i][j] = Math.asin(cunchu[1]/255)/(2*Math.PI/255.0);
				blue[i][j] =  Math.asin(cunchu[2]/255)/(2*Math.PI/255.0);
			}
		}
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				cunchu[0]=red[i][j];
				cunchu[1]=green[i][j];
				cunchu[2]=blue[i][j];
				rs.setPixel(i, j, cunchu);
				
			}
		}
		FileOutputStream w1=new FileOutputStream(this.otfile);
		ImageIO.write(im,"jpg",w1 );
		return im;
	}
	
	
	public BufferedImage transsin () throws IOException {
		int w=im.getWidth(null);
		int h=im.getHeight(null);
		double red[][],green[][],blue[][];
		red=new double[w][h];
		green=new double[w][h];
		blue=new double[w][h];

		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				red [i][j]= 0;
			    green[i][j] = 0;
				blue[i][j] = 0;
			}
		
		}
		WritableRaster rs=im.getRaster();
		double cunchu[]=new double[3];
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				rs.getPixel(i, j,cunchu);
				//Math.exp(a*cunchu[0])/Math.exp(a*255)
				//red [i][j]= Math.pow(cunchu[0], a)/Math.pow(255.0, a-1);
			    //green[i][j] =  Math.pow(cunchu[1], a)/Math.pow(255.0, a-1);
				//blue[i][j] =  Math.pow(cunchu[2], a)/Math.pow(255.0, a-1);	
				
				//red [i][j]=255*(Math.sin((Math.PI/2)*cunchu[0]/255));
			    //green[i][j] = 255*(Math.sin((Math.PI/2)*cunchu[1]/255));
				//blue[i][j] =  255*(Math.sin((Math.PI/2)*cunchu[2]/255));
				
				red [i][j]=127.5*(Math.sin((Math.PI/2)*(cunchu[0]-127.5)/127.5)+1);
			    green[i][j] = 127.5*(Math.sin((Math.PI/2)*(cunchu[1]-127.5)/127.5)+1);
				blue[i][j] = 127.5*(Math.sin((Math.PI/2)*(cunchu[2]-127.5)/127.5)+1);
				
				//red [i][j]=Math.asin(cunchu[0]/255)/(2*Math.PI/255.0);
			    //green[i][j] = Math.asin(cunchu[1]/255)/(2*Math.PI/255.0);
				//blue[i][j] =  Math.asin(cunchu[2]/255)/(2*Math.PI/255.0);
			}
		}
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				cunchu[0]=red[i][j];
				cunchu[1]=green[i][j];
				cunchu[2]=blue[i][j];
				rs.setPixel(i, j, cunchu);
				
			}
		}
		FileOutputStream w1=new FileOutputStream(this.otfile);
		ImageIO.write(im,"jpg",w1 );
		return im;
	}
	public BufferedImage transsin2 () throws IOException {
		int w=im.getWidth(null);
		int h=im.getHeight(null);
		double red[][],green[][],blue[][];
		red=new double[w][h];
		green=new double[w][h];
		blue=new double[w][h];

		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				red [i][j]= 0;
			    green[i][j] = 0;
				blue[i][j] = 0;
			}
		
		}
		WritableRaster rs=im.getRaster();
		double cunchu[]=new double[3];
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				rs.getPixel(i, j,cunchu);
				//Math.exp(a*cunchu[0])/Math.exp(a*255)
				//red [i][j]= Math.pow(cunchu[0], a)/Math.pow(255.0, a-1);
			    //green[i][j] =  Math.pow(cunchu[1], a)/Math.pow(255.0, a-1);
				//blue[i][j] =  Math.pow(cunchu[2], a)/Math.pow(255.0, a-1);	
				
				red [i][j]=255*(Math.sin((Math.PI/2)*cunchu[0]/255));
			    green[i][j] = 255*(Math.sin((Math.PI/2)*cunchu[1]/255));
				blue[i][j] =  255*(Math.sin((Math.PI/2)*cunchu[2]/255));
				
				
				//red [i][j]=Math.asin(cunchu[0]/255)/(2*Math.PI/255.0);
			    //green[i][j] = Math.asin(cunchu[1]/255)/(2*Math.PI/255.0);
				//blue[i][j] =  Math.asin(cunchu[2]/255)/(2*Math.PI/255.0);
			}
		}
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				cunchu[0]=red[i][j];
				cunchu[1]=green[i][j];
				cunchu[2]=blue[i][j];
				rs.setPixel(i, j, cunchu);
				
			}
		}
		FileOutputStream w1=new FileOutputStream(this.otfile);
		ImageIO.write(im,"jpg",w1 );
		return im;
	}
	public BufferedImage meibai() throws IOException {
		int w=im.getWidth(null);
		int h=im.getHeight(null);
		double red[][],green[][],blue[][];
		red=new double[w][h];
		green=new double[w][h];
		blue=new double[w][h];

		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				red [i][j]= 0;
			    green[i][j] = 0;
				blue[i][j] = 0;
			}
		
		}
		WritableRaster rs=im.getRaster();
		double cunchu[]=new double[3];
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				rs.getPixel(i, j,cunchu);
				red [i][j]= Math.pow(cunchu[0], 0.25)*64;
			    green[i][j] =  Math.pow(cunchu[1], 0.25)*64;
				blue[i][j] =  Math.pow(cunchu[2], 0.25)*64;			
			}
		}
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				cunchu[0]=red[i][j];
				cunchu[1]=green[i][j];
				cunchu[2]=blue[i][j];
				rs.setPixel(i, j, cunchu);
				
			}
		}
		FileOutputStream w1=new FileOutputStream(this.otfile);
		ImageIO.write(im,"jpg",w1 );
		return im;
	}
	public BufferedImage filter() throws IOException{
		int a=kenel.length-1;
		int w=im.getWidth(null);
		int h=im.getHeight(null);		
		BufferedImage wt=new BufferedImage(w,h, BufferedImage.TYPE_INT_RGB);		
		double red[][],green[][],blue[][];
		red=new double[w+a][h+a];
		green=new double[w+a][h+a];
		blue=new double[w+a][h+a];
		
		for(int j=0;j<h+a;j++){
			for(int i=0;i<w+a;i++){
				red [i][j]= 0;
			    green[i][j] = 0;
				blue[i][j] = 0;
			}
		
		}
		WritableRaster rs=im.getRaster();
	
		double cunchu[]=new double[3];
		for(int j=a/2;j<h+a/2;j++){
			for(int i=a/2;i<w+a/2;i++){
				rs.getPixel(i-a/2, j-a/2,cunchu);
				red [i][j]= cunchu[0];
			    green[i][j] = cunchu[1];
				blue[i][j] = cunchu[2];			
			}
		}
		
		double sum2[][]=new double[w][h],sum3[][]=new double[w][h],sum4[][]=new double[w][h];
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
				if(!(sum2[i-a/2][j-a/2]<lowbound|sum2[i-a/2][j-a/2]>upbound)){
					red[i][j]=sum2[i-a/2][j-a/2];
				}
				
				if(!(sum3[i-a/2][j-a/2]<lowbound|sum3[i-a/2][j-a/2]>upbound)){
					green[i][j]=sum3[i-a/2][j-a/2];
				}
				if(!(sum4[i-a/2][j-a/2]<lowbound|sum4[i-a/2][j-a/2]>upbound)){
					blue[i][j]=sum4[i-a/2][j-a/2];
				}
				
				
			}
		}
		WritableRaster rs2=wt.getRaster();
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				cunchu[0]=red[i+a/2][j+a/2];
				cunchu[1]=green[i+a/2][j+a/2];
				cunchu[2]=blue[i+a/2][j+a/2];
				rs2.setPixel(i, j, cunchu);
				
			}
		}
		
		FileOutputStream w1=new FileOutputStream(this.otfile);
		ImageIO.write(wt,"jpg",w1 );
		return wt;
	}
	public BufferedImage picMix(BufferedImage im1,BufferedImage im2,double a) throws IOException{
		int w=im1.getWidth();
		int h = im1.getHeight();
		BufferedImage im3=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		if(im1.getWidth()!=im2.getWidth()|im1.getHeight()!=im2.getHeight()){

			System.out.print("Image not match");
		}
		else{
			double[] cunchu1=new double[3];
			double[] cunchu2=new double[3];
			WritableRaster rs1=im1.getRaster();
			WritableRaster rs2=im2.getRaster();
			WritableRaster rs3=im3.getRaster();
			for(int i=0;i<w;i++){
				for(int j=0;j<h;j++){
					rs1.getPixel(i, j, cunchu1);
					rs2.getPixel(i, j, cunchu2);
					for(int l=0;l<3;l++){
						cunchu1[l]=cunchu1[l]*(a)+cunchu2[l]*(1-a);	
					}
					
					rs3.setPixel(i, j, cunchu1);
				}
			}
		}
		this.Output(im3);
		return im3;
		
	}
	public BufferedImage String_to_im(int s,String a) throws IOException{
		int n = a.length();
		BufferedImage wt=new BufferedImage(50*s,55*n*s, BufferedImage.TYPE_INT_RGB);
		int w=wt.getWidth();
		int h = wt.getHeight();	
		Graphics2D g=wt.createGraphics();
		g.setFont(new Font("Ó×Ô²",Font.PLAIN,45*s));//·½ÕýÀ¼Í¤³¬Ï¸ºÚ¼òÌå
		char[] array1=a.toCharArray();
		for(int i = 0;i<n;i++){
			g.drawString((array1[i])+"",0,(90+(i-1)*45)*s);
		}
		double[]cunchu=new double[3];
		double[][] red=new double[w][h];
		double[][] green=new double[w][h];
		double[][] blue=new double[w][h];
		WritableRaster rs2=wt.getRaster();
		for(int j=0;j<w;j++){
			for(int i=0;i<h;i++){
				rs2.getPixel(j, i,cunchu);
				red [j][i]= cunchu[0];
			    green[j][i] = cunchu[1];
				blue[j][i] = cunchu[2];			
			}
		}
		for(int j=0;j<w;j++){
			for(int i=0;i<h;i++){
				cunchu[0]=255-red[j][i];
				cunchu[1]=255-green[j][i];
				cunchu[2]=255-blue[j][i];
				rs2.setPixel(j, i, cunchu);
				
			}
		}
		this.Output(wt);
		return wt;
	}

}
