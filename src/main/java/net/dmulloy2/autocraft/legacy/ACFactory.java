package net.dmulloy2.autocraft.legacy;

public class ACFactory implements EFactory<ACProperties> {

	public ACProperties newEntity() {
		return new ACProperties();
	}
	
}
