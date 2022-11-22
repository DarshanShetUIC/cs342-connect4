import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameButton extends Button{
	
	String buttonImage1;
	String buttonImage2;
	String buttonImage0;
	int player;
	int r;
	int c;
	
	public GameButton(int row, int col, String i0, String i1, String i2){
		buttonImage1 = i1;
		buttonImage2 = i2;
		buttonImage0 = i0;
		Image img1 = new Image(getClass().getResourceAsStream(i1));
		Image img2 = new Image(getClass().getResourceAsStream(i2));
		Image img0 = new Image(getClass().getResourceAsStream(i0));
		player = 0;
		r = row;
		c = col;
	}
	
	public void setPlayer(int player){
		this.player = player;
		if(player == 1){
			this.setGraphic(new ImageView(img1));
		}
		else if(player == 2){
			this.setGraphic(new ImageView(img2));
		}
		else{
			this.setGraphic(new ImageView(img0));
		}
	}
}
