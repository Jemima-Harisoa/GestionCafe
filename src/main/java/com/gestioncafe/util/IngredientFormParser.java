package com.gestioncafe.util;

import java.util.ArrayList;
import java.util.List;

import com.gestioncafe.dto.IngredientFormDTO;

import jakarta.servlet.http.HttpServletRequest;

public class IngredientFormParser {
    public static List<IngredientFormDTO> parseFromRequest(HttpServletRequest req) {
        List<IngredientFormDTO> ingredients = new ArrayList<>();
        int idx = 0;
        while (true) {
            String idMp = req.getParameter("ingredientsWrapper.ingredients[" + idx + "].idMatierePremiere");
            String quantite = req.getParameter("ingredientsWrapper.ingredients[" + idx + "].quantite");
            String idUnite = req.getParameter("ingredientsWrapper.ingredients[" + idx + "].idUnite");
            if (idMp == null && quantite == null && idUnite == null) break;
            if (idMp != null && quantite != null && idUnite != null) {
                try {
                    IngredientFormDTO ing = new IngredientFormDTO();
                    ing.setIdMatierePremiere(Integer.valueOf(idMp));
                    ing.setQuantite(Double.valueOf(quantite));
                    ing.setIdUnite(Integer.valueOf(idUnite));
                    ingredients.add(ing);
                } catch (Exception e) {
                    // Ignore parsing errors for this ingredient
                }
            }
            idx++;
        }
        return ingredients;
    }
}
