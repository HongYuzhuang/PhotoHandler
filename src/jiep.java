import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class jiep {
	public static void main(String args[]) throws AWTException, IOException{
		Robot b=new Robot();
		b.delay(2000);
		File outf2=new File("c:/users/user/desktop/test/jietu.jpg");
		BufferedImage wt=b.createScreenCapture(new Rectangle(1366,768));
		FileOutputStream w1=new FileOutputStream(outf2);
		ImageIO.write(wt,"jpg",w1 );
	}

}
