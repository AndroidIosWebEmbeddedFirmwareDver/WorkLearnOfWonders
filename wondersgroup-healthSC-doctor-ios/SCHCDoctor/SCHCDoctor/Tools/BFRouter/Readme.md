---BFRouter

WDRouter

#1、open   
##1 Example:
             com.healthcloud.patient://doctordetail?doctorid=111
             ／／后台配置链接
             http://www.baidu.com?keyword=abc
             ／／H5页面
             ./doctordetail?doctorid=111
               ／／当前页面跳转
			  doctordetail?doctorid=111
			  ／／当前页面跳转
			  ../doctordetail?doctorid=111  or ../
			  ／／上个页面跳转，隔过当前页面.支持多级返回../../../doctordetail
			  /doctordetail?doctorid=111
			  ／／直接从home页跳转
                
              -doctordetail 跳转至navigation栈中的指定页面，如果不存在则返回到root
              

##2 说明：
            跳转页面如许接收url中的参数请重写基类 WDBaseViewController中的方法 - (instancetype)initWithUrlParameter:(NSDictionary *)parameter
            对于webview 加载网页 需要在plist 文件中配置webview的映射,对应vc实现BFRouterPassUrlProtocal协议的- (instancetype)initWithURL:(NSString *)url方法