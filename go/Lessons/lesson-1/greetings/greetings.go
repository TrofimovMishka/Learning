package greerings

import (
	"fmt"
)

func Hello(name string) string {

	message := fmt.Sprintf("HI, %v. Good job", name)

	//  the := operator is a shortcut for declaring and initializing a variable in one line
	var bob string
	bob = fmt.Sprintf("NO, %v", name)

	return fmt.Sprintf("Message = %v, and bob = %v", message, bob)
}
