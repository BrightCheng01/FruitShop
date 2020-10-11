package com.javapandeng.controller;

import com.javapandeng.base.BaseController;
import com.javapandeng.po.ItemCategory;
import com.javapandeng.service.ItemCategoryService;
import com.javapandeng.utils.Pager;
import com.javapandeng.utils.SystemContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*类目控制层*/
@Controller
@RequestMapping("/itemCategory")
public class ItemCategoryController extends BaseController {

    @Autowired
    ItemCategoryService itemCategoryService;

    /*分页查询类目列表*/
@RequestMapping("/findBySql")
    public String findBySql(Model model,ItemCategory itemCategory)
    {
        Integer pageSize = SystemContext.getPageSize();
        Integer pageOffset = SystemContext.getPageOffset();
        System.out.println(pageSize+"s"+pageOffset);
        String sql ="select * from item_Category where isDelete = 0 and pid is null order by id";
        Pager<ItemCategory> pagers = itemCategoryService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",itemCategory);
        return "itemCategory/itemCategory";
    }
    /*转向到新增一级分类的页面*/
    @RequestMapping("/add")
    public String add()
    {
        return "itemCategory/add";
    }

    /*新增一级类目保存功能*/
    @RequestMapping("/exAdd")
    public String exAdd(ItemCategory itemCategory)
    {
        itemCategory.setIsDelete(0);
        itemCategoryService.insert(itemCategory);
        return "redirect:/itemCategory/findBySql";//可跳到findBySqlaction
    }

    /*转向修改一级分类的页面*/
    @RequestMapping("/update")
    public String update(Integer id,Model model)
    {

        ItemCategory obj = itemCategoryService.load(id);/*获取对应id的字段*/
        model.addAttribute("obj",obj);
        System.out.println(obj.getName());
        return "itemCategory/update";
    }

    /*修改一级分类*/
   @RequestMapping("exUpdate")
    public String exupdate(ItemCategory itemCategory)
   {
       itemCategoryService.updateById(itemCategory);
       return "redirect:/itemCategory/findBySql";
   }

   /*删除类目*/
   @RequestMapping("/delete")
   public String delete(Integer id){
       //删除本身
       ItemCategory load = itemCategoryService.load(id);
       load.setIsDelete(1);
       itemCategoryService.updateById(load);
       //将下级也删除
       String sql = "update item_category set isDelete=1 where pid="+id;
       itemCategoryService.updateBysql(sql);
       return "redirect:/itemCategory/findBySql";
   }
   /*查看二级分类*/
   @RequestMapping("/findBySql2")
   public String findBySql2(ItemCategory itemCategory,Model model){
       String sql = "select * from item_category where isDelete=0 and pid="+itemCategory.getPid()+" order by id";
       Pager<ItemCategory> pagers = itemCategoryService.findBySqlRerturnEntity(sql);

       model.addAttribute("pagers",pagers);
       model.addAttribute("obj",itemCategory);
       return "itemCategory/itemCategory2";
   }

   /*新增二级类目*/
    @RequestMapping("/add2")
    public String add2(int pid ,Model model)
    {
        model.addAttribute("pid",pid);
        return "itemCategory/add2";
    }

    /*新增二级类目保存功能*/
    @RequestMapping("/exAdd2")
    public String exAdd2(ItemCategory itemCategory)
    {
        itemCategory.setIsDelete(0);
        itemCategoryService.insert(itemCategory);
        return "redirect:/itemCategory/findBySql2?pid="+itemCategory.getPid();//可跳到findBySqlaction
    }

    /*转向修改二级分类的页面*/
    @RequestMapping("/update2")
    public String update2(Integer id,Model model)
    {

        ItemCategory obj = itemCategoryService.load(id);/*获取对应id的字段*/
        model.addAttribute("obj",obj);
        System.out.println(obj.getName());
        return "itemCategory/update2";
    }

    /*修改二级分类*/
    @RequestMapping("exUpdate2")
    public String exupdate2(ItemCategory itemCategory)
    {
        itemCategoryService.updateById(itemCategory);
        return "redirect:/itemCategory/findBySql2?pid="+itemCategory.getPid();
    }


    /*删除二级类目*/
    @RequestMapping("/delete2")
    public String delete2(Integer id,Integer pid){
        //删除本身
        System.out.println("输出");
        ItemCategory load = itemCategoryService.load(id);
        load.setIsDelete(1);
        itemCategoryService.updateById(load);

        return "redirect:/itemCategory/findBySql2?pid="+pid;
    }
}
