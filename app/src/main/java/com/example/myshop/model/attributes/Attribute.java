package com.example.myshop.model.attributes;

import java.util.List;

public class Attribute {

	private int id;
	private String name;
	private String slug;
	private List<AttributeTerm> attributeTerms;


	public Attribute(int id, String name, String slug) {
		this.id = id;
		this.name = name;
		this.slug = slug;
	}

	public List<AttributeTerm> getAttributeTerms() {
		return attributeTerms;
	}

	public void setAttributeTerms(List<AttributeTerm> attributeTerms) {
		this.attributeTerms = attributeTerms;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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
			"Attribute{" +
			",name = '" + name + '\'' +
			",id = '" + id + '\'' +
			",slug = '" + slug + '\'' +
			"}";
		}
}
