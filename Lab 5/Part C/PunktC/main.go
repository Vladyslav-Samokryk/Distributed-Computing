package main

import (
	"fmt"
	"math/rand"
	"time"
)

var n int
var arr1 []int
var arr2 []int
var arr3 []int

func sumChange(sumArr chan int, barr1 chan bool, arr1 []int){
	var sum int
	j := 0
	for  {
		if j>0{
			random := rand.Intn(len(arr1))
			isBigger := <-barr1

			if isBigger {
				arr1[random] -= 1
			} else {
				arr1[random] += 1
			}
		}

		sum = 0
		for i:=0;i<len(arr1);i++  {
			sum += arr1[i]
		}
		sumArr <- sum

		j++
	}
}

func verifEqual(sumArr1 chan int, sumArr2 chan int, sumArr3 chan int,
				barr1 chan bool, barr2 chan bool, barr3 chan bool){
	for true {
		sum1 := <-sumArr1
		fmt.Println("Sum of array 1 =", sum1)
		sum2 := <-sumArr2
		fmt.Println("Sum of array 2 =", sum2)
		sum3 := <-sumArr3
		fmt.Println("Sum of array 3 =", sum3)

		if sum1 == sum2 && sum1 == sum3 {
			fmt.Println("Are equal")
			break
		}else{
			fmt.Println(" ")
			if sum1 >= sum2{
				if sum1 >= sum3{
					barr1 <- true
					barr2 <- true
					barr3 <- false
				}else{
					barr1 <- false
					barr2 <- false
					barr3 <- true
				}
			} else {
				if sum2 >= sum3{
					barr1 <- false
					barr2 <- true
					barr3 <- false
				} else {
					barr1 <- false
					barr2 <- true
					barr3 <- true
				}
			}
		}
	}
}

func init(){
	rand.Seed(time.Now().UnixNano())

	n=6
	arr1 = make([]int, n)
	arr2 = make([]int, n)
	arr3 = make([]int, n)
	for i:=0;i<n;i++  {
		arr1[i] = i
		arr2[i] = i + 4
		arr3[i] = i + 1
	}
}

func main() {
	sumArr1 := make(chan int)
	sumArr2 := make(chan int)
	sumArr3 := make(chan int)
	barr1 := make(chan bool)
	barr2 := make(chan bool)
	barr3 := make(chan bool)

	go sumChange(sumArr1, barr1, arr1)
	go sumChange(sumArr2, barr2, arr2)
	go sumChange(sumArr3, barr3, arr3)
	verifEqual(sumArr1, sumArr2, sumArr3, barr1, barr2, barr3)
}
