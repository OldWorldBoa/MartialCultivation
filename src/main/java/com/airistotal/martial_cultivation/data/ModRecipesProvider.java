package com.airistotal.martial_cultivation.data;

import com.airistotal.martial_cultivation.items.books.CultivationIntroBook;
import com.airistotal.martial_cultivation.items.tools.combat.BasicStaff;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;

import java.util.function.Consumer;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        BasicStaff.getRecipeBuilder().build(consumer);
        CultivationIntroBook.getRecipeBuilder().build(consumer);
    }
}
