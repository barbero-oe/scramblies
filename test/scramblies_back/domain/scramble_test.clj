(ns scramblies-back.domain.scramble_test
  (:require [clojure.test :refer :all]
            [scramblies-back.domain.scramble :refer [scramble?]]))

(deftest scramble-test
  (testing "tells if characters of first string can be rearranged to form second string"
    (is (false? (scramble? "abc" "def")))
    (is (true? (scramble? "dlrow" "world")))
    (is (true? (scramble? "helloworld" "world")))))


