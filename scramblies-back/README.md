# scramblies-back

## Scramblies challenge

### Task 1

Complete the function (scramble str1 str2) that returns true if a portion of str1 characters can be rearranged to match str2, otherwise returns false

- Notes: Only lower case letters will be used (a-z). No punctuation or digits will be included.
Performance needs to be considered

```clojure
;; Examples:

(scramble? “rekqodlw” ”world”) ==> true
(scramble? “cedewaraaossoqqyt” ”codewars”) ==> true
(scramble? “katas”  “steak”) ==> false
```

### Task 2

Create a web service that accepts two strings in a request and applies function scramble? from previous task to them.

### Task 3

Create a UI in ClojureScript with two inputs for strings and a scramble button. When the button is fired it should call the API from previous task and display a result.

- Notes: Please pay attention to tests, code readability and error cases.


## Developing

### Setup

When you first clone this repository, run:

```sh
lein duct setup
```

This will create files for local configuration, and prep your system
for the project.

### Environment

To begin developing, start with a REPL.

```sh
lein repl
```

Then load the development environment.

```clojure
user=> (dev)
:loaded
```

Run `go` to prep and initiate the system.

```clojure
dev=> (go)
:duct.server.http.jetty/starting-server {:port 3000}
:initiated
```

By default this creates a web server at <http://localhost:3000>.

When you make changes to your source files, use `reset` to reload any
modified files and reset the server.

```clojure
dev=> (reset)
:reloading (...)
:resumed
```

### Testing

Testing is fastest through the REPL, as you avoid environment startup
time.

```clojure
dev=> (test)
...
```

But you can also run tests through Leiningen.

```sh
lein test
```

## Legal

Copyright © 2021 FIXME
