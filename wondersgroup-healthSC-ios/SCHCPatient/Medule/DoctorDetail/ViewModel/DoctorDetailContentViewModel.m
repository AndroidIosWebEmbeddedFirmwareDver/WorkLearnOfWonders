//
//  DoctorDetailContentViewModel.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "DoctorDetailContentViewModel.h"


@interface DoctorDetailContentViewModel ()


@end

@implementation DoctorDetailContentViewModel


- (instancetype)initWithContent:(NSString *)content {
    
    if (self == [super init]) {
        self.content = content;
        [self prepareData];
    }
    return self;
}


- (void)prepareData {
    
//    self.content = @"      该院是国家“三级乙等”综合医院，担负着本地区60\
//    余万人的医疗救护工作任务，同时还担负着成都医学院、成都中医药大学等医学院校的临床教学工作。      该院是国家“三级乙等”综合医院，担负着本地区60\
//    余万人的医疗救护工作任务，同时还担负着成都医学院、成都中医药大学等医学院校的临床教学工作。      该院是国家“三级乙等”综合医院，担负着本地区60\
//    余万人的医疗救护工作任务，同时还担负着成都医学院、成都中医药大学等医学院校的临床教学工作。      该院是国家“三级乙等”综合医院，担负着本地区60\
//    余万人的医疗救护工作任务，同时还担负着成都医学院、成都中医药大学等医学院校的临床教学工作。";
    
//    UILabel *label = [UILabel new];
//    label.numberOfLines = 0;
//    label.font = [UIFont systemFontOfSize:14.];
//    label.text = self.content;
//    CGSize size = [label sizeThatFits:CGSizeMake(SCREEN_WIDTH-30., MAXFLOAT)];
//    
//    self.cellHeight = size.height+40.;
    self.cellHeight = [self getSpaceLabelHeight:self.content withFont:[UIFont systemFontOfSize:14.] withWidth:SCREEN_WIDTH-30.] + 40.;
}

- (CGFloat)getSpaceLabelHeight:(NSString*)str withFont:(UIFont*)font withWidth:(CGFloat)width {
    
    NSMutableParagraphStyle *paraStyle = [[NSMutableParagraphStyle alloc] init];
    paraStyle.lineBreakMode =NSLineBreakByCharWrapping;
    paraStyle.alignment =NSTextAlignmentLeft;
    paraStyle.lineSpacing = 5.;
    paraStyle.hyphenationFactor = 1.0;
    paraStyle.firstLineHeadIndent =0.0;
    paraStyle.paragraphSpacingBefore =0.0;
    paraStyle.headIndent = 0;
    paraStyle.tailIndent = 0;
    NSDictionary *dic =@{NSFontAttributeName:font,NSParagraphStyleAttributeName:paraStyle,NSKernAttributeName:@1.5f
                         };
    
    NSString *string = [str stringByReplacingOccurrencesOfString:@"\n" withString:@""];
    CGSize size = [string boundingRectWithSize:CGSizeMake(width, MAXFLOAT) options:NSStringDrawingUsesLineFragmentOrigin attributes:dic context:nil].size;
    return size.height;
}


@end
