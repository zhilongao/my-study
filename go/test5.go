package _go

import "fmt"

func main()  {
	// 切片的长度和容量
	var slice1 = []int {1,2,3}
	fmt.Println(slice1) // [1 2 3 ]
	fmt.Printf("slice1的长度为%d，容量为%d\n", len(slice1), cap(slice1))
	// slice1的长度为3，容量为3

	var list = [5]int {1,2,3,4,5}
	slice2 := list[3:]
	fmt.Println(slice2) // [4 5]
	fmt.Printf("slice2的长度为%d，容量为%d\n", len(slice2), cap(slice2))
	// slice2的长度为2，容量为2

	slice3 := append(slice2,6,7,8)
	fmt.Println(slice3) // [1 2 3 6 7 8]
	fmt.Printf("slice3的长度为%d，容量为%d\n", len(slice3), cap(slice3))
	// slice3的长度为5，容量为6
}
