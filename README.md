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

## Instructions

### Backend

To start the backend you need to execute the following commands

```sh
lein duct setup
lein repl
```

Once in the repl you need to load the profile in order to run the app.

```clojure
(dev)
(reset)
(go)
```

The backend should be running on [http://localhost:3000](http://localhost:3000)

To execute the tests, run the test function in the repl

```clojure
(test)
```

### Frontend

To run the front-end app you need to execute the following commands

```clojure
npm install
npm run watch
```

The web page should be running on [http://localhost:8280](http://localhost:8280)

You can run the tests on the browser with auto-reload executing in another terminal:

```clojure
npx karma start
```

