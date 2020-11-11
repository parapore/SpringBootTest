package com.example.demo.model;

import javax.validation.GroupSequence;

//バリデーションの表示順指定。
// なんか上手くいかないのでやめた。
@GroupSequence({ ValidGroup1.class, ValidGroup2.class, ValidGroup3.class })
public interface GroupOrder {

}
