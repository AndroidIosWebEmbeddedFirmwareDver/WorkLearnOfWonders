//
//  DoctorDetailViewModel.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/7.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "DoctorDetailViewModel.h"
#import "DoctorDetailJudgeModel.h"


@interface DoctorDetailViewModel ()

@property (nonatomic, strong) DoctorDetailLayoutModel *judgeCellModel;

@end


@implementation DoctorDetailViewModel

- (instancetype)init {
    
    if (self == [super init]) {
        
        [self prepareData];
    }
    return self;
}

- (void)prepareData {
    
    self.layoutArray = [NSMutableArray array];
    
    DoctorDetailLayoutModel *headerSectionModel = [[DoctorDetailLayoutModel alloc] init];
    headerSectionModel.type = DoctorDetailLayoutTypeHeaderSection;
    [self.layoutArray addObject:headerSectionModel];
    
    DoctorDetailLayoutModel *topSectionModel = [[DoctorDetailLayoutModel alloc] init];
    topSectionModel.type = DoctorDetailLayoutTypeTopSection;
    [self.layoutArray addObject:topSectionModel];
    
    DoctorDetailLayoutModel *judgeSectionModel = [[DoctorDetailLayoutModel alloc] init];
    judgeSectionModel.type = DoctorDetailLayoutTypeJudgeSection;
    [self.layoutArray addObject:judgeSectionModel];
    
    self.topCellHeight = 190.;
}

#pragma mark - 获取医生详情接口

- (void)getDoctorDetail:(void (^)(void))success failure:(void (^)(void))failure {
    
    /*
     | hospitalCode | 是    | 医院代码 | String |      |
     | hosDeptCode | 是    | 科室代码 | String |      |
     | hosDoctCode | 是    | 医生代码 | String |      |
     */
    
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    if ([UserManager manager].isLogin) {
        [params setObject:[UserManager manager].uid forKey:@"userId"];
    }
    [params setObject:self.hospitalCode forKey:@"hospitalCode"];
    [params setObject:self.hosDeptCode forKey:@"hosDeptCode"];
    [params setObject:self.hosDoctCode forKey:@"hosDoctCode"];
    
    [self.adapter request:DOCTOR_DETAIL_URL params:params class:nil responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        [DoctorDetailModel mj_setupObjectClassInArray:^NSDictionary *{
            return @{@"evaluList" : @"DoctorDetailJudgeModel"};
        }];
        
        [DoctorDetailModel mj_setupReplacedKeyFromPropertyName:^NSDictionary *{
            return @{@"mid" : @"id"};
        }];
        
        [DoctorDetailJudgeModel mj_setupReplacedKeyFromPropertyName:^NSDictionary *{
            return @{@"mid" : @"id"};
        }];
        
        self.resultModel = [DoctorDetailModel mj_objectWithKeyValues:response.data];
        
        self.headerModel = [[SCDoctorSchedulingModel alloc] init];
        self.headerModel.doctorInfo = [[DoctorInfo alloc] init];
        self.headerModel.doctorInfo.doctorName = self.resultModel.doctorName;
        self.headerModel.doctorInfo.doctorTitle = self.resultModel.doctorTitle;
        self.headerModel.doctorInfo.headphoto = self.resultModel.headphoto;
        if (self.resultModel.orderCount) {
            self.headerModel.doctorInfo.hosName =[NSString stringWithFormat:@"接诊量: %d",[self.resultModel.orderCount intValue]];
        }else{
            self.headerModel.doctorInfo.hosName = @"接诊量: 0";
        
        }
        
        self.headerModel.doctorInfo.gender = self.resultModel.gender;
        
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        self.topCellHeight = 195.;
        CGFloat width = SCREEN_WIDTH - 30.;
        UIFont *font = [UIFont systemFontOfSize:14.];
        
        self.topCellHeight += [self getCellHeightWithContent:self.resultModel.hosName contentWidth:width font:font numberOfLines:2];
        
        self.topCellHeight += [self getCellHeightWithContent:self.resultModel.expertin contentWidth:width font:font numberOfLines:0];
        self.topCellHeight += [self getCellHeightWithContent:self.resultModel.doctorDesc contentWidth:width font:font numberOfLines:3];
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//        self.resultModel.evaluList = [NSMutableArray array];
//        for (int i = 0; i < 10; i++) {
//            
//            DoctorDetailJudgeModel *model = [[DoctorDetailJudgeModel alloc] init];
//            model.content = @"评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价评价";
//            model.createTime = @"2016-11-11";
//            model.nickName = @"老王";
//            [self.resultModel.evaluList addObject:model];
//        }
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        
        UILabel *tempLabel = [UILabel new];
        tempLabel.font = [UIFont systemFontOfSize:14.];
        tempLabel.numberOfLines = 0;
        for (DoctorDetailJudgeModel *model in self.resultModel.evaluList) {
            tempLabel.text = model.content;
            CGSize size = [tempLabel sizeThatFits:CGSizeMake(SCREEN_WIDTH-30., MAXFLOAT)];
            model.cellHeight = size.height + 50.;
        }
        
        success();
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
        failure();
    }];
}


#pragma mark - 关注/取消关注 接口

- (void)postFavoriteDoctor:(void (^)(NSString *))success failure:(void (^)(NSString *))failure {
    
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    [params setObject:[UserManager manager].uid forKey:@"uid"];
    if (self.resultModel.mid) {
        [params setObject:self.resultModel.mid forKey:@"doctorId"];
    }
    [self.adapter request:DOCTOR_FAVORITE_URL params:params class:nil responseType:Response_Object method:Request_POST needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
                
        success(response.message);
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
        failure(error.localizedDescription);
    }];
}


#pragma mark - 计算cell高度

- (CGFloat)getCellHeightWithContent:(NSString *)content contentWidth:(CGFloat)contentWidth font:(UIFont *)font numberOfLines:(NSInteger)numberOfLines {
    
    UILabel *label = [UILabel new];
    label.font = font;
    label.numberOfLines = numberOfLines;
    label.text = content;
    CGSize size = [label sizeThatFits:CGSizeMake(contentWidth, MAXFLOAT)];
    return size.height;
}


@end


@implementation DoctorDetailLayoutModel

@end
