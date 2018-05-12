package com.wonders.health.venus.open.user.logic;

import com.wonders.health.venus.open.user.entity.Question;
import com.wonders.health.venus.open.user.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songzhen on 2016/8/15.
 */
public class ZytzbsManager {
    private static ZytzbsManager sInstance;
    private HttpTools mHttpTools;

    private ZytzbsManager(){
        mHttpTools = new HttpTools();
    }
    public static ZytzbsManager getInstance() {
        if (sInstance == null) {
            synchronized (ZytzbsManager.class) {
                if (sInstance == null) {
                    sInstance = new ZytzbsManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获得中医体质辨识结果
     * @param answer
     * @param callback
     */
    public void getTiZhi(String answer,ResponseCallback callback){
        SignRequest params = new SignRequest();
        params.addBodyParameter("registerid",UserManager.getInstance().getUser().uid);
        params.addBodyParameter("content",answer);
        mHttpTools.post(UrlConst.TIZHI_RESULT, params, callback);
    }

    /**
     * 获得所有问题列表
     * @return
     */
    public List<Question> getQuestionList(){
        List<Question> mList = new ArrayList<Question>();

        mList.add(new Question("您精神头足，乐于做事吗？", new ArrayList<String>() {
            {
                add("没有");
                add("很少");
                add("有时");
                add("经常");
                add("总是");
            }
        }));
        mList.add(new Question("您体力如何，是否稍微活动一下或做一点家务劳动就感到累吗？",
                new ArrayList<String>() {
                    {
                        add("没有");
                        add("很少");
                        add("有时");
                        add("经常");
                        add("总是");
                    }
                }));
        mList.add(new Question("您容易气短，呼吸短促，上气不接下气吗？",
                new ArrayList<String>() {
                    {
                        add("没有");
                        add("很少");
                        add("有时");
                        add("经常");
                        add("总是");
                    }
                }));
        mList.add(new Question("您说话没有力气吗？", new ArrayList<String>() {
            {
                add("没有");
                add("很少");
                add("有时");
                add("经常");
                add("总是");
            }
        }));
        mList.add(new Question("您心情不愉快，情绪低落吗？", new ArrayList<String>() {
            {
                add("没有");
                add("很少");
                add("有时");
                add("经常");
                add("总是");
            }
        }));
        mList.add(new Question("您遇事是否心情紧张？", new ArrayList<String>() {
            {
                add("没有");
                add("很少");
                add("有时");
                add("经常");
                add("总是");
            }
        }));
        mList.add(new Question("您是否总会想事情不乐观的一面以致情绪不好？",
                new ArrayList<String>() {
                    {
                        add("没有");
                        add("很少");
                        add("有时");
                        add("经常");
                        add("总是");
                    }
                }));
        mList.add(new Question("您容易感到害怕或受到惊吓吗?", new ArrayList<String>() {
            {
                add("没有");
                add("很少");
                add("有时");
                add("经常");
                add("总是");
            }
        }));
        mList.add(new Question("您感觉身体沉重吗?", new ArrayList<String>() {
            {
                add("没有");
                add("很少");
                add("有时");
                add("经常");
                add("总是");
            }
        }));
        mList.add(new Question("您感到手脚心发热吗?", new ArrayList<String>() {
            {
                add("没有");
                add("很少");
                add("有时");
                add("经常");
                add("总是");
            }
        }));
        mList.add(new Question("您手脚发凉吗？(不包含周围温度低或穿的少导致的手脚发冷)",
                new ArrayList<String>() {
                    {
                        add("没有");
                        add("很少");
                        add("有时");
                        add("经常");
                        add("总是");
                    }
                }));
        mList.add(new Question("您上腹部、背部、腰部或膝关节有一处或多处怕冷吗？",
                new ArrayList<String>() {
                    {
                        add("没有");
                        add("很少");
                        add("有时");
                        add("经常");
                        add("总是");
                    }
                }));
        mList.add(new Question("您比别人容易害怕冬天或是夏天的冷空调、电扇吗？",
                new ArrayList<String>() {
                    {
                        add("没有");
                        add("很少");
                        add("有时");
                        add("经常");
                        add("总是");
                    }
                }));
        mList.add(new Question("您每年感冒的次数？", new ArrayList<String>() {
            {
                add("一年不超过2次");
                add("一年2-4次");
                add("一年5-6次");
                add("一年8次以上");
                add("几乎每月都感冒");
            }
        }));
        mList.add(new Question("您没有感冒时也会鼻塞、流鼻涕吗?", new ArrayList<String>() {
            {
                add("没有");
                add("很少");
                add("有时");
                add("经常");
                add("总是");
            }
        }));
        mList.add(new Question("您有前额或脑门油脂分泌多的现象吗?", new ArrayList<String>() {
            {
                add("没有");
                add("很少");
                add("有时");
                add("经常");
                add("总是");
            }
        }));
        mList.add(new Question("您容易过敏(对药物、食物、气味、花粉或在季节交替、气候变化时)吗?",
                new ArrayList<String>() {
                    {
                        add("没有");
                        add("一年1、2次");
                        add("一年3、4次");
                        add("一年5、6次");
                        add("每次遇到上述原因都过敏");
                    }
                }));
        mList.add(new Question("您的皮肤容易起荨麻疹吗? (包括风团、风疹块、风疙瘩和过敏性皮疹)",
                new ArrayList<String>() {
                    {
                        add("没有");
                        add("很少");
                        add("有时");
                        add("经常");
                        add("总是");
                    }
                }));
        mList.add(new Question(
                "您的皮肤在不知不觉中会出现青紫瘀斑、皮下出血吗?(指皮肤在没有外伤的情况下出现青一块紫一块的情况)",
                new ArrayList<String>() {
                    {
                        add("没有");
                        add("很少");
                        add("有时");
                        add("经常");
                        add("总是");
                    }
                }));
        mList.add(new Question("您的皮肤一抓就红，并出现抓痕吗?(指被指甲或钝物划过后皮肤的反应)",
                new ArrayList<String>() {
                    {
                        add("没有");
                        add("很少");
                        add("有时");
                        add("经常");
                        add("总是");
                    }
                }));
        mList.add(new Question("您皮肤或口唇干吗?", new ArrayList<String>() {
            {
                add("没有");
                add("很少");
                add("有时");
                add("经常");
                add("总是");
            }
        }));
        mList.add(new Question("您两颧部有细微红丝吗?(脸颊部位细微的血丝,像钞票上的纹路)",
                new ArrayList<String>() {
                    {
                        add("没有");
                        add("很少");
                        add("有时");
                        add("经常");
                        add("总是");
                    }
                }));
        mList.add(new Question("您面部或鼻部有油腻感或者油亮发光吗? ",
                new ArrayList<String>() {
                    {
                        add("没有");
                        add("很少");
                        add("有时");
                        add("经常");
                        add("总是");
                    }
                }));
        mList.add(new Question("您面色晦黯或出现褐斑吗?", new ArrayList<String>() {
            {
                add("没有");
                add("很少");
                add("有时");
                add("经常");
                add("总是");
            }
        }));
        mList.add(new Question("您容易生痤疮或疮疖吗?", new ArrayList<String>() {
            {
                add("没有");
                add("很少");
                add("有时");
                add("经常");
                add("总是");
            }
        }));
        mList.add(new Question("您感到口干咽燥、总想喝水吗？", new ArrayList<String>() {
            {
                add("没有");
                add("很少");
                add("有时");
                add("经常");
                add("总是");
            }
        }));
        mList.add(new Question("您感到口苦或嘴里有异味吗?(指口苦或口臭)",
                new ArrayList<String>() {
                    {
                        add("没有");
                        add("很少");
                        add("有时");
                        add("经常");
                        add("总是");
                    }
                }));
        mList.add(new Question("您腹部脂肪肥厚吗?( 1尺=33.33厘米)",
                new ArrayList<String>() {
                    {
                        add("腹围<2.4尺");
                        add("腹围2.4-2.55尺 ");
                        add("腹围2.56-2.7尺");
                        add("腹围2.71-3.15尺");
                        add("腹围>3.15尺");
                    }
                }));
        mList.add(new Question("您不喜欢吃凉的食物，或吃了凉的食物后会不舒服吗？",
                new ArrayList<String>() {
                    {
                        add("没有");
                        add("很少");
                        add("有时");
                        add("经常");
                        add("总是");
                    }
                }));
        mList.add(new Question("您有大便黏滞不爽、解不尽的感觉吗？", new ArrayList<String>() {
            {
                add("没有");
                add("很少");
                add("有时");
                add("经常");
                add("总是");
            }
        }));
        mList.add(new Question("您容易便秘或大便干燥吗？", new ArrayList<String>() {
            {
                add("没有");
                add("很少");
                add("有时");
                add("经常");
                add("总是");
            }
        }));
        mList.add(new Question("您舌苔厚腻或有舌苔厚厚的感觉吗？(如果不清楚可咨询医生)",
                new ArrayList<String>() {
                    {
                        add("没有");
                        add("很少");
                        add("有时");
                        add("经常");
                        add("总是");
                    }
                }));
        mList.add(new Question("您舌下静脉瘀紫吗？(如果不清楚可咨询医生)",
                new ArrayList<String>() {
                    {
                        add("没有");
                        add("很少");
                        add("有时");
                        add("经常");
                        add("总是");
                    }
                }));

        return mList;

    }

}
