package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

const (

	smokerCount = 3
)

func numToElement(num int) string {
	switch num {
	case 0:
		return "tabacco"
	case 1:
		return "paper"
	case 2:
		return "matches"
	default:
		return "?"
	}
}

func smoker(
	component int,
	table *[]bool,
	readSemaphore,
	writeSemaphore chan bool,
	waitGroup *sync.WaitGroup) {
	defer waitGroup.Done()
	for {
		readSemaphore <- true
		fmt.Println(White("Checking table №", component),
			" ("+White(numToElement(component))+" dealer)")

		if !(*table)[component] {
			fmt.Println(White("Smoking person №", component, " ..."))
			for i := range *table {
				if (*table)[i] {
					fmt.Println(White(numToElement(i)) + White(" is on the table!"))
				}
				(*table)[i] = false
			}
			writeSemaphore <- true
		} else {
			<-readSemaphore
			time.Sleep(time.Second)
		}
	}
}

func observer(table *[]bool, readSemaphore, writeSemaphore chan bool, waitGroup *sync.WaitGroup) {
	defer waitGroup.Done()
	for {
		<-writeSemaphore
		var first, second = getRandomCigaretteComponent()
		fmt.Println("Observer put items:", White(numToElement(first)),
			"and", White(numToElement(second)))
		(*table)[first] = true
		(*table)[second] = true
		<-readSemaphore
	}
}

func getRandomCigaretteComponent() (int, int) {
	r := rand.New(rand.NewSource(time.Now().UnixNano()))

	stuff1 := r.Intn(3)
	stuff2 := r.Intn(3)
	for stuff2 == stuff1 {
		stuff2 = r.Intn(3)
	}

	return stuff1, stuff2
}

func main() {
	var waitGroup sync.WaitGroup
	var table = make([]bool, smokerCount)

	var readSemaphore = make(chan bool)
	var writeSemaphore = make(chan bool, 1)

	writeSemaphore <- true
	waitGroup.Add(1)
	go observer(&table, readSemaphore, writeSemaphore, &waitGroup)

	for i := 0; i < smokerCount; i++ {
		waitGroup.Add(1)
		go smoker(i, &table, readSemaphore, writeSemaphore, &waitGroup)
	}
	waitGroup.Wait()
}






var (
	White   = Color("\033[1;37m%s\033[0m")
)

func Color(colorString string) func(...interface{}) string {
	sprint := func(args ...interface{}) string {
		return fmt.Sprintf(colorString,
			fmt.Sprint(args...))
	}
	return sprint
}