//
//  EvaluateView.h
//  SCHCPatient
//
//  Created by luzhongchang on 16/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef NS_ENUM(NSInteger,EvaluateViewType) {
    EvaluateViewOne = 0,
    EvaluateViewTwo,
};


typedef void(^ButtonBlock)(NSInteger num);
@interface StarBarView : UIView
{
    NSMutableArray * StarArray;
}
@property(nonatomic ,assign) NSInteger  index;
-(id)initWithFrame:(CGRect)frame withNumber:(int)number;
@end


typedef void(^CommitBlock)(NSString * string , NSInteger selectNumber, id view);
typedef void(^CancelBlock)(id  view );

@interface EvaluateView : UIView<UITextViewDelegate>
@property(nonatomic ,strong) UILabel * titleLabel;
@property(nonatomic ,strong) StarBarView * starView;
@property(nonatomic,strong)  UIView *BgView;
@property(nonatomic ,strong) UITextView * mTextview;

@property(nonatomic ,strong) UILabel * cancelLabel;
@property(nonatomic ,strong) UILabel * commitlabel;
@property(nonatomic,strong) UIView * line;

@property(nonatomic ,strong) UILabel * fontlabel;

@property(nonatomic ,strong) NSString *fontstr;

@property(nonatomic ,copy) CommitBlock commitblock;
@property(nonatomic ,copy) CancelBlock cancelblock;
+(void) createEvaluateView:(NSString *)title buttoncancelTitle:(NSString *) cancelTitle committitle:(NSString *)committitle  starsNum:(int) starsNum type:(EvaluateViewType)type cancelAction:(CancelBlock)cn commitAction:(CommitBlock)cm ;

@end
