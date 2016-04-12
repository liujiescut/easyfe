package com.scut.easyfe.entity;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.Serializable;

/**
 * 所有的数据实体类必须继承自 BaseEntity
 * Created by jay on 16/3/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseEntity implements Serializable, Cloneable{
    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            return objectMapper.writeValueAsString(this);
        }catch (Exception e){
            return "实体类 格式出错！";
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Nullable
    public <T> T getCopy(){
        T copy = null;
        try {
            copy =  (T)clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return copy;
    }
}
