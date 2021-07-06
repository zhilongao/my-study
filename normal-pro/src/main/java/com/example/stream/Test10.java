package com.example.stream;

import org.apache.commons.collections4.MapUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Test10 {
    public static void main(String[] args) {
        // 收集器
        List<Student> list = Arrays.asList(
                new Student("夏明", 25, Gender.FEMALE, Grade.ONE),
                new Student("夏明", 25, Gender.FEMALE, Grade.ONE),
                new Student("夏明1", 25, Gender.FEMALE, Grade.TWO),
                new Student("夏2", 45, Gender.FEMALE, Grade.ONE),
                new Student("夏3", 25, Gender.FEMALE, Grade.THREE),
                new Student("夏明4", 25, Gender.FEMALE, Grade.THREE),
                new Student("夏5", 25, Gender.FEMALE, Grade.FOUR),
                new Student("夏明6", 25, Gender.FEMALE, Grade.FOUR),
                new Student("夏7", 25, Gender.MALE, Grade.ONE),
                new Student("夏明", 25, Gender.FEMALE, Grade.ONE),
                new Student("夏明", 25, Gender.MALE, Grade.ONE),
                new Student("夏明", 25, Gender.FEMALE, Grade.ONE)
        );

        // 1.得到所有学生的年龄列表
        Set<Integer> ages1 = list.stream().map(Student::getAge).collect(Collectors.toSet());
        Set<Integer> ages2 = list.stream().map(Student::getAge).collect(Collectors.toCollection(TreeSet::new));
        System.err.println(ages1);
        System.err.println(ages2);

        // 2.统计汇总信息
        IntSummaryStatistics collect = list.stream().collect(Collectors.summarizingInt(Student::getAge));
        System.err.println("年龄汇总信息");
        System.err.println(collect);

        // 3.分块(两种)
        Map<Boolean, List<Student>> collect1 = list.stream().collect(Collectors.partitioningBy(s -> s.getGender() == Gender.FEMALE));
        MapUtils.verbosePrint(System.out, "男女学生列表", collect1);

        // 4.分组(多种)
        Map<Grade, List<Student>> collect2 = list.stream().collect(Collectors.groupingBy(Student::getGrade));
        MapUtils.verbosePrint(System.out, "学生班级分组", collect2);

        // 得到所有班级学生的个数
        Map<Grade, Long> collect3 = list.stream().collect(Collectors.groupingBy(Student::getGrade, Collectors.counting()));
        MapUtils.verbosePrint(System.out, "学生班级分组", collect3);


    }

    public static class Student{
        private String name;
        private int age;
        private Gender gender;
        private Grade grade;

        public Student(String name, int age, Gender gender, Grade grade) {
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.grade = grade;
        }


        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", gender=" + gender +
                    ", grade=" + grade +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Gender getGender() {
            return gender;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Grade getGrade() {
            return grade;
        }

        public void setGrade(Grade grade) {
            this.grade = grade;
        }
    }
    public static enum Gender{
        MALE,
        FEMALE
    }
    public static enum Grade{
        ONE,
        TWO,
        THREE,
        FOUR
    }
}
