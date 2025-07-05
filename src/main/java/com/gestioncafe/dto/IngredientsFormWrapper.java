package com.gestioncafe.dto;

import java.util.ArrayList;
import java.util.List;

public class IngredientsFormWrapper implements java.io.Serializable {
    private List<IngredientFormDTO> ingredients = new ArrayList<>();

    public IngredientsFormWrapper() {
        this.ingredients = new ArrayList<>();
    }

    public List<IngredientFormDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientFormDTO> ingredients) {
        this.ingredients = ingredients;
    }
}
