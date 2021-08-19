package _go

import (
	"encoding/json"
	"fmt"
)

//创建结构体
type Student struct {
	Id int
	Name string
	Address []string
	IsWork bool
}

//封装返回结构体实例的方法
func (s *Student) setStudentInfo(id int,name string,address []string, iswork bool) {

	s.Address = address
	s.Id = id
	s.IsWork = iswork
	s.Name = name
}

//封装返回结构体实例的方法  返回结构体的指针
func getStudentInfo(id int,name string,address []string, iswork bool) *Student {
	return &Student{id,name,address,iswork}
}


func main() {
	var s Student
	//初始化结构体
	s.setStudentInfo(12,"jack",[]string{"sh","bj"},false)
	//将结构体转成json
	result , err := json.Marshal(s)
	if err != nil {
		fmt.Println("error")
	}

	fmt.Printf("result = %+v\n",string(result))

	stu := getStudentInfo(13,"tom",[]string{"北京","上海","深圳"},true)
	fmt.Printf("type is %T\n",stu)

	fmt.Printf("student = %+v\n",stu)
	//获取结构体的name属性 本来是指针，但是可以直接获取其属性，这是go语言的语法糖
	fmt.Println("stu.Name = ",stu.Name)
	fmt.Println("(*stu).Name = ",(*stu).Name)

	result2,err2 := json.Marshal(stu)
	if err2 != nil {
		fmt.Println("error")
	}

	fmt.Println(string(result2))

}
