import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameButton extends Button{
	
	String buttonImage0 = "/images/none.png";
	String buttonImage1 = "/images/blue.png";
	String buttonImage2 = "/images/red.png";
	String buttonImage3 = "/images/blue-win.png";
	String buttonImage4 = "/images/red-win.png";
	Image img1;
	Image img2;
	Image img3;
	Image img4;
	Image img0;
	int player;
	int r;
	int c;
	
	public GameButton(int row, int col, int player){
		img0 = new Image(getClass().getResourceAsStream(buttonImage0));
		img1 = new Image(getClass().getResourceAsStream(buttonImage1));
		img2 = new Image(getClass().getResourceAsStream(buttonImage2));
		img3 = new Image(getClass().getResourceAsStream(buttonImage3));
		img4 = new Image(getClass().getResourceAsStream(buttonImage4));
		r = row;
		c = col;
		setPlayer(player);
	}
	
	public void setPlayer(int player){
		this.player = player;
		if(player == 1){this.setGraphic(new ImageView(img1));}
		else if(player == 2){this.setGraphic(new ImageView(img2));}
		else if(player == 10){this.setGraphic(new ImageView(img3));}
		else if(player == 20){this.setGraphic(new ImageView(img4));}
		else{this.setGraphic(new ImageView(img0));}
	}
}
