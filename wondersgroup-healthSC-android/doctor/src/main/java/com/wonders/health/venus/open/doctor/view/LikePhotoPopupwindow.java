package com.wonders.health.venus.open.doctor.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wonders.health.venus.open.doctor.R;


/**
 *popwindows
 * @author lj
 * createTime: 2017-6-6
 *
 */
public class LikePhotoPopupwindow {
	
private Context  context;//上下文
private  LinearLayout ll_popup;
private  PopupWindow pop = null;
private  LayoutInflater  inflater;
private PopClickMethod   clickMethod;
	private Button bt_hospitalization;
	private Button door;
	private Button cancel;


	public LikePhotoPopupwindow(Context  context) {
	// TODO Auto-generated constructor stub
	   this.context=context;
	   inflater=LayoutInflater.from(this.context);
	   init();
   }
   public void showPop(View  parent) {

		pop.setAnimationStyle(R.style.Popwindowstyle);
		pop.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
	}
	
	public void init(){
		
		pop = new PopupWindow(context);

		View view = inflater.inflate(R.layout.likephoto_item_popupwindows, null);
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (pop!=null&& pop.isShowing()) {
					pop.dismiss();
				}
			}
		});

		ll_popup = (LinearLayout) view.findViewById(R.id.report_ll_popup);

		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		bt_hospitalization = (Button)view.findViewById(R.id.bt_hospitalization);
		door = (Button)view.findViewById(R.id.bt_door);
		cancel = (Button)view.findViewById(R.id.bt_cancel);



		bt_hospitalization.setOnClickListener(listener);
		door.setOnClickListener(listener);
		cancel.setOnClickListener(listener);

	}
	





	
	private OnClickListener  listener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.bt_hospitalization:
				if (pop!=null) {
					pop.dismiss();
				}
				if (clickMethod!=null) {
					clickMethod.hospitalization();
				}
				break;
			case R.id.bt_door:
				if (pop!=null) {
					pop.dismiss();
				}
				if (clickMethod!=null) {
					clickMethod.door();
				}
				break;
				case R.id.bt_cancel:
					if (pop!=null) {
						pop.dismiss();
					}
					if (clickMethod!=null) {
						clickMethod.cancel();
					}
					break;

			}
		}
	};
	

	public void setCallback(PopClickMethod  clickMethod){
		this.clickMethod=clickMethod;
	}

	public abstract  class PopClickMethod{
		public  abstract void hospitalization();
		public  abstract void door();
		public abstract  void  cancel();

	}
	
	
	
	

}
