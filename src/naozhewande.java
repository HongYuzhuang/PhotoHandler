import com.sun.image.codec.jpeg.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
class mainp extends Frame{
	PhotoToPic p;
	mainp() throws IOException{
		
	    p=new PhotoToPic();
		this.add(p);
		p.addMouseListener(new MouseListener(){
			Point pp =new Point(0,0);

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				pp=arg0.getPoint();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				p.setLocation((int)(p.getX()+arg0.getX()-pp.getX()),(int)(p.getY()+arg0.getY()-pp.getY()));
				p.repaint();
			}
			
		});
		p.setLocation(-200,-200);
		this.setBounds(0, 0, 500,500);
		this.setVisible(true);
		this.validate();
	}
	
}


class PhotoToPic extends Canvas{
	File f;
	BufferedImage im;
	char [][] a=null ;
	
	int w;
	int h;

	PhotoToPic() throws IOException{
		f=new File("C:/Users/user/Desktop","zhaop10.jpg");
	    im=ImageIO.read(f);
		w=(im.getWidth(null));
		h=im.getHeight();
		System.out.print(w+" "+h);
		a=new char [ im.getWidth() ][im.getHeight() ];
		Raster rs=im.getRaster();
		int n=rs.getNumDataElements();
		double[] b=new double[n];
		double sum = 0;
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				if(n==3){
				rs.getPixel(i, j, b);
				sum=(b[0]+b[1]+b[2])/3;
				 
				}
				else{
					sum=(b[3]+b[1]+b[2])/3;	
				}
				if(sum<145){
					a[i][j]=1;
				 }
				 else{
					a[i][j]=' ';
				 }
			}
		}
		
		this.setBounds(-200,-200,1000, 1000);
		this.setVisible(true);
		this.validate();
	
		
	}
	public void paint(Graphics g){
		BufferedImage im2=new BufferedImage(im.getWidth()*5,im.getHeight()*5,im.TYPE_INT_RGB);
		Graphics g2=im2.createGraphics();
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				if(a[i][j]==1){
					
					g.setFont(new Font("ËÎÌו",Font.PLAIN,8));
					g.drawString("1", 3*i, 3*j);
					//g2.setFont(new Font("ËÎÌו",Font.PLAIN,8));
					g2.drawString("1", 5*i, 5*j);
				}
				
			}
		}
		JPEGImageEncoder encoder = null;
		try {
			File wt=new File("C:/Users/user/Desktop","zhaopz.jpg");
			encoder = JPEGCodec.createJPEGEncoder(new FileOutputStream(wt));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			encoder.encode(im2);
		} catch (ImageFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		
	}
	
}
public class naozhewande {
	public static void main(String args[]) throws IOException{
		mainp pp=new mainp();
		
		
	}

}
