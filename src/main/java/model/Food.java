package model;

import java.io.Serializable;
import java.util.Objects;

/***
 * Food class that is essentially just a String wrapper
 * @author Colin Knox
 *
 */
public class Food implements Serializable {
	/**
	 * ID used for serialization
	 */
	private static final long serialVersionUID = -9129029669928109108L;
	int id;
	String name;
	int fridgeID;
	
	public Food(int id, String name, int fridgeID) {
		this.id = id;
		this.name = name;
		this.fridgeID = fridgeID;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getFridgeID() {
		return fridgeID;
	}
	
	public void setFridgeID(int fridgeID) {
		this.fridgeID = fridgeID;
	}

	@Override
	public String toString() {
		return "Food [id=" + id + ", name=" + name + ", fridgeID=" + fridgeID + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fridgeID, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Food other = (Food) obj;
		return id == other.id;
	}
	
	
}
