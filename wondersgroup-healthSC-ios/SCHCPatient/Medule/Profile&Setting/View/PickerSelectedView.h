//
//  PickerSelectedView.h
//  VaccinePatient
//
//  Created by Jam on 16/6/15.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

//typedef NS_ENUM(NSUInteger, DatePickerType) {
//    DatePickerTypeGender = 0,     //性别
//    DatePickerTypeBirthday,       //生日
//};

typedef void(^SelectedBlock)(id selObject);   //Pick选中的回调


@interface PickerSelectedView : UIView

@property (nonatomic, copy) SelectedBlock saveBlock;


//@property (nonatomic, assign) DatePickerType pickerType;

//性别选择
- (void)showGenderPickerWithModel: (NSString *)gender
                        withTitle: (NSString *)showTitle;

//日期选择
- (void)showDatePickerWithModel: (UIDatePickerMode)model
                       withDate: (NSString *)date
                      withTitle: (NSString *)showTitle
                    withMaxDate: (NSDate *)maxDate
                    withMinDate: (NSDate *)minDate;



- (void)dissmiss;

@end
