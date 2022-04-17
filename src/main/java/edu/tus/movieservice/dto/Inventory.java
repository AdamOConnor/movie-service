package edu.tus.movieservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name="Inventory")
@ApiModel(description = "Library Movie Inventory Details - A inventory entity with starting & current quantities of movies available to loan")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @PositiveOrZero
    @ApiModelProperty(name = "initialQuantity", dataType = "Integer", example = "5", notes="Must be zero or positive integer")
    private int initialQuantity;

    @PositiveOrZero
    @ApiModelProperty(name = "currentQuantity", dataType = "Integer", example = "5", notes="Must be zero or positive integer")
    private int currentQuantity;

    @OneToOne(mappedBy = "inventory")
    private Movie movie;

    public Inventory() {
    }

    public Inventory(int initialQuantity, int currentQuantity) {
        this.initialQuantity = initialQuantity;
        this.currentQuantity = currentQuantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getInitialQuantity() {
        return initialQuantity;
    }

    public void setInitialQuantity(int initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

}


