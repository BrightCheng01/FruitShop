package com.javapandeng.controller;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.javapandeng.base.BaseController;
import com.javapandeng.po.*;
import com.javapandeng.service.ItemCategoryService;
import com.javapandeng.service.ItemService;
import com.javapandeng.service.ManageService;
import com.javapandeng.service.UserService;
import com.javapandeng.service.impl.ManageServiceImpl;
import com.javapandeng.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/*登录控制器*/
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    ManageService manageService;
    @Autowired
    ItemService itemService;
    @Autowired
    UserService userService;
    @Autowired
    private ItemCategoryService itemCategoryService;

    /*管理员登录前*/
    @RequestMapping("login")
    public String login()
    {
        return "/login/mLogin";
    }

    /*登录验证*/
    @RequestMapping("toLogin")
    public String toLogin(Manage manage, HttpServletRequest request)
    {

        System.out.println(manageService);
        System.out.println("首先执行");
       Manage byEntity = manageService.getByEntity(manage);
        System.out.println("dsadsad");
        System.out.println(manageService);
        System.out.println("dsadsad");
        if(byEntity==null)
        {
            return "redirect:/login/mlogout";
        }
        request.getSession().setAttribute(Consts.MANAGE,byEntity);
        return "/login/mIndex";
    }

    /*管理员退出*/
    @RequestMapping("mlogout")
    public String mlogout(HttpServletRequest request)
    {
        request.getSession().setAttribute(Consts.MANAGE,null);
        return "/login/mLogin";
    }

    /*前端首页*/
    @RequestMapping("/uIndex")
    public String uIndex(Model model, Item item,HttpServletRequest request)
    {
        //找到所有父类目
        String sql1 = "select * from item_category where isDelete = 0 " +
                " and pid is null order by name ";
        List<ItemCategory> fatherList =itemCategoryService.listBySqlReturnEntity(sql1);

        List<CategoryDto> list = new ArrayList<>();
        if(!CollectionUtils.isEmpty(fatherList))
        {
            for(ItemCategory ic:fatherList)
            {

                CategoryDto dto = new CategoryDto();
                dto.setFather(ic);   //设置弗雷目

                //c查询二级分类（找父类目的儿子们）
                String sql2 = " select * from item_category where isDelete=0 and pid = " +
                        ic.getId();
                List<ItemCategory> childrens = itemCategoryService.listBySqlReturnEntity(sql2);
                dto.setChildrens(childrens);

                list.add(dto);
                model.addAttribute("lbs" ,list);
            }
        }

        //取前十个折扣商品
        List<Item> zks= itemService.listBySqlReturnEntity("select * from item where isDelete = 0 and zk is not null order by zk desc limit 0 ,10");
        model.addAttribute("zks",zks);

        //热销商品
        List<Item> rxs= itemService.listBySqlReturnEntity("select * from item where isDelete = 0  order by gmNum desc limit 0 ,10");
        model.addAttribute("rxs",rxs);

        return "login/uIndex";
    }

    /*跳转到普通用户注册*/
    @RequestMapping("/res")
    public String res(){
        return "login/res";
    }
    /*执行普通用户注册*/
    @RequestMapping("/toRes")
    public String toRes(User user){
        userService.insert(user);
        return "login/uLogin";
    }

    /*普通用户登录功能*/
    @RequestMapping("/uLogin")
    public String uLogin()
    {
        return "login/uLogin";
    }


    /*执行普通用户登录*/
    @RequestMapping("/utoLogin")
    public String utoLogin(User user,HttpServletRequest request){
       User byEntity = userService.getByEntity(user);
       if(byEntity==null){
           return "redirect:/login/res";
       }
       else{
           request.getSession().setAttribute("role",2);
           request.getSession().setAttribute(Consts.USERNAME,byEntity.getUserName());
           request.getSession().setAttribute(Consts.USERID,byEntity.getId());

           return "redirect:/login/uIndex";
       }

    }

    /*会员退出*/
    @RequestMapping("uLogout")
    public String uLogout(HttpServletRequest request)
    {
        HttpSession session=request.getSession();
        session.invalidate();
        return "redirect:/login/uIndex";
    }

    /*修改密码入口*/
    @RequestMapping("/pass")
    public String pass(HttpServletRequest request)
    {
        Object attribute = request.getSession().getAttribute((Consts.USERID));
        if(attribute==null){
            return "redirect:/login/uLogin";
        }
        Integer userId = Integer.valueOf(attribute.toString());
        User load =userService.load(userId);
        request.setAttribute("obj",load);
        return "login/pass";
    }

    /*修改密码执行*/
    @RequestMapping("/upass")
    @ResponseBody
    public String upass(String password,HttpServletRequest request)
    {
        JSONObject js =new JSONObject();
        Object attribute = request.getSession().getAttribute((Consts.USERID));
        if(attribute==null){
            js.put(Consts.RES,0);
            return js.toString();
        }
        Integer userId = Integer.valueOf(attribute.toString());
        User load =userService.load(userId);
        load.setPassWord(password);
        userService.updateById(load);
        request.setAttribute("obj",load);
        js.put(Consts.RES,1);
        return js.toString();
    }
}
