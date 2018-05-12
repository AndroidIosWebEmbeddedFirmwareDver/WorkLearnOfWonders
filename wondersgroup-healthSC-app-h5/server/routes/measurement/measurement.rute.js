'use strict'
var express = require('express')
var router = express.Router()
var request = require('../../common/request')
var api = require('../../common/api-url-map')

router.get('/question', function(req, res) {
    var registerid = req.query.registerid || ""

    res.render('measurement/question',{
          title: '中医体质辨识',
          timer: new Date().getTime(),
          data:{
              registerid:registerid,
              radios:[
                  {value:1,name:'没有'},
                  {value:2,name:'很少'},
                  {value:3,name:'有时'},
                  {value:4,name:'经常'},
                  {value:5,name:'总是'},
              ],
              content:[
                  {key:'tzbs1',index:1, title:'您精神头足，乐于做事吗？'},
                  {key:'tzbs2',index:2, title:'您体力如何，是否稍微活动一下或做一点家务劳动就感到累吗？'},
                  {key:'tzbs3',index:3, title:'您容易气短，呼吸短促，上气不接下气吗？'},
                  {key:'tzbs4',index:4, title:'您说话没有力气吗？'},
                  {key:'tzbs5',index:5, title:'您心情不愉快，情绪低落吗？'},
                  {key:'tzbs6',index:6, title:'您遇事是否心情紧张？'},
                  {key:'tzbs7',index:7, title:'您是否总会想事情不乐观的一面以致情绪不好？'},
                  {key:'tzbs8',index:8, title:'您容易感到害怕或受到惊吓吗？'},
                  {key:'tzbs9',index:9, title:'您感觉身体沉重吗？'},
                  {key:'tzbs10',index:10, title:'您感到手脚心发热吗？'},
                  {key:'tzbs11',index:11, title:'您手脚发凉吗？（不包含周围温度低或穿的少导致的手脚发冷）'},
                  {key:'tzbs12',index:12, title:'您上腹部、背部、腰部或膝关节有一处或多处怕冷吗？'},
                  {key:'tzbs13',index:13, title:'您比别人容易害怕冬天或是夏天的冷空调、电扇吗？'},
                  {key:'tzbs14',index:14, title:'您每年感冒的次数？'},
                  {key:'tzbs15',index:15, title:'您没有感冒时也会鼻塞、流鼻涕吗？'},
                  {key:'tzbs16',index:16, title:'您有前额或脑门油脂分泌多的现象吗？'},
                  {key:'tzbs17',index:17, title:'您容易过敏 (对药物、食物、气味、花粉或在季节交替、气候变化时) 吗？'},
                  {key:'tzbs18',index:18, title:'您的皮肤容易起荨麻疹吗？(包括风团、风疹块、风疙瘩和过敏性皮疹 )'},
                  {key:'tzbs19',index:19, title:'您的皮肤在不知不觉中会出现青紫瘀斑、皮下出血吗？（指皮肤在没有外伤的情况下出现青一块紫一块的情况）'},
                  {key:'tzbs20',index:20, title:'您的皮肤一抓就红，并出现抓痕吗？（指被指甲或钝物划过后皮肤的反应）'},
                  {key:'tzbs21',index:21, title:'您皮肤或口唇干吗？'},
                  {key:'tzbs22',index:22, title:'您两颧部有细微红丝吗？（脸颊部位细微的血丝，像钞票上的纹路）'},
                  {key:'tzbs23',index:23, title:'您面部或鼻部有油腻感或者油亮发光吗？'},
                  {key:'tzbs24',index:24, title:'您面色晦黯或出现褐斑吗？'},
                  {key:'tzbs25',index:25, title:'您容易生痤疮或疮疖吗？'},
                  {key:'tzbs26',index:26, title:'您感到口干咽燥、总想喝水吗？'},
                  {key:'tzbs27',index:27, title:'您感到口苦或嘴里有异味吗？（指口苦或口臭）'},
                  {key:'tzbs28',index:28, title:'您腹部脂肪肥厚吗？（1尺=33.33厘米）'},
                  {key:'tzbs29',index:29, title:'您不喜欢吃凉的食物，或吃了凉的食物后会不舒服吗？'},
                  {key:'tzbs30',index:30, title:'您有大便黏滞不爽、解不尽的感觉吗？'},
                  {key:'tzbs31',index:31, title:'您容易便秘或大便干燥吗？'},
                  {key:'tzbs32',index:32, title:'您舌苔厚腻或有舌苔厚厚的感觉吗？（如果不清楚可咨询医生）'},
                  {key:'tzbs33',index:33, title:'您舌下静脉瘀紫吗？（如果不清楚可咨询医生）'}
              ]
          }
      })
})

router.get('/answer', function(req, res) {
  res.render('measurement/answer',{})
})

router.get('/doctAnswer', function(req, res) {

    var param = req.query.param?JSON.parse(req.query.param):""

    res.render('measurement/doctAnswer',{
          title: '体质辨识结果',
          timer: new Date().getTime(),
          data:{
              result:{
                  '2':'是',
                  '3':'倾向是',
                  '4':'否'
              },
              content:JSON.parse(req.query.param)
          }
      })
})

/**
 * 提交答案
 */
router.post('/submitAnswer', function (req,res) {
    request
        .post(
            api.submitAnswer
            ,req.body
            ,{
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        )
        .then(function(data){
            if(data.code == 0){
                res.send(data);
            }else if(data.code == 1002){
                res.send(data);
            }else{
                res.send('error',{message:data.msg});
            }
        })
});

module.exports = router
