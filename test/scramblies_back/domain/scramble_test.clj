(ns scramblies-back.domain.scramble_test
  (:require [clojure.test :refer :all]
            [scramblies-back.domain.scramble :refer [scramble?]]))

(deftest scramble-test
  (testing "checks if the characters of the first string can be rearranged to form the second string"
    (is (false? (scramble? "abc" "def")))
    (is (true? (scramble? "dlrow" "world")))
    (is (true? (scramble? "helloworld" "world")))
    (is (true? (scramble? "rekqodlw" "world")))
    (is (true? (scramble? "cedewaraaossoqqyt" "codewars")))
    (is (false? (scramble? "katas" "steak")))
    (is (false? (scramble? "kata" "katas")))))
