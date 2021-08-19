package _go

import (
	"net"
	"fmt"
	"time"
)

const RECV_BUF_LEN  = 1024

func main()  {
	//test1()
	test2()
}

func test2()  {
	a1 := [5]int{1,3,5,7,9}
	fmt.Println(len(a1))
}

func test1()  {
	conn,err := net.Dial("tcp", "127.0.0.1:6666")
	if err != nil {
		panic(err.Error())
	}
	defer conn.Close()
	buf := make([]byte, RECV_BUF_LEN)
	for i := 0; i < 5; i++ {
		msg := fmt.Sprintf("Hello World, %03d", i)
		n, err := conn.Write([]byte(msg))
		if err != nil {
			println("Write Buffer Error:", err.Error())
			break
		}
		fmt.Println(msg)
		n, err = conn.Read(buf)
		if err != nil {
			println("Read Buffer Error:", err.Error())
			break
		}
		fmt.Println(string(buf[0:n]))
		time.Sleep(time.Second)
	}
}