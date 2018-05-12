//
//  UploadModel.h
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef enum {
    
    FileType_Image = 0, //图片类型
    FileType_Text,      //文本类型
    FileType_Dict,      //字典类型
    FileType_JSON       //JSON类型
    
} FileType;



@interface UploadModel : NSObject


/** 初始化
 *  @param  type  上传文件类型
 *  @param  data  上传文件data
 *  @param  name  上传文件名称
 */
- (instancetype)init:(FileType)type fileData:(NSData *)data fileName:(NSString *)name;

@property (nonatomic, copy)     NSString    *fileName;
@property (nonatomic, copy)     NSData      *fileData;
@property (nonatomic, assign)   FileType     fileType;


@end
