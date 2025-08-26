package greetings

import (
	"errors"
	"fmt"
	"math/rand"
)

func Bob(name string) string {
	return fmt.Sprintf("Hi, %v. Welcome", name)
}

// Where func<function declaration> Bob<function name>(name string<params and type>) string<return type>

func Hello(name string) (string, error) {

	if name == "" {
		return "", errors.New("empty name is provided")
	}

	message := fmt.Sprintf(getRandomMessage(), name)
	return message, nil
}

/*
Hellos returns a map that associates each of the named people with a greeting message.
*/
func Hellos(names []string) (map[string]string, error) {

	// initialize a map with the following syntax: make(map[key-type]value-type)
	messages := make(map[string]string)

	// Loop through the received slice of names, calling
	// the Hello function to get a message for each name.
	for _, name := range names {
		message, err := Hello(name)

		if err != nil {
			return nil, err
		}
		// In the map, associate the retrieved message with the name.
		messages[name] = message
	}
	return messages, nil
}

func getRandomMessage() string {

	//The Slice definition
	formats := []string{
		"Hi Bob, %v. Welcome",
		"Hi Nil, %v. Welcome",
		"Hi Greg, %v. Welcome",
		"Hi Alan, %v. Welcome",
		"Hi Joe, %v. Welcome",
	}
	// The Array definition
	// the compiler count the array elements for you
	anArrayDefinition_1 := [...]string{"a", "b"}

	// The Array definition
	anArrayDefinition_2 := [2]string{
		"a",
		"b"}

	//The Slice definition
	// The type specification for a slice is []T, where T is the type of the elements of the slice.
	anSliceDefinition_1 := []string{"a", "b"}

	//The Slice definition
	anSliceDefinition_2 := make([]byte, 5, 5) //TODO: Read docs
	// anSliceDefinition_2 == []byte{0, 0, 0, 0, 0}

	b := []byte{'g', 'o', 'l', 'a', 'n', 'g'}
	// b[1:4] == []byte{'o', 'l', 'a'}, sharing the same storage as b
	// b[:2] == []byte{'g', 'o'}
	// b[2:] == []byte{'l', 'a', 'n', 'g'}
	// b[:] == b

	// func copy(dst, src []T) int => The copy function supports copying between slices of different lengths
	// func append(s []T, x ...T) []T => The append function appends the elements x to the end of the slice s, and grows the slice if a greater capacity is needed.
	// The slice is only reference to an array, and if slice is referenced to an array, GC not able to remove whole array even if slice refenerce only smal byte of data,
	// To fix this problem one can copy the interesting data to a new slice before returning it:
	/*
			func CopyDigits(filename string) []byte {
		    	b, _ := ioutil.ReadFile(filename)
		    	b = digitRegexp.Find(b)
		    	c := make([]byte, len(b))
		    	copy(c, b)
		    	return c
			}
	*/

	// use this arrays and slices because without usage I have compilation erorr
	fmt.Println(len(anArrayDefinition_1))
	fmt.Println(len(anArrayDefinition_2))
	fmt.Println(len(anSliceDefinition_1))
	fmt.Println(len(anSliceDefinition_2))
	fmt.Println(len(b))
	fmt.Println("Access to array element b[0] =", b[0])
	fmt.Println("Access to slice element formats[0] =", formats[0])

	return formats[rand.Intn(len(formats))]
}
