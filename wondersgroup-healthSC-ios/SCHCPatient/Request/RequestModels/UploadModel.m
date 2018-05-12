//
//  UploadModel.m
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "UploadModel.h"
#import "UIImage+Scale.h"

@implementation UploadModel

- (instancetype)init:(FileType)type fileData:(NSData *)data fileName:(NSString *)name {
    self = [super init];
    if (self) {
        self.fileName = name;
        self.fileType = type;
        self.fileData = data;
    }
    return self;
}


- (void)setFileData:(NSData *)fileData {
    if (fileData && self.fileType == FileType_Image) {
        UIImage *image = [UIImage imageWithData: fileData];
        _fileData = [image compressImage];
    }
}


@end
