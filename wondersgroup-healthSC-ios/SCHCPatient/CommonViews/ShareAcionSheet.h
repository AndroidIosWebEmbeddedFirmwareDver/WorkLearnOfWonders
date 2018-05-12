//
//  ShareAcionSheet.h
//  VaccinePatient
//
//  Created by ZJW on 16/7/27.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef enum{
    ShareTypeWeiXinFriendCircle,
    ShareTypeWeiXinFriend,
    ShareTypeQQ,
    ShareTypeSina,
    ShareTypeOther,
}ShareType;

//typedef enum{
//    CustomTypeCollectionTopic,//收藏话题
//    CustomTypeDeleteTopic,//删除话题
//}CustomType;


typedef void (^ShareBlock)(ShareType type);
typedef void (^CustomBlock)(NSInteger type);//1000+i
typedef void(^CancelBlock)(void);

@interface ShareAcionSheet : UIView

-(instancetype)initWithCustomIcons:(NSArray *)icons withCustomTitles:(NSArray *)titles;

@property (nonatomic, strong) ShareBlock shareBlock;
@property (nonatomic, strong) CustomBlock customBlock;
@property (nonatomic, copy) CancelBlock cancelBlock;

-(void)show;



@end
