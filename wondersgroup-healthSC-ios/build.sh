#!/bin/sh
if [ ! $1 ]; then
echo "请输入需要编译的版本 te(测试) 或者 re(发布)"
exit
fi

security unlock-keychain "-p" "123123" "/Users/chengang/Library/Keychains/login.keychain"
#需要配置的
#----------------------------------------------------------------------------------------
#配置项目名
IPA_NAME="SCHCPatient"
PRJ_NAME="SCHCPatient"
SCHEME_RE="SCHCPatient"
SCHEME_TE="SCHCPatient_TEST"
#配置证书
#四川
#SIGN_IDENTITY_RE="iPhone Distribution: Si Chuan Wonders Health Data Co., Ltd"
#SIGN_IDENTITY="iPhone Developer: wenliang Wang"
#配置编译Profile
#PROFILE_NAME_RE="VenusOpenUser(Ad-Hoc)"
#PROFILE_NAME="VenusOpenUser(Development)"

#万达企业证书
SIGN_IDENTITY_RE="iPhone Distribution: Wonders Information Co., LTD."
SIGN_IDENTITY="iPhone Distribution: Wonders Information Co., LTD."
#配置编译Profile
PROFILE_NAME_RE="healthvenusopenuser Distribution"
PROFILE_NAME="healthvenusopenuser Distribution"

#配置编译机User名称
USER_NAME="chengang"
#USER_NAME="James"
#----------------------------------------------------------------------------------------


#项目在编译机目录设置，无需配置
SH_PATH=$(cd "$(dirname "$0")"; pwd)
PRJ_PATH="${SH_PATH}"
PRJ_CONFIG_PLIST="config.plist"
PRJ_INFO_PLIST="Info.plist"
cd $PRJ_PATH

#archive包目录
XCARCHIVE_PATH="/Users/${USER_NAME}/Desktop/archive/${PRJ_NAME}.xcarchive"
EXPORT_PLIST_PATH="/Users/${USER_NAME}/Desktop/ExportOptions.plist"

#ipa包目录
#IPA_PATH="/Users/${USER_NAME}/Desktop/"$1"/${PRJ_NAME}.ipa"
IPA_PATH="/Users/${USER_NAME}/Desktop/"$1"/"


#修改plist
#修改打包的服务器
#if [  $1 = "te" ] || [  $1 = "re" ]; then
#/usr/libexec/PlistBuddy -c "set :servertype $1" ${PRJ_PATH}/${PRJ_NAME}/${PRJ_CONFIG_PLIST}
#else
#echo "请输入需要编译的版本 te(测试) 或者 re(发布)"
#exit
#fi

#修改时间
#mydate=`date '+%Y-%m-%d %H:%M:%S'`
##echo $mydate
#/usr/libexec/PlistBuddy -c "set :nowtime ${mydate}" ${PRJ_PATH}/${PRJ_NAME}/${PRJ_CONFIG_PLIST}
#
##build+1
#bundleBuildVersion=`/usr/libexec/PlistBuddy -c "Print CFBundleVersion" ${PRJ_PATH}/${PRJ_NAME}/${PRJ_INFO_PLIST}`
#bundleBuildVersion=$[$bundleBuildVersion+1]
#/usr/libexec/PlistBuddy -c "set :CFBundleVersion ${bundleBuildVersion}" ${PRJ_PATH}/${PRJ_NAME}/${PRJ_INFO_PLIST}

#clean项目
#/usr/bin/xcodebuild -configuration Release -target ${PRJ_NAME} clean
/usr/bin/xcodebuild -configuration Release -alltargets clean


##打包
#/usr/bin/xcodebuild -configuration Release  -workspace ${PRJ_NAME}.xcworkspace -scheme ${PRJ_NAME} CODE_SIGN_IDENTITY="${SIGN_IDENTITY}"
#
##生成ipa
#xcrun -sdk iphoneos PackageApplication -v ${PRJ_PATH}"/DerivedData/${PRJ_NAME}/Build/Products/Release-iphoneos/${PRJ_NAME}.app" -o ${IPA_PATH}
#echo "${IPA_PATH}"


#如果ipa目录ipa包存在，则先删除该包
#if [ -f "${IPA_PATH}" ];then
#rm ${IPA_PATH}
#echo "删除成功"
#fi


#----------------------------------------------------------------------------------------
#Archive
#Archive项目后在archive目录生成.xcarchive包
if [  $1 = "te" ]; then
/usr/bin/xcodebuild -configuration Release archive -workspace ${PRJ_NAME}.xcworkspace -scheme ${SCHEME_TE} CODE_SIGN_IDENTITY="${SIGN_IDENTITY}" -destination generic/platform=iOS -archivePath ${XCARCHIVE_PATH}
else
/usr/bin/xcodebuild -configuration Release archive -workspace ${PRJ_NAME}.xcworkspace -scheme ${SCHEME_RE} CODE_SIGN_IDENTITY="${SIGN_IDENTITY_RE}" -destination generic/platform=iOS -archivePath ${XCARCHIVE_PATH}
fi

/usr/bin/xcodebuild -exportArchive -archivePath ${XCARCHIVE_PATH} -exportPath ${IPA_PATH} -exportOptionsPlist ${EXPORT_PLIST_PATH}

if [  $1 = "te" ]; then
mv ${IPA_PATH}${SCHEME_TE}".ipa" ${IPA_PATH}${IPA_NAME}".ipa"
echo "打包成功"
else
mv ${IPA_PATH}${SCHEME_RE}".ipa" ${IPA_PATH}${IPA_NAME}".ipa"
echo "打包成功"
fi



##把archive目录中生成的xcarchive包转成ipa包放入ipa包目录，需要配置正确的Profile
#if [  $1 = "te" ]; then
#/usr/bin/xcodebuild -exportArchive -exportFormat IPA -archivePath ${XCARCHIVE_PATH} -exportPath ${IPA_PATH} -exportProvisioningProfile "${PROFILE_NAME}"
#else
#/usr/bin/xcodebuild -exportArchive -exportFormat IPA -archivePath ${XCARCHIVE_PATH} -exportPath ${IPA_PATH} -exportProvisioningProfile "${PROFILE_NAME_RE}"
#fi

#----------------------------------------------------------------------------------------


#----------------------------------------------------------------------------------------
#上传脚本
####本地的/home/databackup to ftp服务器上的/home/data####
#!/bin/bash
#ftp -n<<!
#open 10.1.64.47
#user wdjky wdjky
#binary
#hash
#cd /$1/
#lcd /Users/James/Desktop/Build/$1/
#prompt
#mput ${PRJ_NAME}.ipa
#close
#bye
#!
#----------------------------------------------------------------------------------------
