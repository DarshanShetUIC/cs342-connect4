import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameButton extends Button{
	
	String buttonImage0 = "/images/none.png";
	String buttonImage1 = "/images/blue.png";
	String buttonImage2 = "/images/red.png";
	Image img1;
	Image img2;
	Image img0;
	int player;
	int r;
	int c;
	
	public GameButton(int row, int col, int player){
		img0 = new Image(getClass().getResourceAsStream(buttonImage0));
		img1 = new Image(getClass().getResourceAsStream(buttonImage1));
		img2 = new Image(getClass().getResourceAsStream(buttonImage2));
		r = row;
		c = col;
		setPlayer(player);
	}
	
	public void setPlayer(int player){
		this.player = player;
		if(player == 1){this.setGraphic(new ImageView(img1));}
		else if(player == 2){this.setGraphic(new ImageView(img2));}
		else{this.setGraphic(new ImageView(img0));}
	}
}
