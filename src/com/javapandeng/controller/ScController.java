package com.javapandeng.controller;

import com.javapandeng.po.Item;
import com.javapandeng.po.Sc;
import com.javapandeng.service.ItemService;
import com.javapandeng.service.ScService;
import com.javapandeng.utils.Consts;
import com.javapandeng.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sc")
public class ScController {
    @Autowired
    ScService scService;
    @Autowired
    ItemService itemService;
    @RequestMapping("/exAdd")
    public String exAdd(Sc sc, HttpServletRequest request)
    {
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            return "redirect:/login/uLogin";
        }

        //保存到收藏表
        Integer userid = Integer.valueOf(attribute.toString());
        sc.setUserId(userid);
        scService.insert(sc);

        //商品收藏数+1
        Item item = itemService.load(sc.getItemId());
        item.setScNum(item.getScNum()+1);
        itemService.updateById(item);
        return "redirect:/sc/findBySql";
    }

    /*查看收藏列表*/
    @RequestMapping("/findBySql")
    public  String findBySql(Model model,HttpServletRequest request)
    {
        Object attribute = request.getSession().getAttribute((Consts.USERID));
        if(attribute==null){
            return "redirect:/login/uLogin";
        }
        Integer userId = Integer.valueOf(attribute.toString());
        String sql = "select * from sc where user_id="+userId+" order by id desc";
        Pager<Sc> pagers = scService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        return  "sc/my";
    }

    /*取消收藏*/
    @RequestMapping("/delete")
    public String delete(Integer id,HttpServletRequest request)
    {
        Object attribute = request.getSession().getAttribute((Consts.USERID));
        if(attribute==null){
            return "redirect:/login/uLogin";
        }
        scService.deleteById(id);
        return  "redirect:/sc/findBySql";
    }
}
