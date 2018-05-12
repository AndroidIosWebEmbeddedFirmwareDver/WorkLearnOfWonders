//
//  YWSpritesLoadingView.m
//  YYW
//
//  Created by xingyong on 12/11/14.
//  Copyright (c) 2014 YYW. All rights reserved.
//

#import "SpritesLoadingView.h"

@implementation SpritesLoadingView


- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.backgroundColor = [UIColor clearColor];
        
        _loadingImageView=[[UIImageView alloc] initWithFrame:self.bounds];
 
        _loadingImageView.layer.zPosition = MAXFLOAT;
        int dottCount = 12;
        NSMutableArray *imageArray = [NSMutableArray arrayWithCapacity: dottCount];
        for (int i = 1; i <= dottCount; i++) {
          //  [imageArray addObject:[UIImage imageNamed:[NSString stringWithFormat:@"load%d",i]]];
           [imageArray addObject:[UIImage imageNamed:[NSString stringWithFormat:@"dot%d",i]]];
        }
       
      
        _loadingImageView.animationImages = imageArray;
        _loadingImageView.animationDuration = 0.86;
        [_loadingImageView startAnimating];

        [self addSubview:_loadingImageView];
        
         
    }
    return self;
}

- (void)dealloc{
    [_loadingImageView stopAnimating];
    [_loadingImageView removeFromSuperview];
    _loadingImageView = nil;

}

 
@end
