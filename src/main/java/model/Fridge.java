package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/***
 * Class for representing a refrigerator with the ability to track owners/health inspectors
 * It also enforces a constraint of only 3 food items per refrigerator object
 * @author Colin Knox
 *
 */
public class Fridge implements Serializable {
	/**
	 * ID used for serialization
	 */
	private static final long serialVersionUID = 3008795929037010770L;
	final static int MAX_CAPACITY = 3;
	
	int id;
	String name, owner;
	List<Food> items;
	
	
	public Fridge(int id, String name, String owner) {
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.items = new LinkedList<>();
	}
	
	public Fridge(int id, String name, String owner, List<Food> items) {
		this.id = id;
		this.name = name;
		this.owner = owner;
		if (items ==  null) {
			this.items = new LinkedList<>();
		} else {
			this.items = items;
		}
	}
	
	public int getID() {
		return id;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public int getCapacity() {
		return items.size();
	}
	
	public String getName() {
		return name;
	}
	
	public List<Food> getItems() {
		return items;
	}
	
	public void setItems(List<Food> items) {
		this.items = items;
	}
	
	public boolean addItem(Food item) {
		if (!isFull()) {
			items.add(item);
			return true;
		}
		return false;
	}
	
	public boolean removeItem(Food item) {
		if (items.remove(item)) {
			return true;
		}
		return false;
	}
	
	public Food removeItem(int index) {
		return items.remove(index);
	}
	
	public boolean isFull() {
		return items.size() == MAX_CAPACITY;
	}

	@Override
	public String toString() {
		return "\nFridge [capacity=" + getCapacity() + ", id=" + id + ", name=" + name + ", owner=" + owner + ", items="
				+ items.toString() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, items, name, owner);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fridge other = (Fridge) obj;
		return id == other.id;
	}
}
