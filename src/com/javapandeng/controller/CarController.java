package com.javapandeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.javapandeng.po.Item;
import com.javapandeng.po.Car;
import com.javapandeng.service.ItemService;
import com.javapandeng.service.CarService;
import com.javapandeng.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


/*购物车控制器*/
@Controller
@RequestMapping("/car")
public class CarController {
    @Autowired
    CarService carService;
    @Autowired
    ItemService itemService;
    @RequestMapping("/exAdd")
    @ResponseBody
    public String exAdd(Car car, HttpServletRequest request)
    {
        JSONObject js = new JSONObject();
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            js.put(Consts.RES,0);
            return js.toJSONString();
        }

        //保存到购物车
        Integer userid = Integer.valueOf(attribute.toString());
        car.setUserId(userid);
        Item item = itemService.load(car.getItemId());
        String price = item.getPrice();
        Double valueof = Double.valueOf(price);
        car.setPrice(valueof);

        if(item.getZk()!=null)
        {
            valueof=valueof*item.getZk()/10;
            BigDecimal bg = new BigDecimal(valueof).setScale(2, RoundingMode.UP);
            car.setPrice(bg.doubleValue());
            valueof=bg.doubleValue();
        }

        Integer num = car.getNum();
        Double t=valueof*num;

        BigDecimal bg = new BigDecimal(valueof).setScale(2, RoundingMode.UP);
        double doubleValue = bg.doubleValue();

        car.setTotal(doubleValue+"");
        carService.insert(car);

       js.put(Consts.RES,1);
        return js.toJSONString();
    }

    /*转向我的购物车页面*/
    @RequestMapping("/findBySql")
    public String findBySql(Model model,HttpServletRequest request)
    {

        Object attribute = request.getSession().getAttribute((Consts.USERID));
        if(attribute==null){
            return "redirect:/login/uLogin";
        }

        Integer userId = Integer.valueOf(attribute.toString());
        String sql = " select * from car where user_id = "+userId+" order by id desc";
        List<Car> list =carService.listBySqlReturnEntity(sql);
        model.addAttribute("list",list);


        return "car/car";
    }

    /*删除购物车的商品*/
    @RequestMapping("/delete")
    @ResponseBody
    public  String delete(Integer id)
    {
        carService.deleteById(id);

        String resultString = "{\"result\":true}";
        return resultString;
    }

    /**/
}
