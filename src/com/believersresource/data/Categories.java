package com.believersresource.data;

import java.util.Hashtable;
import com.believersresource.data.base.CategoriesBase;

public class Categories extends CategoriesBase {

	public Categories() { super(); }
	public Categories(String sql) { super(sql); }
	public Categories(String sql, Hashtable<String, Object> params) { super(sql, params); }

	public Categories buildTree(int parentId)
	{
		Categories result = this.getByParentId(parentId);
        for (Category category : result)
        {
            Categories children = buildTree(category.getId());
            if (children.size() > 0) category.setChildCategories(children);
        }
        return result;
	}
	
	public Categories getByParentId(int parentId)
    {
        Categories result = new Categories();
        for (Category category : this) { if (category.getParentId() == parentId) result.add(category); }
        return result;
    }
	
	public static Categories loadByType(String categoryType)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("categoryType", categoryType);
		return new Categories("SELECT * FROM Categories WHERE category_type=:categoryType", params);
	}
	
	
	public static Categories getBreadCrumb(Categories allCategories, Category finalCategory)
    {
        Categories result=new Categories();
        result.add(finalCategory);
        if (finalCategory.getParentId() > 0)
        {
            Category parent = allCategories.getById(finalCategory.getParentId());
            Categories parents = getBreadCrumb(allCategories, parent);
            result.addAll(parents);
        }
        return result;
    }
	
	
}