package org.example.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhengzz
 * @version 1.0.0
 * @className Person
 * @description TODO
 * @date 2021/8/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {
    public String name;
    public Integer age;
}
