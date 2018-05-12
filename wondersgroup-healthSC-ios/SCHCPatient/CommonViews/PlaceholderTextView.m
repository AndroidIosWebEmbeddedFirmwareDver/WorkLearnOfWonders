//
//  PlaceholderTextView.m
//  HCPatient
//
//  Created by ZJW on 15/7/14.
//  Copyright (c) 2015年 陈刚. All rights reserved.
//

#import "PlaceholderTextView.h"

@implementation PlaceholderTextView
@synthesize placeHolderLabel;
@synthesize placeholder;
@synthesize placeholderColor;
//@synthesize limitLabel;
//@synthesize limitColor;

- (void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
#if __has_feature(objc_arc)
#else
    [placeHolderLabel release]; placeHolderLabel = nil;
    [placeholderColor release]; placeholderColor = nil;
    [placeholder release]; placeholder = nil;
    [super dealloc];
#endif
    
}

- (void)awakeFromNib
{
    [super awakeFromNib];
//    self.limitLength = 0;
    [self setPlaceholder:@""];
    [self setPlaceholderColor:[UIColor lightGrayColor]];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(textChanged:) name:UITextViewTextDidChangeNotification object:nil];
}

- (id)initWithFrame:(CGRect)frame
{
    if( (self = [super initWithFrame:frame]) )
    {
        self.delegate = self;
        self.limitLength = 0;
//        [self setLimitColor:[UIColor lightGrayColor]];

        [self setPlaceholder:@""];
        [self setPlaceholderColor:[UIColor lightGrayColor]];
        self.placeHolderLabelOrigion = CGPointMake(5, 8);
        
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(textChanged:) name:UITextViewTextDidChangeNotification object:nil];
    }
    return self;
}

- (void)textChanged:(NSNotification *)notification
{
    if([[self placeholder] length] == 0)
    {
        return;
    }
    
    if([[self text] length] == 0)
    {
        [(UIView *)[self viewWithTag:999] setAlpha:1];
    }
    else
    {
        [(UIView *)[self viewWithTag:999] setAlpha:0];
    }
    
    if ([[self text] length] > 50) {
        NSString *limitTxt = [[self text] substringWithRange:NSMakeRange(0, 50)];
        [self setText: limitTxt];
    }
    self.contentText = [self text];
//    if (self.limitLength > 0 && limitLabel) {
//        [limitLabel setText: [NSString stringWithFormat: @"%ld/%ld", (unsigned long)[[self text] length], (long)self.limitLength]];
//    }
}

- (void)setText:(NSString *)text {
    [super setText:text];
    [self textChanged:nil];
}

- (void)drawRect:(CGRect)rect
{
    if( [[self placeholder] length] > 0 )
    {
        if ( placeHolderLabel == nil )
        {
            placeHolderLabel = [[UILabel alloc] initWithFrame:CGRectMake(self.placeHolderLabelOrigion.x,self.placeHolderLabelOrigion.y,self.bounds.size.width - self.placeHolderLabelOrigion.x*2,0)];
            placeHolderLabel.lineBreakMode = NSLineBreakByWordWrapping;
            placeHolderLabel.numberOfLines = 0;
            placeHolderLabel.font = self.font;
            placeHolderLabel.backgroundColor = [UIColor clearColor];
            placeHolderLabel.textColor = self.placeholderColor;
            placeHolderLabel.alpha = 0;
            placeHolderLabel.tag = 999;
            [self addSubview:placeHolderLabel];
        }
        
        placeHolderLabel.text = self.placeholder;
        [placeHolderLabel sizeToFit];
        [self sendSubviewToBack:placeHolderLabel];
    }
    
    if( [[self text] length] == 0 && [[self placeholder] length] > 0 )
    {
        [(UIView *)[self viewWithTag:999] setAlpha:1];
    }
    
//    if (self.limitLength > 0) {
//        if ( !limitLabel )
//        {
//            limitLabel = [[UILabel alloc] init];
//            limitLabel.font = self.font;
//            limitLabel.backgroundColor = [UIColor clearColor];
//            limitLabel.textAlignment = NSTextAlignmentRight;
//            limitLabel.textColor = self.limitColor;
//            [self addSubview: limitLabel];
//            
//            WS(weakSelf);
//            [limitLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//                make.right.mas_equalTo(weakSelf.bounds.size.width);
//                make.bottom.mas_equalTo(weakSelf.bounds.size.height);
//            }];
//        }
//        
//        WS(weakSelf);
//        [limitLabel mas_updateConstraints:^(MASConstraintMaker *make) {
//            make.right.mas_equalTo(weakSelf.bounds.size.width);
//            make.bottom.mas_equalTo(weakSelf.bounds.size.height);
//        }];
//        
//        [limitLabel setText: [NSString stringWithFormat: @"%ld/%ld", (unsigned long)[[self text] length], (long)self.limitLength]];
//        [limitLabel sizeToFit];
//        [self bringSubviewToFront: limitLabel];
//    }
    
    [super drawRect:rect];
}

#pragma mark - UITextViewDelegate

- (BOOL)textView:(UITextView *)textView shouldChangeTextInRange:(NSRange)range replacementText:(NSString *)text {
    if ([text length]) {
        if (self.limitLength > 0) {
            if ([textView.text length] > 50)
                return NO;
            if ([textView.text length] + [text length] > 50) {
                
                NSInteger leftLength =  50 - [textView.text length];
                if (leftLength > 0) {
                    NSString *limitTxt = [text substringWithRange:NSMakeRange(0, leftLength-1)];
                    textView.text = [NSString stringWithFormat: @"%@%@", textView.text, limitTxt];
                }
                return NO;
            }
        }
    }
    return YES;
    
}



@end
