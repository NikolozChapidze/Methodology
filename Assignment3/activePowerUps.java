import acm.graphics.*;
import acm.program.GraphicsProgram;

public class activePowerUps extends GraphicsProgram{
	private GImage powerUp;
	private int hitsOfCtreation;
	private boolean active;
	
	

	public activePowerUps() {
		super();
		active=false;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public activePowerUps(GImage powerUp, int hitsOfCtreation, boolean active) {
		super();
		this.powerUp = powerUp;
		this.hitsOfCtreation = hitsOfCtreation;
		this.active = active;
	}

	public GImage getPowerUp() {
		return powerUp;
	}

	public void setPowerUp(GImage powerUp) {
		this.powerUp = powerUp;
	}

	public int getHitsOfCtreation() {
		return hitsOfCtreation;
	}

	public void setHitsOfCtreation(int hitsOfCtreation) {
		this.hitsOfCtreation = hitsOfCtreation;
	}
	
	
}
