package cs2012final;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/* Lillian Leung
* CS2012
* 01 & 02
* Description: This is the StatusBar class which will create a StatusBar object.
* 			   The purpose of this methods is to show the player how much ammunition they have
* Other Comments: 
*/


public class StatusBar extends ImageView{
	private Image zeroAmmo = new Image("images/ammo 0.png");
	private Image oneAmmo = new Image("images/ammo 1.png");
	private Image twoAmmo = new Image("images/ammo 2.png");
	private Image threeAmmo = new Image("images/ammo 3.png");
	private Image fourAmmo = new Image("images/ammo 4.png");
	private Image fiveAmmo = new Image("images/ammo 5.png");
	
	private Image zeroAmmoMax = new Image("images/max ammo 0.png");
	private Image oneAmmoMax = new Image("images/max ammo 1.png");
	private Image twoAmmoMax = new Image("images/max ammo 2.png");
	private Image threeAmmoMax = new Image("images/max ammo 3.png");
	private Image fourAmmoMax = new Image("images/max ammo 4.png");
	private Image fiveAmmoMax = new Image("images/max ammo 5.png");
	
	private boolean hasCollectedAllAmmo = false; //stores if the player has collected all 5 ammunition from the grid
	
	//constructor that creates status bar
	public StatusBar(double size) {
		this.setImage(this.zeroAmmo);
		
		this.setFitHeight(size);
		this.setFitWidth(size);
	}
	
	//method that updates the status bar image
	public void setStatusBar(int ammoAmount) {
		//check if the user has not collected all the ammo
		if(!this.hasCollectedAllAmmo) {
			switch(ammoAmount) {
				case 0: 
					this.setImage(this.zeroAmmo);
					break;
				case 1:
					this.setImage(this.oneAmmo);
					break;
				case 2:
					this.setImage(this.twoAmmo);
					break;
				case 3:
					this.setImage(this.threeAmmo);
					break;
				case 4:
					this.setImage(this.fourAmmo);
					break;
				case 5:
					this.setImage(this.fiveAmmo);
					break;
				default:
					break;
			}
		}
		else {
			switch(ammoAmount) {
				case 0: 
					this.setImage(this.zeroAmmoMax);
					break;
				case 1:
					this.setImage(this.oneAmmoMax);
					break;
				case 2:
					this.setImage(this.twoAmmoMax);
					break;
				case 3:
					this.setImage(this.threeAmmoMax);
					break;
				case 4:
					this.setImage(this.fourAmmoMax);
					break;
				case 5:
					this.setImage(this.fiveAmmoMax);
					break;
				default:
					break;
			}
		}
	}
	
	public boolean getHasCollectedAllAmmo() {
		return this.hasCollectedAllAmmo;
	}
	
	public void setHasCollectedAllAmmo(boolean hasCollectedAllAmmo) {
		this.hasCollectedAllAmmo = hasCollectedAllAmmo;
	}
}
