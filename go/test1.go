package _go

import (
	"fmt"
)

func init()  {
	/* 这是我的第一个简单的程序 */
	fmt.Println("init  Hello, World!")
}

func main()  {
	fmt.Println("main execute")
	fmt.Println("a" + "b")
	var age int
	fmt.Println(age)
	fmt.Println("-------------")
	formatStr()
}

func formatStr()  {
	// 格式化字符串
	var stockcode = 123
	var enddate = "2020-12-31"
	var url="Code=%d&endDate=%s"
	var target_url = fmt.Sprintf(url, stockcode, enddate)
	fmt.Println(target_url)
}









