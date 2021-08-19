package _go

import (
	"net"
	"fmt"
	"io"
)

const RECV_BUF_LEN = 1024

/*func main()  {
	listener, err := net.Listen("tcp", "0.0.0.0:6666")
	if err != nil {
		panic("error listener " + err.Error())
	}
	fmt.Println("start the server!")
	for {
		conn, err := listener.Accept()
		if err != nil {
			panic("error accept:" + err.Error())
		}
		fmt.Println("Accepted the Connection:", conn.RemoteAddr())
		go EchoServer(conn)
	}
}*/

func EchoServer(conn net.Conn)  {
	buf := make([]byte, RECV_BUF_LEN)
	defer conn.Close()
	var resp = []byte("hello boy")
	for {
		n, err := conn.Read(buf)
		switch err {
		case nil:
			//conn.Write(buf[0:n])
			fmt.Println(n)
			conn.Write(resp)
		case io.EOF:
			fmt.Printf("Warning: End of data: %s \n", err)
		default:
			fmt.Printf("Error: Reading data: %s \n", err)
			return
		}
	}
}