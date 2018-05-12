/**
 * 作者：Bililovy
 *
 * 最后更新时间：2015.09.22
 *
 * 文字动画插件，帮助更好的解决动画效果，使用时，只需将html元素以ID命名，修改此页面
 * 的元素设置，可以自行增删动画和指定动画效果
 */
(function($) {
	"use strict";
		var isOn=0,sets, fx, toAnimate = "#effect", settings = {
			animation:8, //动画方式 从1-15 每个数字代表一个动画 请自测效果
			animationType: "in",//显示方式 in 和out 参数，in代表入场,out代表出场
			backwards: false, //改变动画显示方向，默认false 设为true 动画将从后往前倒序播放
			easing: "easeOutQuint", //这里的效果 可以参见http://code.ciaoca.com/jquery/easing/
			speed: 1000, //动画帧速 即在指定时间内完成动画
			sequenceDelay: 100, //场景延迟  即执行完上一个动画后，等待指定时间继续执行下一动画
			startDelay: 0, //开始延迟，即执行当前动画时，等待指定时间后再开始
			offsetX: 100,//动画元素 x坐标偏移量
			offsetY: 50,//动画元素 Y坐标偏移量
			// onComplete: doThis, //回调函数 即 执行完动画后，需要执行的回调方法
			restoreHTML: true  //重置元素 即重复播放两次该动画 类似于此效果 默认true
		};

		jQuery(document).ready(function() {
			fx = jQuery("#effect");
			jQuery.cjTextFx(settings);
			jQuery.cjTextFx.animate(toAnimate);
		});


})(jQuery);
