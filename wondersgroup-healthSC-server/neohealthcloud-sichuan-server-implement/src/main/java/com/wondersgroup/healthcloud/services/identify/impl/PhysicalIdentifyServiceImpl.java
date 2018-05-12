package com.wondersgroup.healthcloud.services.identify.impl;

import com.wondersgroup.healthcloud.common.utils.IdGen;
import com.wondersgroup.healthcloud.jpa.entity.identify.HealthQuestion;
import com.wondersgroup.healthcloud.jpa.repository.identify.HealthQuestionRepository;
import com.wondersgroup.healthcloud.services.identify.PhysicalIdentifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("physicalIdentifyService")
public class PhysicalIdentifyServiceImpl implements PhysicalIdentifyService {

    @Autowired
    private HealthQuestionRepository healthQuestionRepo;


    @Override
    public String physiqueIdentify(String registerid, String content) {
        String[] arr = content.split(",");
        Integer[] items = new Integer[arr.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = Integer.parseInt(arr[i]);
        }

        Score[] scores = this.getScoreList(items);
        if (scores[7].point >= 9 || scores[0].point < 8 || scores[5].point >= 11) {//评判无效
            this.saveHealthQuestion(registerid, content, null);
            return null;
        }

        String info = null;
        int gentlePhysical = (24 + items[0]) - (items[1] + items[3] + items[4] + items[12]);
        if (gentlePhysical >= 17 && scores[0].point <= 8) {//平和体质
            info = "平和质";
        } else if (gentlePhysical >= 17 && scores[0].point <= 10) {//基本平和体质
            info = "基本是平和质";
            info += " " + scores[0].getLevel() + " " + scores[1].getLevel();
            info = info.trim().replace(" ", ",");
        } else {
            info = " " + scores[0].getLevel() + " " + scores[1].getLevel();
            info = info.trim().replace(" ", ",");
        }
        this.saveHealthQuestion(registerid, content, info);
        return info;
    }

    private void saveHealthQuestion(String registerid, String content, String info) {
        //如果为本人测试，保存测试结果数据
        if (null != registerid && !"".equals(registerid)) {
            List<HealthQuestion> list = healthQuestionRepo.findResultByRegisterId(registerid);
            HealthQuestion entity = new HealthQuestion();
            entity.setRegisterid(registerid);
            entity.setContent(content);
            entity.setType("1");
            entity.setResult(info);
            entity.setTesttime(new Date());
            entity.setDelFlag("0");
            entity.setId(list == null || list.size() == 0 ? IdGen.uuid() : list.get(0).getId());
            healthQuestionRepo.save(entity);
        }
    }

    /**
     * 评估测试结果
     *
     * @param content
     * @return
     */
    private Score[] getScoreList(Integer[] items) {
        List<Score> list = new ArrayList<Score>();

        list.add(new Score(items[1] + items[2] + items[3] + items[13], "气虚质"));
        list.add(new Score(items[10] + items[11] + items[12] + items[28], "阳虚质"));
        list.add(new Score(items[9] + items[20] + items[25] + items[30], "阴虚质"));
        list.add(new Score(items[8] + items[15] + items[27] + items[31], "痰湿质"));
        list.add(new Score(items[22] + items[24] + items[26] + items[29], "湿热质"));
        list.add(new Score(items[18] + items[21] + items[23] + items[32], "血瘀质"));
        list.add(new Score(items[4] + items[5] + items[6] + items[7], "气郁质"));
        list.add(new Score(items[14] + items[16] + items[17] + items[19], "特禀质"));

        Score[] scores = list.toArray(new Score[]{});
        Arrays.sort(scores);
        return scores;
    }

    private class Score implements Comparable<Score> {
        private Integer point;// 排过序后的对象
        private String level;

        public Score(Integer point, String level) {
            this.point = point;
            this.level = level;
        }

        @Override
        public int compareTo(Score o) {// 没排序之前的对象
            if (point < o.point) {
                return 1;
            } else if (point > o.point) {
                return -1;
            } else {
                return level.compareTo(o.level);
            }
        }

        public String getLevel() {
            if (point >= 11) {
                return this.level;
            } else if (this.point >= 9 && this.point <= 10) {
                return "倾向是" + this.level;
            } else {
                return "";
            }
        }

    }
}
