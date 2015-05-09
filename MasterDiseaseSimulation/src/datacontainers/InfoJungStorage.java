package datacontainers;

import java.util.ArrayList;

public class InfoJungStorage {
	ArrayList<ArrayList<InfoStorage>> infoStorages;
	ArrayList<JungStorage> jungStorage;
	
	public InfoJungStorage(ArrayList<ArrayList<InfoStorage>> infoStorages, ArrayList<JungStorage> jungStorage) {
		this.infoStorages = infoStorages;
		this.jungStorage = jungStorage;
	}
	
	public ArrayList<ArrayList<InfoStorage>> getInfoStorages() {
		return infoStorages;
	}

	public ArrayList<JungStorage> getJungStorage() {
		return jungStorage;
	}
}