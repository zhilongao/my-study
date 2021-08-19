package _go

import (
	"fmt"
	"time"
)

func main()  {
	// 并发执行程序
	go running(1, 5)
	go running(2, 6)
	// 接收命令输入，不做任何事情 这里主要等待两个协程执行完毕
	var intput string
	fmt.Scan(&intput)
}


func running(id, limit int) {
	var times int
	for {
		times++
		fmt.Printf("id: %d tick:%d\n", id, times)
		// 延迟一秒
		time.Sleep(time.Second)
		if times == limit {
			break
		}
	}
}


func say(s string)  {
	for i := 0; i < 5; i++ {
		time.Sleep(1 * time.Millisecond)
		fmt.Println(s)
	}
}


