//
//  CountDownManager.m
//  VaccinePatient
//
//  Created by Jam on 16/6/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "CountDownManager.h"

static int limitSend = 60;


@interface CountDownManager() {
    
}

@end


@implementation CountDownManager

+ (instancetype)manager {
    static CountDownManager *instance;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [[CountDownManager alloc] init];
    });
    return instance;
}


#pragma mark - 初始化
- (instancetype)init {
    self = [super init];
    if (self) {
        [self resetAllCountDownTimer];
    }
    return self;
}

- (void)resetAllCountDownTimer {
    
    self.countDownLogin     = -1;
    self.countDownRegister  = -1;
    self.countDownPWD       = -1;
    self.countDownForget    = -1;
    self.countDownMobile    = -1;
    self.countDownApment    = -1;
    self.countDownMobileValidate = -1;
    self.countDownBind = -1;
    
    _loginSMSDate       = nil;
    _registerSMSDate    = nil;
    _passwordDate       = nil;
    _forgetSMSDate      = nil;
    _mobileSMSDate      = nil;
    _apmentSMSDate      = nil;
    _validateMobileSMSDate = nil;
     _bindSMSDate = nil;
}


#pragma mark   倒计时
- (void)countdownTime:(ConutDownType)type {

    switch (type) {
        case CD_COUNTDOWN_LOGIN://登录验证码倒计时
        {
            NSTimer *timer = [NSTimer scheduledTimerWithTimeInterval:0.f target:self selector:@selector(updateLoginCD:) userInfo:nil repeats:YES];
            _loginSMSDate = [NSDate date];
            [[NSRunLoop currentRunLoop] addTimer: timer forMode: NSRunLoopCommonModes];
        }
            break;
        case CD_COUNTDOWN_REGISTER://登录验证码倒计时
        {
            NSTimer *timer = [NSTimer scheduledTimerWithTimeInterval:0.f target:self selector:@selector(updateRegisterCD:) userInfo:nil repeats:YES];
            _registerSMSDate = [NSDate date];
            [[NSRunLoop currentRunLoop] addTimer: timer forMode: NSRunLoopCommonModes];
        }
            break;

        case CD_COUNTDOWN_PWD://重置密码验证码倒计时
        {
            NSTimer *timer = [NSTimer scheduledTimerWithTimeInterval:0.f target:self selector:@selector(updatePwdCD:) userInfo:nil repeats:YES];
            _passwordDate = [NSDate date];
            [[NSRunLoop currentRunLoop] addTimer: timer forMode: NSRunLoopCommonModes];
        }
            break;

        case CD_COUNTDOWN_FORGET://忘记密码验证码倒计时
        {
            NSTimer *timer = [NSTimer scheduledTimerWithTimeInterval:0.f target:self selector:@selector(updateForgetCD:) userInfo:nil repeats:YES];
            _forgetSMSDate= [NSDate date];
            [[NSRunLoop currentRunLoop] addTimer: timer forMode: NSRunLoopCommonModes];
        }
            break;
        case CD_COUNTDOWN_MOBILE_VALIDATE://修改手机号时验证手机验证码倒计时
        {
            NSTimer *timer = [NSTimer scheduledTimerWithTimeInterval:0.f target:self selector:@selector(updateValidateMobileCD:) userInfo:nil repeats:YES];
            _validateMobileSMSDate = [NSDate date];
            [[NSRunLoop currentRunLoop] addTimer: timer forMode: NSRunLoopCommonModes];
        }
            break;
        case CD_COUNTDOWN_MOBILE://修改手机号验证码倒计时
        {
            NSTimer *timer = [NSTimer scheduledTimerWithTimeInterval:0.f target:self selector:@selector(updateMobileCD:) userInfo:nil repeats:YES];
            _mobileSMSDate = [NSDate date];
            [[NSRunLoop currentRunLoop] addTimer: timer forMode: NSRunLoopCommonModes];
        }
            break;
        case CD_COUNTDOWN_APMENT://预约验证码倒计时
        {
            NSTimer *timer = [NSTimer scheduledTimerWithTimeInterval:0.f target:self selector:@selector(updateAppointmentCD:) userInfo:nil repeats:YES];
            _apmentSMSDate = [NSDate date];
            [[NSRunLoop currentRunLoop] addTimer: timer forMode: NSRunLoopCommonModes];
        }
            break;
        case CD_COUNTDOWN_BIND://绑定验证码倒计时
        {
            NSTimer *timer = [NSTimer scheduledTimerWithTimeInterval:0.f target:self selector:@selector(updateBindCD:) userInfo:nil repeats:YES];
            _bindSMSDate = [NSDate date];
            [[NSRunLoop currentRunLoop] addTimer: timer forMode: NSRunLoopCommonModes];
        }
            break;

        default:
            break;
    }
}

#pragma mark - 绑定手机号验证码倒计时
- (void)updateBindCD:(NSTimer *)timer {
    if (_bindSMSDate) {
        int sec = [[NSDate date] timeIntervalSinceDate: _bindSMSDate];
        if (sec >= limitSend) {
            [timer invalidate];
            timer = nil;
            
            self.countDownBind = -1;
            _bindSMSDate       = nil;
        }
        else {
            self.countDownBind = limitSend - sec;
        }
    }
    else {
        [timer invalidate];
        timer = nil;
        
        self.countDownBind = -1;
        _bindSMSDate       = nil;
    }
}

#pragma mark - 登录验证码倒计时
- (void)updateLoginCD:(NSTimer *)timer {
    if (_loginSMSDate) {
        int sec = [[NSDate date] timeIntervalSinceDate: _loginSMSDate];
        if (sec >= limitSend) {
            [timer invalidate];
            timer = nil;
            
            self.countDownLogin = -1;
            _loginSMSDate       = nil;
        }
        else {
            self.countDownLogin = limitSend - sec;
        }
    }
    else {
        [timer invalidate];
        timer = nil;
        
        self.countDownLogin = -1;
        _loginSMSDate       = nil;
    }
}

- (void)updateRegisterCD:(NSTimer *)timer {
    
    if (_registerSMSDate) {
        int sec = [[NSDate date] timeIntervalSinceDate: _registerSMSDate];
        if (sec >= limitSend) {
            [timer invalidate];
            timer = nil;
            
            self.countDownRegister = -1;
            _registerSMSDate       = nil;
        }
        else {
            self.countDownRegister = limitSend - sec;
        }
    }
    else {
        [timer invalidate];
        timer = nil;
        
        self.countDownRegister = -1;
        _registerSMSDate       = nil;
    }
}

#pragma mark - 重置密码验证码倒计时
- (void)updatePwdCD:(NSTimer *)timer {
    if (_passwordDate) {
        int sec = [[NSDate date] timeIntervalSinceDate: _passwordDate];
        if (sec >= limitSend) {
            [timer invalidate];
            timer = nil;
            
            self.countDownPWD = -1;
            _passwordDate       = nil;
        }
        else {
            self.countDownPWD = limitSend - sec;
        }
    }
    else {
        [timer invalidate];
        timer = nil;
        
        self.countDownPWD = -1;
        _passwordDate       = nil;
    }
}

#pragma mark - 忘记密码验证码倒计时
- (void)updateForgetCD:(NSTimer *)timer {
    if (_forgetSMSDate) {
        int sec = [[NSDate date] timeIntervalSinceDate: _forgetSMSDate];
        if (sec >= limitSend) {
            [timer invalidate];
            timer = nil;
            
            self.countDownForget = -1;
            _forgetSMSDate       = nil;
        }
        else {
            self.countDownForget = limitSend - sec;
        }
    }
    else {
        [timer invalidate];
        timer = nil;
        
        self.countDownForget = -1;
        _forgetSMSDate       = nil;
    }
}


#pragma mark - 修改手机号时验证手机验证码倒计时
- (void)updateValidateMobileCD:(NSTimer *)timer {
    if (_validateMobileSMSDate) {
        int sec = [[NSDate date] timeIntervalSinceDate: _validateMobileSMSDate];
        if (sec >= limitSend) {
            [timer invalidate];
            timer = nil;
            
            self.countDownMobileValidate = -1;
            _validateMobileSMSDate       = nil;
        }
        else {
            self.countDownMobileValidate = limitSend - sec;
        }
    }
    else {
        [timer invalidate];
        timer = nil;
        
        self.countDownMobileValidate = -1;
        _validateMobileSMSDate       = nil;
    }
}

#pragma mark - 修改手机号验证码倒计时
- (void)updateMobileCD:(NSTimer *)timer {
    
    if (_mobileSMSDate) {
        int sec = [[NSDate date] timeIntervalSinceDate: _mobileSMSDate];
        if (sec >= limitSend) {
            [timer invalidate];
            timer = nil;
            
            self.countDownMobile = -1;
            _mobileSMSDate       = nil;
        }
        else {
            self.countDownMobile = limitSend - sec;
        }
    }
    else {
        [timer invalidate];
        timer = nil;
        
        self.countDownMobile = -1;
        _mobileSMSDate       = nil;
    }

}

#pragma mark - 预约验证码倒计时
- (void)updateAppointmentCD:(NSTimer *)timer {
    if (_mobileSMSDate) {
        int sec = [[NSDate date] timeIntervalSinceDate: _apmentSMSDate];
        if (sec >= limitSend) {
            [timer invalidate];
            timer = nil;
            
            self.countDownApment = -1;
            _apmentSMSDate       = nil;
        }
        else {
            self.countDownApment = limitSend - sec;
        }
    }
    else {
        [timer invalidate];
        timer = nil;
        
        self.countDownApment = -1;
        _apmentSMSDate       = nil;
    }
}


@end
