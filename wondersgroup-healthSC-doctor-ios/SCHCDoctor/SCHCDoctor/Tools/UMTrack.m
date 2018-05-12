//
//  UMTrack.m
//  EyeProtectionDoctor
//
//  Created by Jam on 16/5/14.
//  Copyright © 2016年 陈刚. All rights reserved.
//

#import "UMTrack.h"

#import <AdSupport/ASIdentifierManager.h>
#include <sys/sysctl.h>
#include <sys/socket.h>
#include <net/if.h>
#include <net/if_dl.h>
#import "OpenUDID.h"

#import "UMMobClick/MobClick.h"


@implementation UMTrack

+ (void)startUMTrackWithAppkey:(NSString *)appkey {
    if (!appkey || ![appkey length])
    {
        return;
    }
    
    NSString *version = [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleShortVersionString"];
    [MobClick setAppVersion:version];
    
    UMConfigInstance.appKey = appkey;
//    UMConfigInstance.channelId = @"App Store";
//    [MobClick startWithAppkey: appkey];
    [MobClick startWithConfigure: UMConfigInstance];
    
    //友盟渠道追踪
    [UMTrack requestTrackWithAppkey: appkey];
}


/*
 * 友盟集成
 */
+(void)requestTrackWithAppkey:(NSString *)appkey
{
    if (!appkey || ![appkey length])
    {
        return;
    }
    
    ASIdentifierManager *asIM = [[ASIdentifierManager alloc] init];
    NSString *idfa = [asIM.advertisingIdentifier UUIDString];
    NSString *idfv = [[UIDevice currentDevice].identifierForVendor UUIDString];
    NSString *openudid = [OpenUDID value];
    NSString *mac = [self macString];
    
    size_t size;
    
    // Set 'oldp' parameter to NULL to get the size of the data
    // returned so we can allocate appropriate amount of space
    sysctlbyname("hw.machine", NULL, &size, NULL, 0);
    // Allocate the space to store name
    char *name = malloc(size);
    // Get the platform name
    sysctlbyname("hw.machine", name, &size, NULL, 0);
    // Place name into a string
    NSString *machine = [NSString stringWithCString:name encoding:NSUTF8StringEncoding];
    // Done with this
    free(name);
    
    NSString *requestURL = [[NSString alloc] initWithFormat:@"http://ar.umeng.com/stat.htm?ak=%@&device_name=%@&idfa=%@&openudid=%@&idfv=%@&mac=%@",appkey,machine, idfa, openudid, idfv, mac];
    
//    NSError *error = nil;
//    NSHTTPURLResponse *response = nil;
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:requestURL]];
//    NSData *responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    [[NSURLSession sharedSession] dataTaskWithRequest: request completionHandler:^(NSData * _Nullable data, NSURLResponse * _Nullable response, NSError * _Nullable error) {
        if (response)
        {
            NSLog(@"ok");
        }
    }];
    
}


+ (NSString * )macString{
    int mib[6];
    size_t len;
    char *buf;
    unsigned char *ptr;
    struct if_msghdr *ifm;
    struct sockaddr_dl *sdl;
    
    mib[0] = CTL_NET;
    mib[1] = AF_ROUTE;
    mib[2] = 0;
    mib[3] = AF_LINK;
    mib[4] = NET_RT_IFLIST;
    
    if ((mib[5] = if_nametoindex("en0")) == 0) {
        printf("Error: if_nametoindex error\n");
        return NULL;
    }
    
    if (sysctl(mib, 6, NULL, &len, NULL, 0) < 0) {
        printf("Error: sysctl, take 1\n");
        return NULL;
    }
    
    if ((buf = malloc(len)) == NULL) {
        printf("Could not allocate memory. error!\n");
        return NULL;
    }
    
    if (sysctl(mib, 6, buf, &len, NULL, 0) < 0) {
        printf("Error: sysctl, take 2");
        free(buf);
        return NULL;
    }
    
    ifm = (struct if_msghdr *)buf;
    sdl = (struct sockaddr_dl *)(ifm + 1);
    ptr = (unsigned char *)LLADDR(sdl);
    NSString *macString = [NSString stringWithFormat:@"%02X:%02X:%02X:%02X:%02X:%02X",
                           *ptr, *(ptr+1), *(ptr+2), *(ptr+3), *(ptr+4), *(ptr+5)];
    free(buf);
    
    return macString;
}


@end
