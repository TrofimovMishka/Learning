
# GO

## Usefull commands

 - **go mod init < module path >< module name >** - init go module
 - **go run < path to module >** - compile and run go module
 - How add new dependencies, [source](https://go.dev/doc/tutorial/getting-started)
    - find import statement, example:
        
        `import "rsc.io/quote"`
        
    - Force go to get this dependencies by running:
        
        `go mod tidy` - add module requirements and sums, download dependencies<br>
        `go run .` - compile and run module<br>

    - Force go to get dependencies located locally by running:

        `go mod edit -replace lesson-2.com/greetings=../greetings` - if dependencies `lesson-2.com/greetings` is locally located under the path `../greetings` - that directive notify the Go where dependencies located, this directive should be run in module directory where you wanna add this dependency and after you should synchronize runnind `go mod tidy` directive. This add line `replace lesson-2.com/greetings => ../greetings` to go.mod file<br>
        

## Best practice

***
### Naming a module

When you run `go mod init` to create a module for tracking dependencies, you specify a module path that serves as the module’s name. The module path becomes the import path prefix for packages in the module. Be sure to specify a module path that won’t conflict with the module path of other modules.

At a minimum, a module path need only indicate something about its origin, such as a company or author or owner name. But the path might also be more descriptive about what the module is or does.

The module path is typically of the following form:

`<prefix>/<descriptive-text>`

 - The `<prefix>` - is typically a string that partially describes the module. This might be:

    - The location of the repository where Go tools can find the module’s source code (required if you’re publishing the module). For example, it might be github.com/<project-name>/. Use this best practice **if you think you might publish the module for others to use.**

    - A name you control. If you’re not using a repository name, be sure to choose a prefix that you’re confident won’t be used by others. A good choice is your company’s name. **Avoid common terms such as widgets, utilities, or app.**

- For the `<descriptive-text>`, a good choice would be a project name. Remember that package names carry most of the weight of describing functionality. The module path creates a namespace for those package names


**Reserved module path prefixes.** Go guarantees that the following strings won’t be used in package names.
 -  `test` - You can use test as a module path prefix for a module whose code is designed to locally test functions in another module.
 - `example` - Used as a module path prefix in some Go documentation, such as in tutorials where you’re creating a module just to track dependencies. Note that Go documentation also uses `example.com` to illustrate when the example might be a published module.

***