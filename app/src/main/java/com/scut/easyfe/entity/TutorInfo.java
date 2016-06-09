package com.scut.easyfe.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 专业辅导的信息(由课程跟状态(高中 初中 高考 中考)决定)
 * Created by jay on 16/6/8.
 */
public class TutorInfo extends BaseEntity {
    private String category = "";
    private String course = "";
    List<String> examPaper = new ArrayList<>();
    TutorKnowledge forKnowledge = new TutorKnowledge();

    public ArrayList<String> getLevelOneList(){
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < forKnowledge.getLevel1().size(); i++) {
            list.add(forKnowledge.getLevel1().get(i).getName());
        }

        return list;
    }

    public ArrayList<String> getLevelTwoList(int levelOneIndex){
        ArrayList<String> list = new ArrayList<>();
        if(levelOneIndex < 0 || levelOneIndex >= forKnowledge.getLevel1().size()){
            return list;
        }

        for (int i = 0; i < forKnowledge.getLevel1().get(levelOneIndex).getLevel2().size(); i++) {
            list.add(forKnowledge.getLevel1().get(levelOneIndex).getLevel2().get(i).getName());
        }

        return list;
    }

    public ArrayList<String> getLevelThreeList(int levelOneIndex, int levelTwoIndex){
        ArrayList<String> list = new ArrayList<>();
        if(levelOneIndex < 0 || levelOneIndex >= forKnowledge.getLevel1().size()){
            return list;
        }

        if(levelTwoIndex < 0 || levelTwoIndex >= forKnowledge.getLevel1().get(levelOneIndex).getLevel2().size()){
            return list;
        }

        list.addAll(forKnowledge.getLevel1().get(levelOneIndex).getLevel2().get(levelTwoIndex).getLevel3());

        return list;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public List<String> getExamPaper() {
        return examPaper;
    }

    public void setExamPaper(List<String> examPaper) {
        this.examPaper = examPaper;
    }

    public TutorKnowledge getForKnowledge() {
        return forKnowledge;
    }

    public void setForKnowledge(TutorKnowledge forKnowledge) {
        this.forKnowledge = forKnowledge;
    }

    static class TutorKnowledge extends BaseEntity{
        List<LevelOneEntity> level1 = new ArrayList<>();

        public List<LevelOneEntity> getLevel1() {
            return level1;
        }

        public void setLevel1(List<LevelOneEntity> level1) {
            this.level1 = level1;
        }
    }

    static class LevelOneEntity extends BaseEntity{
        private String name = "";
        List<LevelTwoEntity> level2 = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<LevelTwoEntity> getLevel2() {
            return level2;
        }

        public void setLevel2(List<LevelTwoEntity> level2) {
            this.level2 = level2;
        }
    }

    static class LevelTwoEntity extends BaseEntity{
        private String name = "";
        List<String> level3 = new ArrayList<>();

        public LevelTwoEntity() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getLevel3() {
            return level3;
        }

        public void setLevel3(List<String> level3) {
            this.level3 = level3;
        }
    }
}
