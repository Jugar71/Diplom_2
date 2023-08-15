package serialization;

import java.util.List;

public class OrderCreateSerialization {
    private List<String> ingredients;

    public OrderCreateSerialization() {}

    public OrderCreateSerialization(List<String> ingredients) { this.ingredients = ingredients; }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
