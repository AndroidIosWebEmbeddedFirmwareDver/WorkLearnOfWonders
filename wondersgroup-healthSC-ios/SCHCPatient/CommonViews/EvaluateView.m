//
//  EvaluateView.m
//  SCHCPatient
//
//  Created by luzhongchang on 16/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

/* how to use
     [EvaluateView createEvaluateView:@"对本次服务评价" buttoncancelTitle:@"取消" committitle:@"提交" starsNum:5 type:EvaluateViewTwo  cancelAction:^(id view) {
     [view removeFromSuperview];
     } commitAction:^(NSString *string, NSInteger selectNumber ,EvaluateView * view) {
     NSLog(@"%@ %ld",string ,(long)selectNumber);
     
     [view removeFromSuperview];
     }];
 
 */


#import "EvaluateView.h"
@implementation StarBarView

-(id)initWithFrame:(CGRect)frame withNumber:(int)number
{
    self = [super initWithFrame:frame];
    if(self)
    {
        
        self.index=-1;
        //width 24*number
        //height 24;
        StarArray = [NSMutableArray new];
        for (int i =0 ; i< number;  i++)
        {
            UIImageView * imageview = [[UIImageView alloc] initWithFrame:CGRectMake( 4+ i * 20, 4, 16, 16)];
            imageview.image =[UIImage imageNamed:@"stargray"];
            [self addSubview:imageview];
            UIButton * button = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 24, 24)];
            button.tag =i;
            [button addTarget:self action:@selector(buttonres:) forControlEvents:UIControlEventTouchUpInside];
            button.center = imageview.center;
            [self addSubview:button];
            
            [StarArray addObject:imageview];
        
        }
    }
    return self;
    
}


-(void)buttonres:(UIButton *)sender
{
    
    for (int i=0 ; i< StarArray.count; i++)
    {
        UIImageView *imagev = [StarArray objectAtIndex:i];
        if(i<=sender.tag)
        {
            imagev.image = [UIImage imageNamed:@"starselect"];
        }
        else
            imagev.image = [UIImage imageNamed:@"stargray"];
    }
    
    self.index = sender.tag;
}
@end



@implementation EvaluateView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

-(id)initWithPramas:(NSString *) title starsNum:(int) starsNum type:(EvaluateViewType)type
{
    self = [super initWithFrame:SCREEN_BOUNDS];
    if(self)
    {
        self.backgroundColor = [[UIColor alloc] initWithRed:0 green:0 blue:0 alpha:0.5];
        [self setUpView:title starsNum:starsNum type:type];
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(textChanged:) name:UITextViewTextDidChangeNotification object:nil];
        [self bindRac];
    }
    return self;
    
}


-(void)bindRac
{

//    RAC(self , fontstr)  =self.mTextview.rac_textSignal;
    
    WS(weakSelf);
    [self.mTextview.rac_textSignal subscribeNext:^(NSString * text){
        weakSelf.fontlabel.text = [NSString stringWithFormat:@"%lu/149",(unsigned long)text.length>149?149:(unsigned long)text.length];
    }];
    
    
    
    
}
-(void) setUpView:(NSString *) title starsNum:(int) starsNum type:(EvaluateViewType)type
{

    self.BgView = [[UIView alloc] initWithFrame:CGRectZero];
    self.BgView.backgroundColor =[UIColor whiteColor];
    [self addSubview: self.BgView];
    self.BgView.layer.masksToBounds=YES;
    self.BgView.layer.cornerRadius=5;

    self.BgView.frame = CGRectMake(16, SCREEN_HEIGHT, SCREEN_WIDTH-32, 240);
    
   
   
    
    self.titleLabel = [self setUpLabel:CGRectZero text:title font:[UIFont systemFontOfSize:18] textColor:[UIColor tc1Color] textAlignment:NSTextAlignmentCenter parent:self.BgView];
    [self.titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.BgView);
        make.top.equalTo(self.BgView).offset(21);
        make.height.equalTo(@18);
    }];
    
    self.mTextview = [[UITextView alloc] initWithFrame:CGRectZero];
    self.mTextview.delegate =self;
    self.mTextview.font = [UIFont systemFontOfSize:16];
    self.mTextview.textColor = [UIColor tc1Color];
    [self.BgView addSubview:self.mTextview];
   
    
    if(starsNum !=0)
    {
        self.starView = [[StarBarView alloc] initWithFrame:CGRectZero withNumber:starsNum];
        [self.BgView addSubview:self.starView];
        
        [self.starView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.centerX.equalTo(self.BgView.mas_centerX);
            make.top.equalTo(self.titleLabel.mas_bottom).offset(11);
            make.height.equalTo(@24);
            make.width.mas_equalTo(24*5);
        }];
        
      
        [self.mTextview mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(self.BgView).offset(17);
            make.right.equalTo(self.BgView).offset(-17);
            make.top.equalTo(self.starView.mas_bottom).offset(23);
            make.height.equalTo(@48);
            
        }];
    }
    else
    {
        
     
        [self.mTextview mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(self.BgView).offset(17);
            make.right.equalTo(self.BgView).offset(-17);
            make.top.equalTo(self.titleLabel.mas_bottom).offset(11);
            make.height.equalTo(@48);
            
        }];
    
    }
    
  
    self.fontlabel = [self setUpLabel:CGRectZero text:title font:[UIFont systemFontOfSize:12] textColor:[UIColor tc4Color] textAlignment:NSTextAlignmentCenter parent:self.BgView];
    [self.fontlabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(self.mTextview.mas_right);
        make.top.equalTo(self.mTextview.mas_bottom).offset(5);
        make.height.equalTo(@12);
    }];
    
    
    
    
    
    
    self.line = [[UIView alloc] initWithFrame:CGRectZero];
    self.line.backgroundColor =[UIColor tc5Color];
    [self.BgView addSubview:self.line];
    [self.line mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.BgView.mas_left).offset(15);
        make.right.equalTo(self.BgView.mas_right).offset(-15);
        make.height.equalTo(@0.5);
        make.top.equalTo(self.fontlabel.mas_bottom).offset(5);
    }];
    
    
    [self.BgView mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.mas_left).offset(16);
        make.right.equalTo(self.mas_right).offset(-16);
        make.centerY.equalTo(self.mas_centerY);
        make.bottom.equalTo(self.line.mas_bottom).offset(44);
    }];
    
    
    
    if(type ==EvaluateViewOne)
    {
        self.cancelLabel =[self setUpLabel:CGRectZero text:@"取消" font:[UIFont systemFontOfSize :16] textColor:[UIColor tc3Color] textAlignment:NSTextAlignmentCenter parent:self.BgView];
        [self.cancelLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.right.equalTo(self.BgView);
            make.top.equalTo(self.line.mas_bottom);
             make.height.equalTo(@44);
        }];
        
        
        
        UIButton * cancelbutton = [[UIButton alloc] initWithFrame:CGRectZero];
        [self.BgView addSubview:cancelbutton];
        [cancelbutton mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.right.top.bottom.equalTo(self.cancelLabel);
        }];
        
        [[cancelbutton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^( id x) {
            
            WS(weakSelf);
            if(self.cancelblock)
            {
                
                [UIView animateWithDuration:0.3 delay:0 options:UIViewAnimationOptionCurveEaseOut animations:^{
                    
                    self.BgView.frame = CGRectMake(16, SCREEN_HEIGHT, SCREEN_WIDTH-32, 240);
                } completion:^(BOOL finished) {
                    
                    [[NSNotificationCenter defaultCenter] removeObserver:self];
                    weakSelf.cancelblock(weakSelf);
                }];
            }
            
        }];
        
      
        
        
        
    }
    else
    {
        
        self.cancelLabel =[self setUpLabel:CGRectZero text:@"取消" font:[UIFont systemFontOfSize :16] textColor:[UIColor tc3Color] textAlignment:NSTextAlignmentCenter parent:self.BgView];
        [self.cancelLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(self.BgView.mas_left);
            make.right.equalTo(self.BgView.mas_centerX);
            make.top.equalTo(self.line.mas_bottom);
            make.height.equalTo(@44);
        }];
        
        UIButton * cancelbutton = [[UIButton alloc] initWithFrame:CGRectZero];
        [self.BgView addSubview:cancelbutton];
        [cancelbutton mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.right.top.bottom.equalTo(self.cancelLabel);
        }];
        
        [[cancelbutton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^( id x) {
            
            WS(weakSelf);
            if(self.cancelblock)
            {
                
                
                [UIView animateWithDuration:0.3 delay:0 options:UIViewAnimationOptionCurveEaseOut animations:^{
                    
                    self.BgView.frame = CGRectMake(16, SCREEN_HEIGHT, SCREEN_WIDTH-32, 240);
                } completion:^(BOOL finished) {
                    [[NSNotificationCenter defaultCenter] removeObserver:self];
                    weakSelf.cancelblock(weakSelf);
                }];
                
                
                
                
            }
            
        }];
        
        
        
        self.commitlabel =[self setUpLabel:CGRectZero text:@"提交" font:[UIFont systemFontOfSize :16] textColor:[UIColor tc5Color] textAlignment:NSTextAlignmentCenter parent:self.BgView];
        [self.commitlabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(self.BgView.mas_centerX);
            make.right.equalTo(self.BgView.mas_right);
            make.top.equalTo(self.line.mas_bottom);
            make.height.equalTo(@44);
        }];
        
        
        UIButton * commitbutton = [[UIButton alloc] initWithFrame:CGRectZero];
        [self.BgView addSubview:commitbutton];
        [commitbutton mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.right.top.bottom.equalTo(self.commitlabel);
        }];
        
        [[commitbutton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^( id x) {
            
            WS(weakSelf);
            if(self.commitblock)
            {
                
                NSString *trimStr = [weakSelf.mTextview.text stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
              
                
                if (trimStr.length==0 || trimStr.length == 0) {
                    
                    [MBProgressHUDHelper showHudWithText:@"请输入评价内容!"];
                    
                    return ;
                }
                
                
                if (weakSelf.mTextview.text.length<=0)
                {
                    [[NSNotificationCenter defaultCenter] removeObserver:self];
                 weakSelf.commitblock(weakSelf.mTextview.text,  weakSelf.starView==nil?0:  weakSelf.starView.index+1, weakSelf);
                    return ;
                }
            
                
                [UIView animateWithDuration:0.3 delay:0 options:UIViewAnimationOptionCurveEaseOut animations:^{
                    
                    self.BgView.frame = CGRectMake(16, SCREEN_HEIGHT, SCREEN_WIDTH-32, 240);
                } completion:^(BOOL finished) {
                    [[NSNotificationCenter defaultCenter] removeObserver:self];
                    weakSelf.commitblock(weakSelf.mTextview.text,  weakSelf.starView==nil?0:  weakSelf.starView.index+1, weakSelf);
                }];
                
                
            }
            
        }];
        
        
    
    }
    
    
    
    
    
    
    //action
    
    [UIView animateWithDuration:0.3 delay:0 options:UIViewAnimationOptionCurveEaseOut animations:^{
        
        self.BgView.centerY = self.centerY;
    } completion:nil];
    
  
    
    WS(weakSelf);
    self.tappedBlock=^(UITapGestureRecognizer* ges)
    {
        
        if(weakSelf.cancelblock)
        {
            
//            
//            [UIView animateWithDuration:0.3 delay:0 options:UIViewAnimationOptionCurveEaseOut animations:^{
//                
//                weakSelf.BgView.frame = CGRectMake(16, SCREEN_HEIGHT, SCREEN_WIDTH-32, 240);
//            } completion:^(BOOL finished) {
//                weakSelf.cancelblock(weakSelf);
//            }];
//            
            
            
            
        }

        
    };
    
    
    
    
    
    
}
-(UILabel *) setUpLabel:(CGRect)frame text:(NSString*)text font:(UIFont*)font textColor:(UIColor*)textcolor textAlignment:(NSTextAlignment) textAlignment parent:(UIView*)parentview
{
    UILabel * label = [[UILabel alloc] initWithFrame:frame];
    label.font = font;
    label.textColor = textcolor;
    label.textAlignment = textAlignment;
    label.text = text;
    [parentview addSubview:label];
    return label;
}



+(void) createEvaluateView:(NSString *)title buttoncancelTitle:(NSString *) cancelTitle committitle:(NSString *)committitle  starsNum:(int) starsNum type:(EvaluateViewType)type cancelAction:(CancelBlock)cn commitAction:(CommitBlock)cm
{
    EvaluateView * v = [[EvaluateView alloc] initWithPramas:title starsNum:starsNum type:type];
    v.cancelLabel.text = cancelTitle;
    v.commitlabel.text  = committitle;
    [[UIApplication sharedApplication].keyWindow addSubview:v];
    v.cancelblock = cn;
    v.commitblock = cm;
    
}

-(BOOL)textViewShouldBeginEditing:(UITextView *)textView
{

    
        [self.BgView mas_remakeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(self.mas_left).offset(16);
            make.right.equalTo(self.mas_right).offset(-16);
            make.centerY.equalTo(self.mas_centerY).offset(-70);
            make.bottom.equalTo(self.line.mas_bottom).offset(44);
        }];
    return YES;
}

- (BOOL)textView:(UITextView *)textView shouldChangeTextInRange:(NSRange)range replacementText:(NSString *)text
{

    if(textView.text.length>=149)
    {
        textView.text = [textView.text substringWithRange:NSMakeRange(0, 149)];
    }
    return YES;
}


-(BOOL)textViewShouldEndEditing:(UITextView *)textView
{
   

    [self.BgView mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.mas_left).offset(16);
        make.right.equalTo(self.mas_right).offset(-16);
        make.centerY.equalTo(self.mas_centerY);
        make.bottom.equalTo(self.line.mas_bottom).offset(44);
    }];
    
    return YES;
}


- (void)textChanged:(NSNotification *)notification
{
   
    UITextView *textView = (UITextView *)notification.object;
    if(textView.text.length>=149)
    {
        textView.text = [textView.text substringWithRange:NSMakeRange(0, 149)];
    }
   
}




@end
