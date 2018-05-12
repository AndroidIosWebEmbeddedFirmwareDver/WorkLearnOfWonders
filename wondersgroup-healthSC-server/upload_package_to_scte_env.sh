
echo '#################'
echo '开始上传'
echo ''
echo '1.sichuan-user.war'
scp  ./neohealthcloud-sichuan-server-user-api/target/sichuan-user.war root@192.168.2.117:/usr/local/wondershealth/soft/tomcat-7005/webapps/sichuan-user.war
echo ''
echo '2.sichuan-internal.war'
scp  ./neohealthcloud-sichuan-server-internal-api/target/sichuan-internal.war root@192.168.2.117://usr/local/wondershealth/soft/tomcat-7006/webapps/sichuan-internal.war
echo ''
echo 'end!'
echo '#################'
