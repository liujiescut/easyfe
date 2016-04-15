package com.scut.easyfe.entity.test;

import com.scut.easyfe.entity.BaseEntity;

/**
 * 供选择的选项实体类
 * Created by jay on 16/3/30.
 */
public class ToSelectItem extends BaseEntity {
    private String text;              //被选择内容
    private Object formatText;        //被选择内容
    private boolean selected = false; //是否被选中

    public ToSelectItem(String text, boolean selected) {
        this(text, selected, "");
    }

    public ToSelectItem(String text, boolean selected, Object formatText) {
        this.text = text;
        this.selected = selected;
        this.formatText = formatText;
    }

    public Object getFormatText() {
        return formatText;
    }

    public void setFormatText(Object formatText) {
        this.formatText = formatText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
