package com.spring.boot.demo.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangxiaolong on 2017/7/19.
 */

@RestController
@EnableAutoConfiguration
public class HelloController {

    @RequestMapping("/hello")
    public String index(){
        System.out.println("go into Controller");
        return "Hello this is Spring-boot Controller!";
    }

    @RequestMapping("/hello1")
    public String index2(){
        System.out.println("go into Controller");
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<style type=\"text/css\">\n" +
                "    body, div, p {\n" +
                "        font-family: 'Microsoft Yahei';\n" +
                "        font-size: 14px;\n" +
                "    }\n" +
                "\n" +
                "    .box {\n" +
                "        width: 400px;\n" +
                "        height: 400px;\n" +
                "        border: 10px solid #8bf2f1;\n" +
                "        margin: 100px auto;\n" +
                "        border-radius: 50%;\n" +
                "        box-shadow: 0px 0px 20px 3px #444 inset;\n" +
                "        position: relative;\n" +
                "    }\n" +
                "\n" +
                "    /*原点*/\n" +
                "    .origin {\n" +
                "        width: 20px;\n" +
                "        height: 20px;\n" +
                "        border-radius: 50%;\n" +
                "        background: #ff0000;\n" +
                "        top: 190px;\n" +
                "        left: 190px;\n" +
                "        position: absolute;\n" +
                "    }\n" +
                "\n" +
                "    /* 指针 */\n" +
                "    .clock_line {\n" +
                "        position: absolute;\n" +
                "        position: absolute;\n" +
                "        z-index: 20;\n" +
                "    }\n" +
                "\n" +
                "    .hour_line {\n" +
                "        width: 100px;\n" +
                "        height: 4px;\n" +
                "        top: 198px;\n" +
                "        left: 200px;\n" +
                "        background-color: #000;\n" +
                "        border-radius: 2px;\n" +
                "        transform-origin: 0 50%;\n" +
                "        box-shadow: 1px -3px 8px 3px #aaa;\n" +
                "    }\n" +
                "\n" +
                "    .minute_line {\n" +
                "        width: 130px;\n" +
                "        height: 2px;\n" +
                "        top: 199px;\n" +
                "        left: 190px;\n" +
                "        background-color: #000;\n" +
                "        transform-origin: 7.692% 50%;\n" +
                "        box-shadow: 1px -3px 8px 1px #aaa;\n" +
                "    }\n" +
                "\n" +
                "    .second_line {\n" +
                "        width: 170px;\n" +
                "        height: 1px;\n" +
                "        top: 199.5px;\n" +
                "        left: 180px;\n" +
                "        background-color: #f60;\n" +
                "        transform-origin: 11.765% 50%;\n" +
                "        box-shadow: 1px -3px 7px 1px #bbb;\n" +
                "    }\n" +
                "\n" +
                "    .dot_box {\n" +
                "        width: inherit;\n" +
                "        height: inherit;\n" +
                "    }\n" +
                "\n" +
                "    /*时钟数*/\n" +
                "    .dot {\n" +
                "        width: 40px;\n" +
                "        height: 40px;\n" +
                "        line-height: 40px;\n" +
                "        text-align: center;\n" +
                "        font-size: 22px;\n" +
                "        position: absolute;\n" +
                "    }\n" +
                "\n" +
                "    .clock-scale {\n" +
                "        width: 195px;\n" +
                "        height: 2px;\n" +
                "        transform-origin: 0% 50%;\n" +
                "        z-index: 7;\n" +
                "        position: absolute;\n" +
                "        top: 199px;\n" +
                "        left: 200px;\n" +
                "    }\n" +
                "\n" +
                "    .scale-show {\n" +
                "        width: 12px;\n" +
                "        height: 2px;\n" +
                "        background-color: #555;\n" +
                "        float: left;\n" +
                "    }\n" +
                "\n" +
                "    .scale-hidden {\n" +
                "        width: 183px;\n" +
                "        height: 2px;\n" +
                "        float: left;\n" +
                "    }\n" +
                "\n" +
                "    /*日期*/\n" +
                "    .date_info {\n" +
                "        width: 160px;\n" +
                "        height: 28px;\n" +
                "        line-height: 28px;\n" +
                "        text-align: center;\n" +
                "        position: absolute;\n" +
                "        top: 230px;\n" +
                "        left: 120px;\n" +
                "        z-index: 11;\n" +
                "        color: #555;\n" +
                "        font-weight: bold;\n" +
                "    }\n" +
                "\n" +
                "    .time_info {\n" +
                "        width: 110px;\n" +
                "        height: 35px;\n" +
                "        line-height: 35px;\n" +
                "        text-align: center;\n" +
                "        position: absolute;\n" +
                "        top: 270px;\n" +
                "        left: 150px;\n" +
                "        z-index: 11;\n" +
                "        color: #555;\n" +
                "        background: #253e3e;\n" +
                "    }\n" +
                "\n" +
                "    .time {\n" +
                "        width: 35px;\n" +
                "        height: 35px;\n" +
                "        float: left;\n" +
                "        color: #fff;\n" +
                "        font-size: 22px;\n" +
                "    }\n" +
                "\n" +
                "    #minute_time {\n" +
                "        border-left: 1px solid #fff;\n" +
                "        border-right: 1px solid #fff;\n" +
                "    }\n" +
                "\n" +
                "</style>\n" +
                "<body>\n" +
                "<div class=\"box\" id=\"clock\">\n" +
                "    <!-- 原点 -->\n" +
                "    <div class=\"origin\"></div>\n" +
                "    <!-- 时钟数 -->\n" +
                "    <div class=\"dot_box\">\n" +
                "        <div class=\"dot\">6</div>\n" +
                "        <div class=\"dot\">5</div>\n" +
                "        <div class=\"dot\">4</div>\n" +
                "        <div class=\"dot\">3</div>\n" +
                "        <div class=\"dot\">2</div>\n" +
                "        <div class=\"dot\">1</div>\n" +
                "        <div class=\"dot\">12</div>\n" +
                "        <div class=\"dot\">11</div>\n" +
                "        <div class=\"dot\">10</div>\n" +
                "        <div class=\"dot\">9</div>\n" +
                "        <div class=\"dot\">8</div>\n" +
                "        <div class=\"dot\">7</div>\n" +
                "    </div>\n" +
                "    <!-- 时、分、秒针 -->\n" +
                "    <div class=\"clock_line hour_line\" id=\"hour_line\"></div>\n" +
                "    <div class=\"clock_line minute_line\" id=\"minute_line\"></div>\n" +
                "    <div class=\"clock_line second_line\" id=\"second_line\"></div>\n" +
                "    <!-- 日期 -->\n" +
                "    <div class=\"date_info\" id=\"date_info\"></div>\n" +
                "    <!-- 时间 -->\n" +
                "    <div class=\"time_info\">\n" +
                "        <div class=\"time\" id=\"hour_time\"></div>\n" +
                "        <div class=\"time\" id=\"minute_time\"></div>\n" +
                "        <div class=\"time\" id=\"second_time\"></div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "<script type=\"text/javascript\">\n" +
                "    window.onload=function(){\n" +
                "//    $(function () {\n" +
                "        var clock = document.getElementById(\"clock\");\n" +
                "\n" +
                "        function initNumXY() {\n" +
                "            // 1、12个数字的位置设置\n" +
                "            var radius = 160;//大圆半价\n" +
                "            var dot_num = 360 / $(\".dot\").length;//每个div对应的弧度数\n" +
                "            //每一个dot对应的弧度;\n" +
                "            var ahd = dot_num * Math.PI / 180;\n" +
                "            $(\".dot\").each(function (index, el) {\n" +
                "                $(this).css({\n" +
                "                    \"left\": 180 + Math.sin((ahd * index)) * radius,\n" +
                "                    \"top\": 180 + Math.cos((ahd * index)) * radius\n" +
                "                });\n" +
                "            });\n" +
                "            // 2、刻钟设置\n" +
                "            for (var i = 0; i < 60; i++) {\n" +
                "                clock.innerHTML += \"<div class='clock-scale'> \" +\n" +
                "                    \"<div class='scale-hidden'></div>\" +\n" +
                "                    \"<div class='scale-show'></div>\" +\n" +
                "                    \"</div>\";\n" +
                "            }\n" +
                "            var scale = document.getElementsByClassName(\"clock-scale\");\n" +
                "            for (var i = 0; i < scale.length; i++) {\n" +
                "                scale[i].style.transform = \"rotate(\" + (i * 6 - 90) + \"deg)\";\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        initNumXY();//调用上面的函数\n" +
                "        //获取时钟id\n" +
                "        var hour_line = document.getElementById(\"hour_line\"),\n" +
                "            minute_line = document.getElementById(\"minute_line\"),\n" +
                "            second_line = document.getElementById(\"second_line\"),\n" +
                "            date_info = document.getElementById(\"date_info\"),//获取date_info\n" +
                "            hour_time = document.getElementById(\"hour_time\"),// 获去时间id\n" +
                "            minute_time = document.getElementById(\"minute_time\"),\n" +
                "            second_time = document.getElementById(\"second_time\");\n" +
                "\n" +
                "        //3、设置动态时间\n" +
                "        function setTime() {\n" +
                "            var nowdate = new Date();\n" +
                "            //获取年月日时分秒\n" +
                "            var year = nowdate.getFullYear(),\n" +
                "                month = nowdate.getMonth() + 1,\n" +
                "                day = nowdate.getDay(),\n" +
                "                hours = nowdate.getHours(),\n" +
                "                minutes = nowdate.getMinutes(),\n" +
                "                seconds = nowdate.getSeconds(),\n" +
                "                date = nowdate.getDate();\n" +
                "            var weekday = [\"星期日\", \"星期一\", \"星期二\", \"星期三\", \"星期四\", \"星期五\", \"星期六\"];\n" +
                "            // 获取日期id\n" +
                "            date_info.innerHTML = year + \"年\" + month + \"月\" + day + \"日   \" + weekday[day];\n" +
                "            hour_time.innerHTML = hours >= 10 ? hours : \"0\" + hours;\n" +
                "            minute_time.innerHTML = minutes >= 10 ? minutes : \"0\" + minutes;\n" +
                "            second_time.innerHTML = seconds >= 10 ? seconds : \"0\" + seconds;\n" +
                "            console.log(year + \"年\" + month + \"月\" + day + \"日   \" + weekday[day]);\n" +
                "            //时分秒针设置\n" +
                "            var hour_rotate = (hours * 30 - 90) + (Math.floor(minutes / 12) * 6);\n" +
                "            hour_line.style.transform = 'rotate(' + hour_rotate + 'deg)';\n" +
                "            minute_line.style.transform = 'rotate(' + (minutes * 6 - 90) + 'deg)';\n" +
                "            second_line.style.transform = 'rotate(' + (seconds * 6 - 90) + 'deg)';\n" +
                "        }\n" +
                "\n" +
                "        // setTime();\n" +
                "        setInterval(setTime, 1000);\n" +
                "\n" +
                "\n" +
                "     };\n" +
                "\n" +
                "</script>\n" +
                "</body>\n" +
                "\n" +
                "\n" +
                "</html>";

    }

}
