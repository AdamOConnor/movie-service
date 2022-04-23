package edu.tus.movieservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Movies")
@ApiModel(description = "Library Movie Details - A movie entity to add to the library inventory storing movie titles, year, rating, description along with current inventory details")
public class Movie {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotBlank(message = "Movie must have an associated a Name")
	@Size(min=4)
	@Column(name = "name")
	private String name;

	@NotBlank(message = "Movie must have an associated Director")
	@Column(name = "director")
	@ApiModelProperty(name = "director", dataType = "String", example = "Matt Reeves", notes="Must have a valid director")
	private String director;

	@Column(name = "year")
	private String year;

	@NotBlank(message = "Movie must have an associated rating")
	@Column(name = "rating")
	private String rating;

	@NotBlank(message = "Movie must have an associated description")
	@Lob
	@Column(name = "description")
	private String description;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "inventory_id", referencedColumnName = "id")
	private Inventory inventory;

	public Movie() {
	}

	public Movie(String name, String director, String year, String rating, String description, Inventory inventory) {
		this.id = id;
		this.name = name;
		this.director = director;
		this.year = year;
		this.rating = rating;
		this.description = description;
		this.inventory = inventory;
	}

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
}
