package _go

import (
	"fmt"
	"net/http"
	"encoding/json"
	"io/ioutil"
)

func main() {
	// 监听协议
	//http.HandleFunc("/", HelloWorldHandler)
	http.HandleFunc("/user/login", UserLoginHandler)
	// 监听服务
	err := http.ListenAndServe("0.0.0.0:8082", nil)
	if err != nil {
		fmt.Println("服务器错误")
	}
}

func HelloWorldHandler(w http.ResponseWriter, r *http.Request)  {
	fmt.Println("r.Method = ", r.Method)
	fmt.Println("r.URL = ", r.URL)
	fmt.Println("r.Header = ", r.Header)
	fmt.Println("r.Body = ", r.Body)
	fmt.Println(w, "HelloWorld!")
}

func UserLoginHandler(response http.ResponseWriter, r *http.Request)  {
	fmt.Println("receive request")
	bytes, e := ioutil.ReadAll(r.Body)
	var body string
	if (e != nil) {
		fmt.Fprintf(response, "server error")
	} else {
		body = string(bytes)
	}
	fmt.Println("body = " + body)
	var u User1
	u.setUserInfo("jack", 10, 10)

	var s Student1
	s.setStudentInfo(12,"jack", []string{"sh","bj"},false)

	result, err := json.Marshal(u)
	if err != nil {
		fmt.Fprintf(response, "server error")
	} else {
		fmt.Fprintf(response, string(result))
	}
}

type User1 struct {
	Name string
	Age int
	Id int
}

type Student1 struct {
	Id int
	Name string
	Address []string
	IsWork bool
}

func (u *User1) setUserInfo(name string, age int, id int) {
	u.Name = name
	u.Age = age
	u.Id = id
}


func (s *Student1) setStudentInfo(id int, name string, address []string, iswork bool) {
	s.Address = address
	s.Id = id
	s.IsWork = iswork
	s.Name = name
}




