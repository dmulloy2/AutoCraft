package net.dmulloy2.autocraft.io.legacy;

@Deprecated
public class ACFactory implements EFactory<ACProperties> {

	public ACProperties newEntity() {
		return new ACProperties();
	}
}