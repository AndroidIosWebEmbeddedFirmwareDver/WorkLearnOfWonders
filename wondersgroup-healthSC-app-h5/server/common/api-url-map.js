'use strict'

module.exports = {
    session:'/h5/internal/session',
    //文章详情
    getArticle:'/h5/back/article/areaInfo',
    //中医体质辨识问题提交
    submitAnswer:'/h5/api/physicalIdentify',
    getHelpCenter: '/h5/api/helpCenter/find',
    feedback:'/h5/api/feedback/add',

    // 健康档案
    DoctorList: '/h5/api/healthRecord/jiuzhen/list', //就诊记录列表
    ReportList: '/h5/api/healthRecord/jianyan/list', //查验报告列表
    ReportInfo: '/h5/api/healthRecord/jianyan/detail', //查验报告详情
    Prescription: '/h5/api/healthRecord/yongyao/list', //电子处方列表
    PrescriptionInfo: '/h5/api/healthRecord/yongyao/detail', //电子处方详情
    Hospitalization: '/h5/api/healthRecord/zhuyuan/list', //住院史列表
    HospitalizationInfo: '/h5/api/healthRecord/zhuyuan/detail', //住院史详情

    inspectInfo: '/h5/api/healthOnline/jianyan/detail', //检验报告详情
    checkupInfo: '/h5/api/healthOnline/jiancha/detail', //检查报告详情

    PrescriptionInfos: '/h5/api/eprescription/details', // 首页电子处方详情
}
