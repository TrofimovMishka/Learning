***
Here I'll try to add some usefull and interesting info about go syntax and go specific methods and data-types 
***
### Array
- definition<br>

    `b := [2]string{"Penn", "Teller"}`<br> OR <br>
    `b := [...]string{"Penn", "Teller"}`

- access to elements by index<br>
    `b[0]`
***
### Slice
- definition<br>

    `b := []string{"Penn", "Teller"}`<br>
    using `func make([]T, len, cap) []T`<br>
    using existing array `b[1:4]` returns slice of this array

- access to elements by index<br>
    `b[0]`

- append elements to slice<br>
    `func append(s []T, x ...T) []T`

- copy slice<br>
    `func copy(dst, src []T) int`

- NOTE: 
    - When define slice using existing array, this slice is only reference to existing array. GC not delete array if slice in use. use `func copy(dst, src []T) int` to create slice object that independent from array
***
### Map
- definition<br>
    `messages := make(map[string]string)` syntax `make(map[key-type]value-type)`

- acces to value by key<br>
    `name := names["first]`

- adding elements<br>
    `names["first] = "Bob"`
***