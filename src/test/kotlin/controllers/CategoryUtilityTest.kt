package controllers

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.CategoryUtility.categories
import utils.CategoryUtility.isValidCategory

internal class CategoryUtilityTest {
    @Test
    fun categoriesReturnsFullCategoriesSet(){
            Assertions.assertEquals(5, categories.size)
            Assertions.assertTrue(categories.contains("Hobby"))
            Assertions.assertTrue(categories.contains("College"))
            Assertions.assertFalse(categories.contains(""))
        }

    @Test
    fun isValidCategoryTrueWhenCategoryExists(){
            Assertions.assertTrue(isValidCategory("Hobby"))
            Assertions.assertTrue(isValidCategory("hobby"))
            Assertions.assertTrue(isValidCategory("COLLEGE"))
        }

    @Test
    fun isValidCategoryFalseWhenCategoryDoesNotExist(){
            Assertions.assertFalse(isValidCategory("Hob"))
            Assertions.assertFalse(isValidCategory("colllege"))
            Assertions.assertFalse(isValidCategory(""))
    }
}