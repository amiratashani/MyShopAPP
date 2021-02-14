package com.example.myshop.model.attributes;

public class AttributeTerm {

	private int menuOrder;
	private String name;
	private int count;
	private String description;
	private int id;
	private String slug;
	private boolean isCheck;

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean check) {
		isCheck = check;
	}

	public void setMenuOrder(int menuOrder){
		this.menuOrder = menuOrder;
	}

	public int getMenuOrder(){
		return menuOrder;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setSlug(String slug){
		this.slug = slug;
	}

	public String getSlug(){
		return slug;
	}

	@Override
 	public String toString(){
		return 
			"AttributeTerm{" +
			"menu_order = '" + menuOrder + '\'' +
			",name = '" + name + '\'' + 
			",count = '" + count + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",slug = '" + slug + '\'' + 
			"}";
		}
}
