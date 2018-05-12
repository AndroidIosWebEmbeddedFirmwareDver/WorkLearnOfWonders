//
//  HCSetupView.m
//  HCPatient
//
//  Created by ZJW on 16/3/16.
//  Copyright © 2016年 陈刚. All rights reserved.
//

#import "UISetupView.h"

@implementation UISetupView

+ (UILabel *) setupLabelWithSuperView:(UIView *)view withText:(NSString *)text withTextColor:(UIColor *)textColor withFontSize:(CGFloat)fontSize {
    
    UILabel *label = [[UILabel alloc]init];
    if (text) label.text = text;
    if (textColor) label.textColor = textColor;
    if (fontSize) label.font = [UIFont systemFontOfSize:fontSize];
    if (view) [view addSubview:label];
    
    return label;
}

+ (UIImageView *) setupImageViewWithSuperView:(UIView *)view withImageName:(NSString *)imageName {
    
    UIImageView *imageView = [[UIImageView alloc]init];
    if (imageName.length) imageView.image = [UIImage imageNamed:imageName];
    if (view) [view addSubview:imageView];
    
    return imageView;
}

+ (UIButton *) setupButtonWithSuperView:(UIView *)view withTitleToStateNormal:(NSString *)title withTitleColorToStateNormal:(UIColor *)titleColor withTitleFontSize:(CGFloat)titleFontSize withAction:(void(^)(UIButton *sender))action{

    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    if (title) [button setTitle:title forState:UIControlStateNormal];
    if (titleColor) [button setTitleColor:titleColor forState:UIControlStateNormal];
    if (titleFontSize) button.titleLabel.font = [UIFont systemFontOfSize:titleFontSize];
    if (action) {
        [[button rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^( id x) {
            action(button);
        }];
    }
    if (view) [view addSubview:button];

    return button;
}

+ (UIView *) setupLineViewWithSuperView:(UIView *)view{
    
    UIView *line = [[UIView alloc]init];
    line.backgroundColor = [UIColor dc1Color];
    if (view) [view addSubview:line];
    
    return line;
}
+ (UIView *) setupLineViewWithSuperView:(UIView *)view color:(UIColor *)color{
    
    UIView *line = [[UIView alloc]init];
    line.backgroundColor = color;
    if (view) [view addSubview:line];
    
    return line;
}
+ (UIView *) setupBottomLineViewWithSuperView:(UIView *)view withSpace:(CGFloat)space{
    if (view == nil) return nil;
    
    UIView *line = [[UIView alloc]init];
    line.backgroundColor = [UIColor dc1Color];
    [view addSubview:line];
    [line mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(view);
        make.left.equalTo(view).offset(space);
        make.right.equalTo(view).offset(-space);
        make.height.mas_equalTo(0.5);
    }];
    
    return line;
}

+ (UIView *) setupTopLineViewWithSuperView:(UIView *)view withSpace:(CGFloat)space{
    if (view == nil) return nil;
    
    UIView *line = [[UIView alloc]init];
    line.backgroundColor = [UIColor dc1Color];
    [view addSubview:line];
    [line mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(view);
        make.left.equalTo(view).offset(space);
        make.right.equalTo(view).offset(-space);
        make.height.mas_equalTo(0.5);
    }];
    
    return line;
}

+ (UIView *) setupViewWithSuperView:(UIView *)view withBackGroundColor:(UIColor *)color{
    
    UIView *childView = [[UIView alloc]init];
    if (view) [view addSubview:childView];
    if (color) childView.backgroundColor = color;
    
    return childView;
}

+ (UITableView *) setupTableViewWithSuperView:(UIView *)view withStyle:(UITableViewStyle)style withDelegateAndDataSource:(id)delegate{
    
    UITableView *tableView = [[UITableView alloc]initWithFrame:CGRectZero style:style];
    if (delegate) {
        tableView.delegate = delegate;
        tableView.dataSource = delegate;
    }
    [tableView setBackgroundColor: [UIColor clearColor]];
    [tableView setSeparatorColor: [UIColor clearColor]];
    [tableView setSeparatorStyle: UITableViewCellSeparatorStyleNone];
    [tableView setShowsHorizontalScrollIndicator: NO];
    [tableView setShowsVerticalScrollIndicator:   NO];
    if (view) [view addSubview:tableView];
    
    return tableView;
}

+ (UITextView *) setupTextViewWithSuperView:(UIView *)view withText:(NSString *)text withTextColor:(UIColor *)textColor withFontSize:(CGFloat)fontSize withDelegate:(id)delegate withReturnKeyType:(UIReturnKeyType)returnKeyType withKeyboardType:(UIKeyboardType)keyboardType{
    
    UITextView *textView = [[UITextView alloc]init];
    if (delegate) textView.delegate = delegate;
    textView.returnKeyType = returnKeyType;
    textView.keyboardType = keyboardType;
    if (text) textView.text = text;
    if (textColor) textView.textColor = textColor;
    if (fontSize) textView.font = [UIFont systemFontOfSize:fontSize];

    if (view) [view addSubview:textView];
    
    return textView;
}

+ (UITextField *) setupTextFieldWithSuperView:(UIView *)view withText:(NSString *)text withTextColor:(UIColor *)textColor withFontSize:(CGFloat)fontSize withPlaceholder:(NSString *)placeholder withDelegate:(id)delegate withReturnKeyType:(UIReturnKeyType)returnKeyType withKeyboardType:(UIKeyboardType)keyboardType{
    
    UITextField *textField = [[UITextField alloc]init];
    if (delegate) textField.delegate = delegate;
    textField.returnKeyType = returnKeyType;
    textField.keyboardType = keyboardType;
    if (text) textField.text = text;
    if (textColor) textField.textColor = textColor;
    if (fontSize) textField.font = [UIFont systemFontOfSize:fontSize];
    if (placeholder) textField.placeholder = placeholder;

    if (view) [view addSubview:textField];
    
    return textField;
}

+ (UIScrollView *) setupScrollViewWithSuperView:(UIView *)view withDelegate:(id)delegate withPagingEnabled:(BOOL)isPagingEnabled{
    
    UIScrollView *scrollView = [[UIScrollView alloc]init];
    [scrollView setShowsHorizontalScrollIndicator: NO];
    [scrollView setShowsVerticalScrollIndicator:   NO];
    [scrollView setPagingEnabled: isPagingEnabled];
    if (delegate) [scrollView setDelegate: delegate];

    if (view) [view addSubview:scrollView];
    
    return scrollView;
}

+ (UIView *) setupDashLineWithSuperView:(UIView *)view withLineColor:(UIColor *)color withSize:(CGSize)size withLineLength:(int)lineLength withLineSpacing:(int)lineSpacing{
    if (view == nil) return nil;
    
    UIView *dashLine = [[UIView alloc]initWithFrame:CGRectMake(0, 0, size.width, size.height)];
    [view addSubview:dashLine];
    if (lineLength <= 0) {
        lineLength = 1;
    }
    if (lineSpacing <= 0) {
        lineSpacing = 1;
    }
    [self drawDashLine:dashLine lineLength:lineLength lineSpacing:lineSpacing lineColor:color];
    return dashLine;
}

/**
 *  画虚线
 *
 *  @param lineView    需要绘制成虚线的view
 *  @param lineLength  虚线的宽度
 *  @param lineSpacing 虚线的间距
 *  @param lineColor   虚线的颜色
 */
+ (void)drawDashLine:(UIView *)lineView lineLength:(int)lineLength lineSpacing:(int)lineSpacing lineColor:(UIColor *)lineColor
{
    CAShapeLayer *shapeLayer = [CAShapeLayer layer];
    [shapeLayer setBounds:lineView.bounds];
    [shapeLayer setPosition:CGPointMake(CGRectGetWidth(lineView.frame) / 2, CGRectGetHeight(lineView.frame))];
    [shapeLayer setFillColor:[UIColor clearColor].CGColor];
    //  设置虚线颜色为blackColor
    [shapeLayer setStrokeColor:lineColor.CGColor];
    //  设置虚线宽度
    [shapeLayer setLineWidth:CGRectGetHeight(lineView.frame)];
    [shapeLayer setLineJoin:kCALineJoinRound];
    //  设置线宽，线间距
    [shapeLayer setLineDashPattern:[NSArray arrayWithObjects:[NSNumber numberWithInt:lineLength], [NSNumber numberWithInt:lineSpacing], nil]];
    //  设置路径
    CGMutablePathRef path = CGPathCreateMutable();
    CGPathMoveToPoint(path, NULL, 0, 0);
    CGPathAddLineToPoint(path, NULL, CGRectGetWidth(lineView.frame), 0);
    [shapeLayer setPath:path];
    CGPathRelease(path);
    //  把绘制好的虚线添加上来
    [lineView.layer addSublayer:shapeLayer];
}




@end
