package com.scut.easyfe.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * 所有的数据实体类必须继承自 BaseEntity
 * Created by jay on 16/3/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseEntity implements Serializable{
}
