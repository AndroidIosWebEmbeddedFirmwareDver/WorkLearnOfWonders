//
//  SCEvaluationInputView.m
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/2.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import "SCEvaluationInputView.h"

#define KEY_WINDOW [UIApplication sharedApplication].keyWindow

@interface SCEvaluationInputView()

@property (nonatomic, strong) UIView *contentV;
@property (nonatomic, strong) UIView *maskView;
@property (nonatomic, strong) UIView *bottomContentV;
@property (nonatomic, strong) UIImageView *iconView;
@property (nonatomic, strong) UILabel *tipLabel;
@property (nonatomic, strong) UIView *bottomLine;


@property (nonatomic, strong) UIView *inputContentV;
@property (nonatomic, strong) UITextView *textView;
@property (nonatomic, strong) UIButton *sureBtn;

//@property (nonatomic, strong) UIView *inputView;


@end

@implementation SCEvaluationInputView

//- (instancetype)initWithFrame:(CGRect)frame
//               bottomShowView:(UIView *)bottomView
//                    inputView:(UIView *)inputView
//                   showHandle:(ShowHandle)showHandler
//                dismissHandle:(DismissHandle)dismissHandler {
//    if (self = [super initWithFrame:frame]) {
//        if (bottomView != nil) {
//            [self addSubview:bottomView];
//        }
//        
//        _inputView = inputView;
//    }
//    return self;
//}
//
//- (void)show {
//    [self setupInputView:_inputView];
//    
//    [_inputView.subviews enumerateObjectsUsingBlock:^(__kindof UIView * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
//        if ([obj conformsToProtocol:@protocol(UITextInput)]) {
//            [obj becomeFirstResponder];
//        }
//    }];
//}
//
//- (void)setupInputView:(UIView *)inputView {
//    _contentV = [self creatViewInView:KEY_WINDOW withFrame:[UIScreen mainScreen].bounds backgroundColor:nil action:NULL];
//    
//    
//    //
//    [[NSNotificationCenter defaultCenter] addObserver:self
//                                             selector:@selector(keyBoardWillShow:)
//                                                 name:UIKeyboardWillShowNotification
//                                               object:nil];
//    [[NSNotificationCenter defaultCenter] addObserver:self
//                                             selector:@selector(keyBoardWillHidden:)
//                                                 name:UIKeyboardWillHideNotification
//                                               object:nil];
//    _maskView = [self creatViewInView:self.contentV
//                            withFrame:[UIScreen mainScreen].bounds
//                      backgroundColor:[UIColor colorWithWhite:0.2 alpha:0.5]
//                               action:@selector(hiddenInputView)];
//    _maskView.alpha = 0;
//    [_contentV sendSubviewToBack:_maskView];
//}



- (instancetype)initWithFrame:(CGRect)frame {
    if (self = [super initWithFrame:frame]) {
        [self setupSubvews];
        
        self.backgroundColor = [UIColor bc1Color];
    }
    
    return self;
}

- (void)setupSubvews {
    //
    _bottomContentV = [[UIView alloc] initWithFrame:CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, 44)];
    _bottomContentV.backgroundColor = [UIColor bc2Color];
    [self addSubview:_bottomContentV];;
    
    //
    _iconView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"icon评论"]];
    _iconView.frame = CGRectMake(20, 10, 20, 20);
    [_bottomContentV addSubview:_iconView];
    
    //
    CGFloat tipLabelX = CGRectGetMaxX(_iconView.frame) + 10;
    CGFloat tipLabelY = 0;
    CGFloat tipLabelW = [UIScreen mainScreen].bounds.size.width - tipLabelX -  20;
    CGFloat tipLabelH = _bottomContentV.frame.size.height;
    _tipLabel = [[UILabel alloc] initWithFrame:CGRectMake(tipLabelX, tipLabelY,  tipLabelW, tipLabelH)];
    _tipLabel.textColor = [UIColor tc2Color];
    _tipLabel.font = [UIFont systemFontOfSize:16];
    _tipLabel.text = @"添加评论";
    _tipLabel.userInteractionEnabled = YES;
    [_tipLabel addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(showInputView)]];
    [self addSubview:_tipLabel];
    
    //
    _bottomLine = [[UIView alloc] initWithFrame:CGRectMake(_iconView.left,
                                                           CGRectGetMaxY(_iconView.frame) + 6.5,
                                                           CGRectGetMaxX(_tipLabel.frame) - _iconView.left ,
                                                           0.5)];
    _bottomLine.backgroundColor = [UIColor tc7Color];
    [self addSubview:_bottomLine];

}



- (void)showInputView {
    [self setupInputView];
    [_textView becomeFirstResponder];
}


- (void)hiddenInputView {
    [_textView resignFirstResponder];
}


- (void)setupInputView {
    _contentV = [self creatViewInView:KEY_WINDOW withFrame:[UIScreen mainScreen].bounds backgroundColor:nil action:NULL];
    
    // 遮罩
    _maskView = [self creatViewInView:self.contentV
                            withFrame:[UIScreen mainScreen].bounds
                      backgroundColor:[UIColor colorWithWhite:0.2 alpha:0.5]
                               action:@selector(hiddenInputView)];
    _maskView.alpha = 0;
    
    // 输入内容容器
    _inputContentV = [self creatViewInView:self.contentV
                                 withFrame:CGRectMake(0,
                                                      [UIScreen mainScreen].bounds.size.height,
                                                      [UIScreen mainScreen].bounds.size.width,
                                                      90)
                           backgroundColor:[UIColor whiteColor]
                                    action:NULL];
    
    // TextView
    CGFloat textViewX = 15;
    CGFloat textViewY = 15;
    CGFloat textViewW = _inputContentV.frame.size.width - 15 - 58;
    CGFloat textViewH = 60;
    _textView = [[UITextView alloc] initWithFrame:CGRectMake(textViewX, textViewY, textViewW, textViewH)];
    _textView.textColor = [UIColor tc1Color];
    _textView.font = [UIFont systemFontOfSize:16];
    _textView.showsHorizontalScrollIndicator = NO;
    _textView.placeholder = @"请输入您的评价内容";
    [_inputContentV addSubview:_textView];
    
    //
    UIView *separatorLine = [[UIView alloc] initWithFrame:CGRectMake(_textView.frame.origin.x,
                                                            CGRectGetMaxY(_textView.frame),
                                                            _textView.frame.size.width,
                                                            0.5)];
    separatorLine.backgroundColor = [UIColor bc7Color];
    [_inputContentV addSubview:separatorLine];
    
    
    // 确认按钮
    _sureBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    CGFloat sureBtnX = CGRectGetMaxX(_textView.frame);
    CGFloat sureBtnY = _textView.frame.origin.y;
    CGFloat sureBtnW = _inputContentV.frame.size.width - CGRectGetMaxX(_textView.frame);
    CGFloat sureBtnH = _textView.frame.size.height;
    _sureBtn.frame = CGRectMake(sureBtnX, sureBtnY, sureBtnW, sureBtnH);
    [_sureBtn setTitle:@"提交" forState:UIControlStateNormal];
    [_sureBtn setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
    [_sureBtn.titleLabel setFont:[UIFont systemFontOfSize:14]];
    [_sureBtn addTarget:self action:@selector(sureAction) forControlEvents:UIControlEventTouchUpInside];
    [_inputContentV addSubview:_sureBtn];
    
    
    //
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyBoardWillShow:)
                                                 name:UIKeyboardWillShowNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyBoardWillHidden:)
                                                 name:UIKeyboardWillHideNotification
                                               object:nil];
    
}


- (void)sureAction {
    if (self.sureHandler) {
        self.sureHandler(_textView.text);
    }
}


- (void)dismiss {
    [_textView resignFirstResponder];
}


- (void)keyBoardWillShow:(NSNotification *)notif {
    NSDictionary *userInfo = notif.userInfo;
    NSNumber *duration = userInfo[@"UIKeyboardAnimationDurationUserInfoKey"];
    NSValue *rectValue = userInfo[@"UIKeyboardBoundsUserInfoKey"];
    CGRect rect = [rectValue CGRectValue];
    
    [UIView animateWithDuration:[duration floatValue] animations:^{
        self.maskView.alpha = 1;
        
        CGRect inputCVFrame   = _inputContentV.frame;
        inputCVFrame.origin.y = [UIScreen mainScreen].bounds.size.height - rect.size.height - inputCVFrame.size.height;
        _inputContentV.frame  = inputCVFrame;
    }];
}


-(void)keyBoardWillHidden:(NSNotification *)notif {
    NSDictionary *userInfo = notif.userInfo;
    NSNumber *duration = userInfo[@"UIKeyboardAnimationDurationUserInfoKey"];
    
    [UIView animateWithDuration:[duration floatValue] animations:^{
        self.maskView.alpha = 0;
        
        CGRect inputCVFrame   = _inputContentV.frame;
        inputCVFrame.origin.y = [UIScreen mainScreen].bounds.size.height;
        _inputContentV.frame  = inputCVFrame;
        
    } completion:^(BOOL finished) {
        [_contentV removeFromSuperview];
        [[NSNotificationCenter defaultCenter] removeObserver:self];
    }];
}



- (UIView *)creatViewInView:(UIView *)inView withFrame:(CGRect)rect backgroundColor:(UIColor *)bgColor action:(SEL)action{
    UIView *v = [[UIView alloc] initWithFrame:rect];
    if (bgColor) {
        v.backgroundColor = bgColor;
    }
    if (inView) {
        [inView addSubview:v];
    }
    
    [v addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:action]];
    
    return v;
}
@end
