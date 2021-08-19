package main


import (
     "database/sql"
     "fmt"
     _ "github.com/go-sql-driver/mysql"
)

// 定义一个全局对象db
var db *sql.DB

//定义结构体
type User struct {
	Id   int
	Name string
	Age  int
}





func initDB(err error)  {
	//连接数据库
	dsn := "test1:123456@tcp(10.241.1.41:3306)/test1"
	db, err = sql.Open("mysql", dsn)
	//错误处理
	if err != nil {
		return err
	}
	//检验dsn是否正确
	err = db.Ping()
	if err != nil {
		return err
	}
	return nil
}

//数据库单行查询
func queryRowDemo() {
	//创建User对象
	var u User
	//sql语句
	sqlStr := "select * from user where id=?"
	//调用QueryRow方法返回查询的一行，后边调用的scan方法是将查询出来的行放入指定的参数中
	err := db.QueryRow(sqlStr, 1).Scan(&u.Id, &u.Name, &u.Age)
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Println(u.Id, u.Name, u.Age)
}


func main() {
	//调用初始化函数
	err := initDB()
	if err != nil {
		fmt.Println(err)
	}
	//调用函数
	//queryRowDemo()
	//queryDemo()
	//InsertDemo()
	//UpdateDemo()
	//DeleteDemo()
}

// The solution is to simply upgrade go to the latest version