//
//  PatientInfoViewModel.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/7.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseViewModel.h"
#import "PatientInfoModel.h"
typedef NS_ENUM(NSUInteger, PatientInfoCellType) {
    PatientInfoCellNormal = 0,      //普通
    PatientInfoCellSelected,        //选择
    PatientInfoCellTextField,       //textFiled
    PatientInfoCellTextView,        //textView
    PatientInfoCellTextPanel        //输入面板
};

@interface PatientInfoViewModel : BaseViewModel

@property (assign, nonatomic) ReferralType type;                //住院， 门诊
@property (strong, nonatomic) NSArray * modelsArray;            //数据标题
@property (strong, nonatomic) NSArray * urgencyArray;           //选择类型数组(一般，紧急)


@property (copy, nonatomic) PatientInfoModel * data;            //转诊数据


- (instancetype)initWithType:(ReferralType)type model:(PatientInfoModel *)model;

/**获取编辑数据*/
- (PatientInfoModel *)getPostData;


/**
 检测重要数据是否完整

 @return 错误信息
 */
- (NSString *)checkModelImportantFullWithErrorString;
@end

@interface PatientInfoCellTypeModel : NSObject
@property (strong, nonatomic) NSString * title;             //标题
@property (strong, nonatomic) NSString * placeHolder;       //空闲文案
@property (strong, nonatomic) NSString * detail;            //值
@property (assign, nonatomic) PatientInfoCellType type;     //类型

@property (assign, nonatomic) BOOL isImportant;             //是否重要
@property (assign, nonatomic) BOOL topBlank;                //上方是否留白
@property (strong, nonatomic) NSString * keyString;         //key值
@end
