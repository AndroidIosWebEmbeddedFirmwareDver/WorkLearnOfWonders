//
//  PatientDetailViewController.m
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/8.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatientDetailViewController.h"
#import "PatientDetailView.h"

@interface PatientDetailViewController ()

@property (nonatomic, strong) PatientDetailView *patientDetailView;

@end

@implementation PatientDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"患者详情";
    
    [self prepareUI];
    [self bindRac];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)prepareUI {
    _patientDetailView = [[PatientDetailView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 90)];
    [self.view addSubview:_patientDetailView];
    
    _patientDetailView.model = self.model;
    WS(weakSelf)
    _patientDetailView.callActionBlock = ^(NSString *mobile) {
        [weakSelf callMobile:mobile];
    };

}

- (void)bindRac {
    
}

- (void)callMobile:(NSString *)mobile {
    
    if (mobile == nil || [[mobile stringByDeletingTerminalSpace] isEqualToString:@""]) {
        return;
    }
    
    NSURL *telURL = [NSURL URLWithString:[NSString stringWithFormat:@"tel://%@", mobile]];
    UIAlertController *alertC = [UIAlertController alertControllerWithTitle:[NSString stringWithFormat:@"拨打%@", mobile]
                                                                    message:nil
                                                             preferredStyle:UIAlertControllerStyleAlert];
    [alertC addAction:[UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
    }]];
    
    [alertC addAction:[UIAlertAction actionWithTitle:@"拨打" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        if (![[UIApplication sharedApplication] canOpenURL:telURL]) {
            
            return;
        }
        
        [[UIApplication sharedApplication] openURL:telURL];
    }]];
    
    
    [self.navigationController presentViewController:alertC animated:YES completion:^{
        
    }];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
